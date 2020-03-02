package com.antika.berk.ggeasy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.object.ChampionServerObject;


public class ChampionTabHost extends DialogFragment {
    private FragmentTabHost mTabHost;
    private TextView c_name;
    Intent intent;

    private ChampionServerObject co;

    public void setItem(ChampionServerObject co) {
        this.co = co;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_champion_tab_host, container, false);
        c_name=(TextView)view.findViewById(R.id.textView46) ;
        mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        c_name.setText(co.getChampionName());
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("cID", co);
        mTabHost.addTab(mTabHost.newTabSpec("fragmenta").setIndicator("",getResources().getDrawable(R.drawable.ct)).setContent(intent),
                CounterFragment.class,bundle);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("",getResources().getDrawable(R.drawable.skill1)).setContent(intent),
                SkillFragment.class,bundle);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentd").setIndicator("",getResources().getDrawable(R.drawable.skin)).setContent(intent),
                SkinFragment.class, bundle);
        mTabHost.addTab(mTabHost.newTabSpec("fragmente").setIndicator("",getResources().getDrawable(R.drawable.stat)).setContent(intent),
                StatFragment.class, bundle);


        return view;
    }
}
