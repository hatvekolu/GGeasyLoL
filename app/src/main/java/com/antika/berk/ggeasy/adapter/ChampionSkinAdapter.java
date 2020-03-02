package com.antika.berk.ggeasy.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.fragment.BlankFragment;
import com.antika.berk.ggeasy.object.ChampionSkinObject;
import com.dmallcott.dismissibleimageview.DismissibleImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

import it.sephiroth.android.library.picasso.Picasso;

public class ChampionSkinAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    Context context;
    private List<ChampionSkinObject> championList;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public ChampionSkinAdapter(Activity activity, List<ChampionSkinObject> champions) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        championList = champions;
        context=activity;
    }

    @Override
    public int getCount() {
        return championList.size();
    }

    @Override
    public ChampionSkinObject getItem(int position) {
        //şöyle de olabilir: public Object getItem(int position)
        return championList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        DismissibleImageView championlogo;
        TextView championName;
        Button dl;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        final ViewHolder viewHolder;

        if(rowView==null)
        {
            rowView = mInflater.inflate(R.layout.grid_skin_item,null);
            viewHolder = new ViewHolder();
            viewHolder.championlogo = (DismissibleImageView) rowView.findViewById(R.id.activity_main_dismissibleImageView);
            viewHolder.championName = (TextView) rowView.findViewById(R.id.textView4);
            viewHolder.dl = (Button) rowView.findViewById(R.id.button9);
            rowView.setTag(viewHolder);
        }
        else {
            viewHolder  = (ViewHolder) rowView.getTag();
        }

        final ChampionSkinObject champ = championList.get(position);
        viewHolder.championName.setText(champ.getSkinName());
        if(champ.getSkinName().equals("default"))
            viewHolder.championName.setText(context.getString(R.string.classic));
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/img/champion/loading/"+champ.getKey()+"_"+champ.getNum()+".jpg").into(viewHolder.championlogo);

        viewHolder.dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadImageTask().execute("http://ddragon.leagueoflegends.com/cdn/img/champion/loading/"+champ.getKey()+"_"+champ.getNum()+".jpg");
            }
        });


        return rowView;
    }

    public Bitmap getBitmapFromURL(String imageUrl) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveImageToExternalStorage(Bitmap finalBitmap) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/GGEasy-LOL");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(context, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        BlankFragment progress;
        @Override
        protected void onPreExecute() {

            FragmentActivity activity = (FragmentActivity)(context);
            FragmentManager fm = activity.getSupportFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = getBitmapFromURL(urldisplay);

            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            saveImageToExternalStorage(result);
            progress.dismiss();
        }

    }



}
