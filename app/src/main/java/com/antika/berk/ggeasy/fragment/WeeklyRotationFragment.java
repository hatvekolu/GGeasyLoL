package com.antika.berk.ggeasy.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.adapter.ChampionServerAdapter;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.ChampionObject;
import com.antika.berk.ggeasy.object.ChampionServerObject;

import java.util.ArrayList;
import java.util.List;


public class WeeklyRotationFragment extends Fragment {
    GridView gridView;
    List<ChampionObject> championObjects=new ArrayList<ChampionObject>();
    ChampionServerAdapter adapter;
    List<ChampionServerObject> championServerObject;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_rotation, container, false);
        gridView=(GridView) view.findViewById(R.id.grid_view);

        Toast.makeText(getContext(),getContext().getString(R.string.weekly_rotation),Toast.LENGTH_LONG).show();

        new getData().execute();

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
    private class getData extends AsyncTask<String, String, String >
    {
        BlankFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }
        @Override
        protected String doInBackground(String... params) {
            RiotApiHelper riotApiHelper=new RiotApiHelper();
            DBHelper dbHelper=new DBHelper(getContext());
            championObjects.clear();
            championServerObject=new ArrayList<ChampionServerObject>();
            try {
                List<Integer>freeToPlay=riotApiHelper.getChampionFreeToPlay(getString(R.string.language));
                for(int i=0;i<freeToPlay.size();i++){
                    if (dbHelper.getChampion(freeToPlay.get(i).toString()) == null)
                        dbHelper.insertChampion(riotApiHelper.getStaticChampion(Integer.parseInt(freeToPlay.get(i).toString())));

                    championObjects.add(dbHelper.getChampion(freeToPlay.get(i).toString()));
                }
                for (int i=0;i<championObjects.size();i++){

                   championServerObject.add(new ChampionServerObject(championObjects.get(i).getChampionID(),championObjects.get(i).getChampionKey(),
                          championObjects.get(i).getChampionName(),dbHelper.getMatch("Gorev32"),"","",null,null,null,null,null,null));
                }

            return "0";

            }catch (Exception e){
                return "HATA";
            }


        }


        @Override
        protected void onPostExecute(String s) {
            if (s.equals("0")){

                adapter = new ChampionServerAdapter(getActivity(), championServerObject);
                gridView.setAdapter(adapter);
            }
            else Toast.makeText(getContext(),getContext().getString(R.string.ops_make_mistake),Toast.LENGTH_LONG).show();
            progress.dismiss();

        }
    }

}
