package com.antika.berk.ggeasy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.adapter.ChampionSkillAdapter;
import com.antika.berk.ggeasy.object.ChampionServerObject;


public class SkillFragment extends Fragment {
    ListView skilllist;
    TextView skill_tv;
    private ChampionServerObject co;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_skill, container, false);
        skilllist=(ListView) view.findViewById(R.id.skillView);
        skill_tv=(TextView)view.findViewById(R.id.title);

        Bundle bundle = this.getArguments();
        co = (ChampionServerObject) bundle.getParcelableArrayList("cID");
        ChampionSkillAdapter adapter=new ChampionSkillAdapter(getActivity(),co.getChampionSkillObjects());
        skilllist.setAdapter(adapter);
        return view;
    }






}
