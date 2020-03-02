package com.antika.berk.ggeasy.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.MissionHelper;
import com.antika.berk.ggeasy.object.RozetObject;
import com.mobstac.circularimageprogress.CircularImageProgressView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by Lenovo- on 5.9.2019.
 */

public class RozetAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<RozetObject> mKisiListesi;
    private Context context;
    MissionHelper missionHelper;

    public RozetAdapter(Activity activity, List<RozetObject> kisiler) {
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mKisiListesi = kisiler;
        missionHelper=new MissionHelper(context);
    }

    @Override
    public int getCount() {
        return mKisiListesi.size();
    }

    @Override
    public RozetObject getItem(int position) {
        return mKisiListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.rozet_grid_item, null);

        ImageView rozet = (ImageView) satirView.findViewById(R.id.imageView31);
        CircularImageProgressView progressView =(CircularImageProgressView) satirView.findViewById(R.id.circular_image_progress);
        CircularImageProgressView progressView1 =(CircularImageProgressView) satirView.findViewById(R.id.circularImageProgressView);
        TextView textView = (TextView)satirView.findViewById(R.id.textView47);
        RozetObject kisi = mKisiListesi.get(position);
        Picasso.with(context).load(missionHelper.gorev_img.get((Integer.parseInt(kisi.getMission()))-1)).transform(new CircleTransform()).into(rozet);
        textView.setText(missionHelper.gorev_txt.get((Integer.parseInt(kisi.getMission()))-1));

        progressView.setCircleWidth(20);
        progressView1.setCircleWidth(20);
        if (kisi.getTotal()<=25){
            progressView.setProgress(kisi.getTotal());
        }

        else if(kisi.getTotal()>25 && kisi.getTotal()<=50) {
            progressView.setVisibility(View.INVISIBLE);
            progressView1.setVisibility(View.VISIBLE);
            progressView1.setProgress((kisi.getTotal()-25));
        }

        return satirView;
    }
    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}
