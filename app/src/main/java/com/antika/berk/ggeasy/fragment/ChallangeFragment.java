package com.antika.berk.ggeasy.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.adapter.ChallengeAdapter;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.MissionHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.ChallengeObject;
import com.antika.berk.ggeasy.object.UserObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChallangeFragment extends Fragment {
    ImageView profile;
    FloatingActionButton add_cha;
    ListView listView;
    DBHelper dbHelper;
    UserObject uo;
    List<ChallengeObject> challengeObjects;
    MissionHelper missionHelper;
    TextView name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_challange, container, false);

        add_cha=(FloatingActionButton)view.findViewById(R.id.floatingActionButton) ;
        profile      = (ImageView  ) view.findViewById(R.id.imageView32 );
        listView=(ListView)view.findViewById(R.id.challengeView) ;
        name = (TextView)view.findViewById(R.id.textView31);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbHelper.getUser() == null) {
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
        add_cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbHelper.getUser().getEmail().length()>0){


                    FragmentManager fm = getFragmentManager();
                    AddChallengeFragment asf = new AddChallengeFragment();
                    asf.setChallengeFragment(ChallangeFragment.this);
                    asf.show(fm, "");

                }
                else
                    Toast.makeText(getContext(),getContext().getString(R.string.pls_register),Toast.LENGTH_LONG).show();

            }
        });
        new getData().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getFragmentManager();
                ChallengeOpenFragment asf = new ChallengeOpenFragment();
                asf.setChallengeObject(challengeObjects.get(position));
                asf.setChallengeFragment(ChallangeFragment.this);
                asf.show(fm, "");
            }
        });
        return view;
    }
    private class getData extends AsyncTask<String,String,String> {

        BlankFragment progress;



        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
            challengeObjects=new ArrayList<ChallengeObject>();
        }

        @Override
        protected String doInBackground(String... strings) {
            RiotApiHelper riotApiHelper = new RiotApiHelper();
            dbHelper=new DBHelper(getContext());
            missionHelper=new MissionHelper(getContext());
            uo=dbHelper.getUser();
            try {
                challengeObjects.clear();
                String gelenData=riotApiHelper.readURL("http://ggeasylol.com/json/get_challenge.php?SihirdarAdi="+uo.getEmail().replaceAll(" ","_")+"&Region="+uo.getRegion());
                JSONArray array=new JSONArray(gelenData);
                for(int i=0;i<array.length();i++){
                    JSONObject obj1=array.getJSONObject(i);
                    challengeObjects.add(new ChallengeObject(obj1.getString("ID"),obj1.getString("tarih"),obj1.getString("mission"),
                            obj1.getString("user1Match"),obj1.getString("user2Match"),obj1.getString("status"),
                            obj1.getString("user1Name"),obj1.getString("user2Name"),
                            obj1.getString("user1Region"),obj1.getString("user2Region"),obj1.getString("user1Icon"),obj1.getString("user2Icon"),missionHelper.gorev_txt.get((Integer.parseInt(obj1.getString("mission")))-1), obj1.getBoolean("winnerUser")));


                }


                return "0";

            }



            catch (Exception e) {
                return "HATA";

            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(!s.equals("HATA")){
                ChallengeAdapter adapter=new ChallengeAdapter(getActivity(),challengeObjects);
                listView.setAdapter(adapter);
                name.setText(dbHelper.getUser().getEmail());
            }
            else{
                Toast.makeText(getContext(),getContext().getString(R.string.ops_make_mistake),Toast.LENGTH_LONG).show();
                add_cha.setVisibility(View.GONE);
            }

            progress.dismiss();


        }
    }
    public void yenile(){
        new getData().execute();

    }
}
