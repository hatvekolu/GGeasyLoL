package com.antika.berk.ggeasy.Other;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.UserObject;

public class Challenge {
    Context c;
    DBHelper dbHelper;
    public  Challenge(Context c){
        this.c=c;

    }

    public  boolean Gorev1(String id,int x){


        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>0){
            PuanGonder(id,uo.getEmail(),3000);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;
    }
    public  boolean Gorev2(String id,int x){


        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>0){
            PuanGonder(id,uo.getEmail(),1500);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;

    }
    public  boolean Gorev3(String id,int x){


        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>0){
            PuanGonder(id,uo.getEmail(),750);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;

    }
    public  boolean Gorev4(String id,int x){


        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>0){
            PuanGonder(id,uo.getEmail(),300);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;

    }
    public  boolean Gorev5(String id,int x){

        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>=10){
            PuanGonder(id,uo.getEmail(),600);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;

    }
    public  boolean Gorev6(String id,int x){

        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>=20){
            PuanGonder(id,uo.getEmail(),1200);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;

    }
    public  boolean Gorev7(String id,int x){

        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>=30){
            PuanGonder(id,uo.getEmail(),2000);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;
    }
    public  boolean Gorev8(String id,int x){

        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>=10){
            PuanGonder(id,uo.getEmail(),350);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;
    }
    public  boolean Gorev9(String id,int x){

        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>=20){
            PuanGonder(id,uo.getEmail(),750);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;
    }
    public  boolean Gorev10(String id,int x){

        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>=30){
            PuanGonder(id,uo.getEmail(),1500);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;
    }
    public  boolean Gorev11(String id,int x){

        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>=2){
            PuanGonder(id,uo.getEmail(),400);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;
    }
    public  boolean Gorev12(String id,int x){

        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>=4){
            PuanGonder(id,uo.getEmail(),1100);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;
    }
    public  boolean Gorev13(String id,int x){

        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>=6){
            PuanGonder(id,uo.getEmail(),2000);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;
    }
    public  boolean Gorev14(String id,int x){

        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>99){
            PuanGonder(id,uo.getEmail(),200);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;
    }
    public  boolean Gorev15(String id,int x){

        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>149){
            PuanGonder(id,uo.getEmail(),350);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;
    }
    public  boolean Gorev16(String id,int x){

        dbHelper=new DBHelper(c);
        UserObject uo=dbHelper.getUser();
        if(x>199){
            PuanGonder(id,uo.getEmail(),600);
            Toast.makeText(c,c.getString(R.string.congratulations),Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(c,c.getString(R.string.mission_failed),Toast.LENGTH_LONG).show();
        return false;
    }
    public void PuanGonder(String id,String userID, double puan){
        dbHelper=new DBHelper(c);
        new getData().execute(id,userID, Double.toString(puan),dbHelper.getUser().getRegion());
    }

    private class getData extends AsyncTask<String,String,String> {


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            RiotApiHelper riotApiHelper = new RiotApiHelper();

            String cevap =  riotApiHelper.readURL("http://ggeasylol.com/json/sonuc_challenge.php?ID="+params[0]+"&winnerName="+params[1].replaceAll(" ","_")+"&puan="+params[2]+"&Region="+params[3]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }

}
