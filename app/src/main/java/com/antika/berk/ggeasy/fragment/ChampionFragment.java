package com.antika.berk.ggeasy.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.adapter.ChampionServerAdapter;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.ChampionServerObject;
import com.antika.berk.ggeasy.object.ChampionSkillObject;
import com.antika.berk.ggeasy.object.ChampionSkinObject;
import com.antika.berk.ggeasy.object.ChampionStatObject;
import com.antika.berk.ggeasy.object.CounterObject;
import com.antika.berk.ggeasy.object.TavsiyeObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ChampionFragment extends Fragment {
    GridView gv_champions;
    EditText et_arama;
    ChampionServerAdapter adapter;
    DBHelper dbHelper;
    ArrayList<ChampionServerObject> champions;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_champion, container, false);

        gv_champions = (GridView) view.findViewById(R.id.grid_view);
        et_arama     = (EditText) view.findViewById(R.id.editText3);

        new getData().execute();

        gv_champions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChampionServerObject data= (ChampionServerObject) adapter.getItem(position);
                FragmentManager fm = getFragmentManager();
                ChampionTabHost alertDialog = new ChampionTabHost();
                alertDialog.setItem(data);
                alertDialog.show(fm,"fragment_alert");

                View view1 = getActivity().getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);}
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    getActivity().finish();
                }
                return false;
            }
        } );
        return view;
    }

    private class getData extends AsyncTask<String, String, String> {
        BlankFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {
            RiotApiHelper raHelper = new RiotApiHelper();
            champions=new ArrayList<ChampionServerObject>();



            dbHelper = new DBHelper( getContext());
            try{
                String gelendata=raHelper.readURL("http://ggeasylol.com/json/get_champions.php?language="+getString(R.string.language3).toLowerCase());

                JSONArray arrayAna=new JSONArray(gelendata);
                for (int i = 0; i<arrayAna.length();i++) {
                    ArrayList<TavsiyeObject> tavsiyeObject = new ArrayList<TavsiyeObject>();
                    ArrayList<ChampionSkinObject> championSkinObjects = new ArrayList<ChampionSkinObject>();
                    ArrayList<ChampionSkillObject> championSkillObjects = new ArrayList<ChampionSkillObject>();
                    ChampionStatObject championStatObject;
                    CounterObject co;
                    CounterObject ao;
                    JSONObject object = arrayAna.getJSONObject(i);
                    JSONArray arraySkill = object.getJSONArray("spells");
                    JSONArray arraySkins = object.getJSONArray("skins");
                    JSONArray arrayTips = object.getJSONArray("enemytips");
                    for (int j = 0; j<arraySkill.length();j++) {
                        JSONObject object1 = arraySkill.getJSONObject(j);
                        championSkillObjects.add(new ChampionSkillObject(object1.getString("name"),object1.getString("description"),
                                object1.getString("full")  ));
                    }
                    for (int j = 0; j<arraySkins.length();j++) {
                        JSONObject object1 = arraySkins.getJSONObject(j);
                        championSkinObjects.add(new ChampionSkinObject(object1.getString("name"),Integer.parseInt(object1.getString("num")),
                                object1.getString("key")  ));
                    }
                    for (int j = 0; j<arrayTips.length();j++) {
                        JSONObject object1 = arrayTips.getJSONObject(j);
                        tavsiyeObject.add(new TavsiyeObject(object1.getString("tip") ));
                    }
                    championStatObject = new ChampionStatObject(object.getString("armor"),object.getString("armorperlevel")
                            ,object.getString("attackdamage"),object.getString("attackdamageperlevel"),object.getString("attackrange")
                            ,object.getString("hp"),object.getString("hpperlevel"),object.getString("hpregen"),object.getString("hpregenperlevel")
                            ,object.getString("movespeed"),object.getString("mp"),object.getString("mpperlevel"),object.getString("mpregen")
                            ,object.getString("mpregenperlevel"),object.getString("spellblock"),object.getString("spellblockperlevel")
                            ,Integer.parseInt(object.getString("attack")),Integer.parseInt(object.getString("defense")),Integer.parseInt(object.getString("magic"))
                            ,Integer.parseInt(object.getString("difficulty")));
                    ao=new CounterObject(object.getString("against1"),object.getString("against2"),object.getString("against3"),
                            object.getString("against4"),object.getString("against5"));
                    co=new CounterObject(object.getString("counter1"),object.getString("counter2"),object.getString("counter3"),
                            object.getString("counter4"),object.getString("counter5"));
                    champions.add(new ChampionServerObject(object.getString("id"),
                            object.getString("key"),
                            object.getString("name"),object.getString("version"),object.getString("ip"),object.getString("rp"),tavsiyeObject,championSkillObjects,championSkinObjects,championStatObject,
                            co,ao));

                }
                dbHelper.insertMatch(champions.get(0).getVersion(), "Gorev32");


                return "0";

            }catch (Exception e){
                return ""+champions.size();
            }



        }

        @Override
        protected void onPostExecute(String results)
        {
            if(results.equals("0")){
                adapter = new ChampionServerAdapter(getActivity(), champions);
                gv_champions.setAdapter(adapter);
                et_arama.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        System.out.println("Text ["+s+"]");

                        adapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
            else
                Toast.makeText(getActivity(), results, Toast.LENGTH_LONG).show();
            progress.dismiss();
        }

    }
    private class skillEkle extends AsyncTask<String, String, String> {
        BlankFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values) {
            RiotApiHelper raHelper = new RiotApiHelper();
            String gelendata = "";
            try {

                for (int i = 0; i< champions.size();i++) {
                    for (int j =0;j<champions.get(i).getChampionSkillObjects().size();j++){
                        gelendata = raHelper.readURL("http://ggeasylol.com/admin/skill_ekle.php?championID="+champions.get(i).getChampionID()+
                                "&language=pt&skill="+champions.get(i).getChampionSkillObjects().get(j).getImage()
                                +"&skillA="+champions.get(i).getChampionSkillObjects().get(j).getDescription().replaceAll("<font color='#FFF673'>","").replaceAll("<font color='#669900'>","").replaceAll("<font color='#FFFFFF'>","").replaceAll("<font color='#fe5c50'>","").replaceAll("<font color='#8484fb'>","").replaceAll("<font color='#99FF99'>","").replaceAll("<font color='#00DD33'>","").replaceAll("<font color='#ee91d7'>","").replaceAll("<font color='#91d7ee'>","").replaceAll("<font color='#6655CC'>","").replaceAll("<font color='#8C8C8C'>","").replaceAll("<font color='#cd90ee'>","").replaceAll("<font color='#EDDA74'>","").replaceAll("<font color='#9b0f5f'>","").replaceAll("<font color='#BB55EE'>","").replaceAll("<font color='#FF3300'>","").replaceAll("<font color='#FF9900'>","").replaceAll(" ","_").replaceAll("'","").replaceAll("<br>","").replaceAll("</factionIonia1>","").replaceAll("<factionIonia1>","").replaceAll("@ResetWindow@","").replaceAll("@ResetCDR@","").replaceAll("</magicDamage>","").replaceAll("<magicDamage>","").replaceAll("</font>","")
                                +"&skillN="+champions.get(i).getChampionSkillObjects().get(j).getSkillName().replaceAll(" ","_").replaceAll("'","")).replaceAll("<br>","").replaceAll("</factionIonia1>","").replaceAll("<factionIonia1>","");

                    }
                }


                return gelendata;

            } catch (Exception e) {
                return "HATA";
            }


        }

        @Override
        protected void onPostExecute(String results) {


            progress.dismiss();

        }
    }
    private class statEkle extends AsyncTask<String, String, String> {
        BlankFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values) {
            RiotApiHelper raHelper = new RiotApiHelper();
            String gelendata = "";
            try {

                for (int i = 0; i< champions.size();i++) {
                    gelendata = raHelper.readURL("http://ggeasylol.com/admin/stat_ekle.php?championID="+champions.get(i).getChampionID()
                            +"&armor="+champions.get(i).getCso().getArmor()+"&armorperlv="+champions.get(i).getCso().getArmorperlevel()
                            +"&attackdamage="+champions.get(i).getCso().getAttackdamage()+"&attackdamageperlv="+champions.get(i).getCso().getAttackdamageperlevel()
                            +"&attackrange="+champions.get(i).getCso().getAttackrange()+"&hpperlv="+champions.get(i).getCso().getHpperlevel()+"&hp="+champions.get(i).getCso().getHp()
                            +"&hpregen="+champions.get(i).getCso().getHpregen()+"&hpregenperlv="+champions.get(i).getCso().getHpregenperlevel()
                            +"&movespeed="+champions.get(i).getCso().getMovespeed()+"&mp="+champions.get(i).getCso().getMp()
                            +"&mpperlv="+champions.get(i).getCso().getMpperlevel()+"&mpregen="+champions.get(i).getCso().getMpregen()
                            +"&mpregenperlv="+champions.get(i).getCso().getMpperlevel()+"&spellblock="+champions.get(i).getCso().getSpellblock()
                            +"&spellblockperlv="+champions.get(i).getCso().getSpellblockperlevel()+"&attack="+champions.get(i).getCso().getAttack()
                            +"&defense="+champions.get(i).getCso().getDefense()+"&magic="+champions.get(i).getCso().getMagic()
                            +"&difficulty="+champions.get(i).getCso().getDifficulty());
                }


                return gelendata;

            } catch (Exception e) {
                return "HATA";
            }


        }

        @Override
        protected void onPostExecute(String results) {


            progress.dismiss();

        }
    }
    private class loreEkle extends AsyncTask<String, String, String> {
        BlankFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values) {
            RiotApiHelper raHelper = new RiotApiHelper();
            String gelendata = "";
            try {

                for (int i = 0; i< champions.size();i++) {
                    gelendata = raHelper.readURL("http://ggeasylol.com/admin/lore_ekle.php?championID="+champions.get(i).getChampionID()
                            +"&language=tr");
                }


                return gelendata;

            } catch (Exception e) {
                return "HATA";
            }


        }

        @Override
        protected void onPostExecute(String results) {


            progress.dismiss();

        }
    }

    private class skinEkle extends AsyncTask<String, String, String> {
        BlankFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values) {
            RiotApiHelper raHelper = new RiotApiHelper();
            String gelendata = "";
            try {

                for (int i = 0; i< champions.size();i++) {
                    for (int j=0;j<champions.get(i).getChampionSkinObjects().size();j++){
                        gelendata = raHelper.readURL("http://ggeasylol.com/admin/skin_ekle.php?championID="+champions.get(i).getChampionID()
                                +"&language=de"+"&skinName="+champions.get(i).getChampionSkinObjects().get(j).getSkinName().replaceAll(" ","_").replaceAll("'","")
                                +"&skinPng="+champions.get(i).getChampionSkinObjects().get(j).getNum()
                                +"&championKey="+champions.get(i).getChampionSkinObjects().get(j).getKey());
                    }

                }


                return gelendata;

            } catch (Exception e) {
                return "HATA";
            }


        }

        @Override
        protected void onPostExecute(String results) {


            progress.dismiss();

        }
    }
    private class championEkle extends AsyncTask<String, String, String> {
        BlankFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values) {
            RiotApiHelper raHelper = new RiotApiHelper();
            String gelendata = "";
            try {

                for (int i = 0; i< champions.size();i++) {
                    gelendata = raHelper.readURL("http://ggeasylol.com/admin/champion_ekle.php?championID="+champions.get(i).getChampionID()
                            +"&championKey="+champions.get(i).getChampionKey()
                            +"&championName="+champions.get(i).getChampionName().replaceAll(" ","_").replaceAll("'",""));

                }


                return gelendata;

            } catch (Exception e) {
                return "HATA";
            }


        }

        @Override
        protected void onPostExecute(String results) {


            progress.dismiss();

        }
    }
    private class tipEkle extends AsyncTask<String, String, String> {
        BlankFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values) {
            RiotApiHelper raHelper = new RiotApiHelper();
            String gelendata = "";
            try {

                for (int i = 0; i< champions.size();i++) {
                    for (int j = 0; j<champions.get(i).getTavsiye().size();j++){
                        gelendata = raHelper.readURL("http://ggeasylol.com/admin/tips_ekle.php?championID="+champions.get(i).getChampionID()
                                +"&language=de"+"&tip="+champions.get(i).getTavsiye().get(j).getAciklama().replaceAll(" ","_").replaceAll("'",""));
                    }


                }


                return gelendata;

            } catch (Exception e) {
                return "HATA";
            }


        }

        @Override
        protected void onPostExecute(String results) {


            progress.dismiss();

        }
    }
    private class anaJson extends AsyncTask<String, String, String> {
        BlankFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {
            RiotApiHelper raHelper = new RiotApiHelper();
            champions=new ArrayList<ChampionServerObject>();
            JSONObject obje1, obje2, obje3;
            dbHelper = new DBHelper( getContext());
            try{
                String gelendata=raHelper.readURL("http://ggeasylol.com/api/pt_BR/championFull.json");

                obje1 = new JSONObject(gelendata);
                obje2 = obje1.getJSONObject("data");

                Iterator<?> key = obje2.keys();
                while (key.hasNext()){
                    obje3 = obje2.getJSONObject((String) key.next());
                    ChampionStatObject championStatObject;
                    ArrayList<TavsiyeObject> tavsiyeObject = new ArrayList<TavsiyeObject>();
                    ArrayList<ChampionSkinObject> championSkinObjects = new ArrayList<ChampionSkinObject>();
                    ArrayList<ChampionSkillObject> championSkillObjects = new ArrayList<ChampionSkillObject>();
                    JSONObject object = obje3.getJSONObject("stats");
                    JSONObject object1 = obje3.getJSONObject("info");
                    JSONArray array = obje3.getJSONArray("enemytips");
                    JSONArray array1 = obje3.getJSONArray("skins");
                    JSONArray array2 = obje3.getJSONArray("spells");
                    for (int i =0; i<array1.length();i++){
                        JSONObject object2 = array1.getJSONObject(i);
                        championSkinObjects.add(new ChampionSkinObject(object2.getString("name"),
                                Integer.parseInt(object2.getString("num")),obje3.getString("id")));
                    }
                    for (int i =0; i<array2.length();i++){
                        JSONObject object2 = array2.getJSONObject(i);
                        JSONObject object3 = object2.getJSONObject("image");
                        championSkillObjects.add(new ChampionSkillObject(object2.getString("name"),
                                object2.getString("description"),object3.getString("full")));
                    }
                    JSONObject object21 = obje3.getJSONObject("passive");
                    JSONObject object22= object21.getJSONObject("image");
                    championSkillObjects.add(new ChampionSkillObject(object21.getString("name"),object21.getString("description"),object22.getString("full")));

                    for (int i =0; i<array.length();i++){
                        tavsiyeObject.add(new TavsiyeObject(array.getString(i)));
                    }
                    championStatObject = new ChampionStatObject(object.getString("armor"),object.getString("armorperlevel")
                            ,object.getString("attackdamage"),object.getString("attackdamageperlevel"),object.getString("attackrange")
                            ,object.getString("hp"),object.getString("hpperlevel"),object.getString("hpregen"),object.getString("hpregenperlevel")
                            ,object.getString("movespeed"),object.getString("mp"),object.getString("mpperlevel"),object.getString("mpregen")
                            ,object.getString("mpregenperlevel"),object.getString("spellblock"),object.getString("spellblockperlevel")
                            ,Integer.parseInt(object1.getString("attack")),Integer.parseInt(object1.getString("defense")),Integer.parseInt(object1.getString("magic"))
                            ,Integer.parseInt(object1.getString("difficulty")));
                    champions.add(new ChampionServerObject(obje3.getString("key"),
                         obje3.getString("id"), obje3.getString("name"),obje1.getString("version"),"","",tavsiyeObject,championSkillObjects,championSkinObjects,championStatObject,null,null));
                }
                dbHelper.insertMatch(obje1.getString("version"), "Gorev32");

                return "0";

            }catch (Exception e){
                return "HATA";
            }



        }

        @Override
        protected void onPostExecute(String results)
        {
            if(results.equals("0")){
                new statEkle().execute();
            }
            else
                Toast.makeText(getActivity(), getString(R.string.ops_make_mistake), Toast.LENGTH_LONG).show();
            progress.dismiss();
        }

    }
}
