package com.antika.berk.ggeasy.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.ChampionMasterObject;
import com.antika.berk.ggeasy.object.CurrentGameObject;
import com.antika.berk.ggeasy.object.LeagueObject;
import com.antika.berk.ggeasy.object.MasterObject;
import com.antika.berk.ggeasy.object.ParticipantListObject;
import com.antika.berk.ggeasy.object.ParticipantObject;
import com.antika.berk.ggeasy.object.SummonerObject;
import com.antika.berk.ggeasy.object.UserObject;

import java.util.ArrayList;
import java.util.List;

public class CurrentMatchFragment extends Fragment {
    EditText et_username;
    Spinner sp_server;
    Button bt_getdata;
    DBHelper dbHelper;
    UserObject uo;
    ListView fri_lv;
    boolean pre=false;
    List<ParticipantListObject> participantsItems = new ArrayList<ParticipantListObject>();
    String summonerName;
    ImageView profile;
    CurrentGameObject cgo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_current_match, container, false);

        //SET VIEWS*********************************************************************************
        et_username  = (EditText   ) view.findViewById(R.id.editText2   );
        sp_server    = (Spinner    ) view.findViewById(R.id.spinner2    );
        bt_getdata   = (Button     ) view.findViewById(R.id.button2     );
        profile      = (ImageView  ) view.findViewById(R.id.imageView13 );
        //******************************************************************************************

        //SPİNNER SETTINGS**************************************************************************
        List<String> categories = new ArrayList<String>();
        categories.add("TR"  );categories.add("EUNE");categories.add("EUW" );categories.add("JP"  );
        categories.add("KR"  );categories.add("LAN" );categories.add("LAS" );categories.add("NA"  );
        categories.add("OCE" );categories.add("RU"  );categories.add("BR"  );categories.add("PBE" );
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_server.setAdapter(dataAdapter);
        //******************************************************************************************
        dbHelper = new DBHelper(getContext());
        bt_getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_username.getText().length() > 0)
                    new getData().execute(et_username.getText().toString(), sp_server.getSelectedItem().toString());
                else
                    Toast.makeText(getContext(), getString(R.string.set_summoner_name), Toast.LENGTH_LONG).show();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbHelper.getUser().getEmail().length()== 0) {
                    FragmentManager fm = getFragmentManager();
                    LoginFragment alertDialog = new LoginFragment();
                    alertDialog.show(fm,"fragment_alert");
                }
                else {
                    ProfilFragment cmf = new ProfilFragment();
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(
                            R.id.content_main_page,
                            cmf,"0").commit();
                }

            }
        });

        return view;
    }



    public class getData extends AsyncTask<String, String, String> {

        RiotApiHelper raHelper = new RiotApiHelper();

        BlankFragment progress;
        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... strings) {
            SummonerObject so;
            List<MasterObject> masterObjects=new ArrayList<MasterObject>();
            so = raHelper.getSumonner(strings[0], strings[1]);
            if (so == null){return getString(R.string.check_summoner_name_or_region);}
            summonerName = so.getName();
            cgo = raHelper.getCurrentMatch(so.getId(), strings[1]);
            if (cgo == null){return getString(R.string.summoner_isnt_play_game);}
            participantsItems.clear();
            for (int i = 0; i < cgo.getParticipants().size(); i++){
                ParticipantObject part = cgo.getParticipants().get(i);
                List<LeagueObject> leagues = raHelper.getSummonerLeague(""+part.getSummonerId(), strings[1]);
                LeagueObject lo;
                masterObjects=part.getMasterObjects();
                try {lo = leagues.get(0);}catch (Exception e){lo = new LeagueObject("","","","",0,0,0,false,false,false,false,"",0,0,0);}
                List<ChampionMasterObject>cmo=raHelper.getMasteries(part.getSummonerId(),strings[1],part.getChampionId());
                ChampionMasterObject masterObject;
                try {masterObject=cmo.get(0);}catch (Exception e){masterObject=new ChampionMasterObject(0,0,0,0,0,0,"",false);}
                participantsItems.add(new ParticipantListObject(part.getSummonerName(),
                        part.getTeamId(), part.getChampionId(), part.getSpell1Id(),
                        part.getSpell2Id(), lo.getTier(), lo.getDivision(), lo.getLeaguePoints(),
                        lo.getMiniSeriesprogress(),masterObject.getChampionPoints(),masterObject.getChampionLevel(),masterObjects
                ));





                if (dbHelper.getChampion(Integer.toString(part.getChampionId())) == null)
                    dbHelper.insertChampion(raHelper.getStaticChampion(part.getChampionId()));

                if (dbHelper.getSpell(Integer.toString(part.getSpell1Id())) == null)
                    dbHelper.insertSpell(raHelper.getStaticSpell(part.getSpell1Id()));

                if (dbHelper.getSpell(Integer.toString(part.getSpell2Id())) == null)
                    dbHelper.insertSpell(raHelper.getStaticSpell(part.getSpell2Id()));
            }
            return "0";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.equals("0")) {
                CurrentMatchOpenFragment cmof = new CurrentMatchOpenFragment();
                cmof.setParameters(cgo, summonerName, participantsItems);
                CurrentMatchFragment.this.getFragmentManager().beginTransaction()
                        .replace(R.id.content_main_page, cmof, "")
                        .addToBackStack(null)
                        .commit();

                View view1 = getActivity().getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);}

            }
            else
            {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
            progress.dismiss();
        }
    }


}
