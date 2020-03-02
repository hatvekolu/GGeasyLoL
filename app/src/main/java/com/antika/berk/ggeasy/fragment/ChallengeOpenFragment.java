package com.antika.berk.ggeasy.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.antika.berk.ggeasy.Other.Challenge;
import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.MissionHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.ChallengeObject;
import com.antika.berk.ggeasy.object.MatchIdObject;
import com.antika.berk.ggeasy.object.MissionObject;
import com.antika.berk.ggeasy.object.SummonerObject;
import com.antika.berk.ggeasy.object.UserObject;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengeOpenFragment extends DialogFragment {


    Button check;
    ImageView iv_s1,iv_s2,mission_logo;
    TextView tv_s1,tv_s2,puan,durum,mission_ad,mission_aciklama;
    ChallengeObject challengeObject;
    MissionHelper missionHelper;
    DBHelper dbHelper;
    UserObject uo;
    Challenge challenge;
    ChallangeFragment cf;
    public void setChallengeFragment(ChallangeFragment cf){
        this.cf=cf;
    }
    public void setChallengeObject(ChallengeObject challengeObject){
        this.challengeObject=challengeObject;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_challenge_open, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        check           =   (Button)view.findViewById(R.id.check          );
        iv_s1           =   (ImageView)view.findViewById(R.id.imageView31 );
        iv_s2           =   (ImageView)view.findViewById(R.id.imageView34 );
        tv_s1           =   (TextView)view.findViewById(R.id.textView67   );
        tv_s2           =   (TextView)view.findViewById(R.id.textView70   );
        puan            =   (TextView)view.findViewById(R.id.textView69   );
        durum           =   (TextView)view.findViewById(R.id.textView68   );
        mission_aciklama=   (TextView)view.findViewById(R.id.textView72   );
        mission_logo    =   (ImageView) view.findViewById(R.id.imageView30);
        mission_ad      =   (TextView)view.findViewById(R.id.textView71   );
        RiotApiHelper riotApiHelper=new RiotApiHelper();
        missionHelper=new MissionHelper(getContext());
        dbHelper=new DBHelper(getContext());
        if(challengeObject.getStatus().equals("0")){
            durum.setText(getContext().getString(R.string.waiting));
            durum.setTextColor(0x99FFC107);
        }
        else if (challengeObject.getStatus().equals("1")){
            durum.setText(getContext().getString(R.string.accepted));
            durum.setTextColor(0xff000000);
        }

        else {
            if (challengeObject.getWinnerUser()){
                durum.setText(getContext().getString(R.string.victory));
                durum.setTextColor(0x9907FF07);
            }
            else if(!challengeObject.getWinnerUser() && challengeObject.getStatus().equals("2")){
                durum.setText(getContext().getString(R.string.defeat));
                durum.setTextColor(0x99FF1100);
            }
        }
        tv_s1.setText(challengeObject.getUser1Name());
        if(challengeObject.getUser2Icon().length()>0)
            tv_s2.setText(challengeObject.getUser2Name());
        else
            tv_s2.setText(R.string.waiting);
        mission_ad.setText(missionHelper.gorev_txt.get(Integer.parseInt(challengeObject.getMission())-1));
        mission_aciklama.setText(missionHelper.GorevAciklama.get(Integer.parseInt(challengeObject.getMission())-1));
        mission_logo.setImageResource(missionHelper.gorev_img.get(Integer.parseInt(challengeObject.getMission())-1));
        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/profileicon/" + challengeObject.getUser2Icon() + ".png").transform(new CircleTransform()).into(iv_s2);
        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/profileicon/" + challengeObject.getUser1Icon() + ".png").transform(new CircleTransform()).into(iv_s1);


        puan.setText("x "+missionHelper.gorev_puan.get(Integer.parseInt(challengeObject.getMission())-1));

        if(challengeObject.getStatus().equals("1")){
            check.setVisibility(View.VISIBLE);
        }


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getData2().execute();
            }
        });




        return view;
    }
    public class CircleTransform implements com.squareup.picasso.Transformation {
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


    private class getData2 extends AsyncTask<String,String,String> {

        BlankFragment progress;
        MatchIdObject matchIdObject;
        MissionObject missionObject;


        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");

        }

        @Override
        protected String doInBackground(String... strings) {
            RiotApiHelper riotApiHelper = new RiotApiHelper();
            challenge=new Challenge(getContext());
            try{
                dbHelper=new DBHelper(getContext());
                uo=dbHelper.getUser();
                SummonerObject so=riotApiHelper.getSumonner(uo.getEmail(),uo.getRegion());
                matchIdObject=riotApiHelper.getMatchIDs(so.getAccountId(),uo.getRegion(),1).get(0);
                missionObject=riotApiHelper.getMatch(matchIdObject.getMatchID(),uo.getRegion(),so.getId());
                return "okey";
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            boolean sonuc = false;
            if(s.equals("HATA"))
                Toast.makeText(getContext(),getContext().getString(R.string.try_again),Toast.LENGTH_LONG).show();
            else {
                if (dbHelper.getUser().getSummonerID().equals(challengeObject.getUser2Name())){

                    if(!matchIdObject.getMatchID().equals(challengeObject.getUser2Match())){

                        if(challengeObject.getMission().equals("1")){

                            sonuc=challenge.Gorev1(challengeObject.getId(),missionObject.getPentaKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("2")){

                            sonuc=challenge.Gorev2(challengeObject.getId(),missionObject.getQuadraKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("3")){

                            sonuc=challenge.Gorev3(challengeObject.getId(),missionObject.getTripleKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("4")){

                            sonuc=challenge.Gorev4(challengeObject.getId(),missionObject.getDoubleKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("5")){

                            sonuc=challenge.Gorev5(challengeObject.getId(),missionObject.getKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("6")){

                            sonuc=challenge.Gorev6(challengeObject.getId(),missionObject.getKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("7")){

                            sonuc=challenge.Gorev7(challengeObject.getId(),missionObject.getKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("8")){

                            sonuc=challenge.Gorev8(challengeObject.getId(),missionObject.getAssists());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("9")){

                            sonuc=challenge.Gorev9(challengeObject.getId(),missionObject.getAssists());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }
                        }else if(challengeObject.getMission().equals("10")){

                            sonuc=challenge.Gorev10(challengeObject.getId(),missionObject.getAssists());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("11")){

                            sonuc=challenge.Gorev11(challengeObject.getId(),missionObject.getTowerKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("12")){

                            sonuc=challenge.Gorev12(challengeObject.getId(),missionObject.getTowerKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("13")){

                            sonuc=challenge.Gorev13(challengeObject.getId(),missionObject.getTowerKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("14")){

                            sonuc=challenge.Gorev14(challengeObject.getId(),(missionObject.getMinionsKilled()+missionObject.getNeutralMinionsKilled()));
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("15")){

                            sonuc=challenge.Gorev15(challengeObject.getId(),(missionObject.getMinionsKilled()+missionObject.getNeutralMinionsKilled()));
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("16")){

                            sonuc=challenge.Gorev16(challengeObject.getId(),missionObject.getMinionsKilled()+missionObject.getNeutralMinionsKilled());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }
                    }
                    else
                        Toast.makeText(getContext(),getContext().getString(R.string.not_detected),Toast.LENGTH_LONG).show();
                }
                else {
                    if(!matchIdObject.getMatchID().equals(challengeObject.getUser1Match())){

                        if(challengeObject.getMission().equals("1")){

                            sonuc=challenge.Gorev1(challengeObject.getId(),missionObject.getPentaKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }
                        }else if(challengeObject.getMission().equals("2")){

                            sonuc=challenge.Gorev2(challengeObject.getId(),missionObject.getQuadraKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }
                        }else if(challengeObject.getMission().equals("3")){

                            sonuc=challenge.Gorev3(challengeObject.getId(),missionObject.getTripleKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("4")){

                            sonuc=challenge.Gorev4(challengeObject.getId(),missionObject.getDoubleKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("5")){

                            sonuc=challenge.Gorev5(challengeObject.getId(),missionObject.getKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("6")){

                            sonuc=challenge.Gorev6(challengeObject.getId(),missionObject.getKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }
                        }else if(challengeObject.getMission().equals("7")){

                            sonuc=challenge.Gorev7(challengeObject.getId(),missionObject.getKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("8")){

                            sonuc=challenge.Gorev8(challengeObject.getId(),missionObject.getAssists());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("9")){

                            sonuc=challenge.Gorev9(challengeObject.getId(),missionObject.getAssists());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("10")){

                            sonuc=challenge.Gorev10(challengeObject.getId(),missionObject.getAssists());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("11")){

                            sonuc=challenge.Gorev11(challengeObject.getId(),missionObject.getTowerKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("12")){

                            sonuc=challenge.Gorev12(challengeObject.getId(),missionObject.getTowerKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }
                        }else if(challengeObject.getMission().equals("13")){

                            sonuc=challenge.Gorev13(challengeObject.getId(),missionObject.getTowerKills());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("14")){

                            sonuc=challenge.Gorev14(challengeObject.getId(),missionObject.getMinionsKilled()+missionObject.getNeutralMinionsKilled());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }

                        }else if(challengeObject.getMission().equals("15")){
                            sonuc=challenge.Gorev15(challengeObject.getId(),missionObject.getMinionsKilled()+missionObject.getNeutralMinionsKilled());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }
                        }else if(challengeObject.getMission().equals("16")){

                            sonuc=challenge.Gorev16(challengeObject.getId(),missionObject.getMinionsKilled()+missionObject.getNeutralMinionsKilled());
                            if (sonuc){
                                cf.yenile();
                                getDialog().dismiss();
                            }
                        }
                    }
                    else
                        Toast.makeText(getContext(),getContext().getString(R.string.not_detected),Toast.LENGTH_LONG).show();
                }



            }
            progress.dismiss();


        }

    }


}