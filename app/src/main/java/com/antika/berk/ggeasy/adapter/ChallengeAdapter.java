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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.MissionHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.ChallengeObject;
import com.antika.berk.ggeasy.object.UserObject;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by Lenovo- on 5.9.2019.
 */

public class ChallengeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ChallengeObject> mKisiListesi;
    private Context context;

    public ChallengeAdapter(Activity activity, List<ChallengeObject> kisiler) {
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mKisiListesi = kisiler;


    }

    @Override
    public int getCount() {
        return mKisiListesi.size();
    }

    @Override
    public ChallengeObject getItem(int position) {
        return mKisiListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        DBHelper dbHelper = new DBHelper(context);
        UserObject uo=dbHelper.getUser();
        MissionHelper missionHelper=new MissionHelper(context);
        satirView = mInflater.inflate(R.layout.challenge_list_item, null);
        ImageView iv_user1 = (ImageView) satirView.findViewById(R.id.imageView26);
        ImageView iv_user2 = (ImageView) satirView.findViewById(R.id.imageView28);
        TextView tv_user1   = (TextView ) satirView.findViewById(R.id.textView58);
        TextView tv_user2  = (TextView ) satirView.findViewById(R.id.textView65);
        TextView puan  = (TextView ) satirView.findViewById(R.id.textView61);
        LinearLayout back=(LinearLayout)satirView.findViewById(R.id.background);

        ChallengeObject challengeObject = mKisiListesi.get(position);
        tv_user1.setText(challengeObject.getUser1Name());
        if(challengeObject.getUser2Name().length()>0)
            tv_user2.setText(challengeObject.getUser2Name());
        else
            tv_user2.setText(R.string.waiting);
        puan.setText("x "+missionHelper.gorev_puan.get(Integer.parseInt(challengeObject.getMission())-1));
        if(challengeObject.getStatus().equals("0")){

        }
        else if(challengeObject.getStatus().equals("2")){

        }
        if (challengeObject.getWinnerUser()){
            back.setBackgroundColor(0x9177FF07);
        }
        else if (challengeObject.getStatus().equals("2")  && !challengeObject.getWinnerUser()){
            back.setBackgroundColor(0x97FF2407);
        }

        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/profileicon/" + challengeObject.getUser1Icon() + ".png").transform(new CircleTransform()).into(iv_user1);

        if (challengeObject.getUser2Name().length()>0)
             Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/profileicon/" + challengeObject.getUser2Icon() + ".png").transform(new CircleTransform()).into(iv_user2);
        else
            Picasso.with(context).load(R.drawable.unknown).transform(new CircleTransform()).into(iv_user2);

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