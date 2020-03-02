package com.antika.berk.ggeasy.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.adapter.PassiveAdapter;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.FourSkillObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PassiveSkillFragment extends Fragment {
    PassiveAdapter adapter;
    List<FourSkillObject> champions=new ArrayList<FourSkillObject>();
    GridView gv;
    int x,y;
    DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_passive_skill, container, false);
        gv=(GridView)view.findViewById(R.id.gv_pass);
        dbHelper =new DBHelper(getContext());

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(Integer.parseInt(dbHelper.getMatch("Gorev30"))>champions.size()-1 && position<=Integer.parseInt(dbHelper.getMatch("Gorev31"))){
                    FourSkillObject data= (FourSkillObject) adapter.getItem(position);
                    FragmentActivity activity = (FragmentActivity)(getContext());
                    FragmentManager fm = activity.getSupportFragmentManager();
                    PassiveOpenFragment alertDialog = new PassiveOpenFragment();
                    alertDialog.setItem(data);
                    alertDialog.setFragment(PassiveSkillFragment.this);
                    alertDialog.setVeri(position);
                    alertDialog.show(fm,"fragment_alert");
                }

            }
        });


        new getData().execute();

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

            try{
                champions.clear();
                String gelendata=raHelper.readURL("http://ggeasylol.com/json/get_skills.php?durum=1");
                JSONArray array=new JSONArray(gelendata);
                String [] list = new String[5];
                for (int i=0;i<array.length();i++) {
                    JSONObject object = array.getJSONObject(i);
                    JSONArray array1 = object.getJSONArray("skills");
                    for (int j = 0; j < array1.length(); j++) {
                        JSONObject object1 = array1.getJSONObject(j);
                        list[j] = object1.getString("skill");
                    }


                    champions.add(new FourSkillObject("" + i, list[0], list[1], list[2],
                            list[3], list[4], object.getString("championName"), object.getString("version")
                    ));
                }


                return "0";

            }catch (Exception e){
                return "HATA";
            }


        }

        @Override
        protected void onPostExecute(String results)
        {
            if(!results.equals("HATA")) {

                adapter=new PassiveAdapter(getActivity(),champions);
                gv.setAdapter(adapter);

            }

            else
                Toast.makeText(getActivity(), getString(R.string.ops_make_mistake), Toast.LENGTH_LONG).show();
            progress.dismiss();
        }
    } public  void yenile(){
        adapter.notifyDataSetChanged();
    }

}
