package com.antika.berk.ggeasy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.DBHelper;


public class FourSkillTabHost extends Fragment {

    ImageView profile;
    private FragmentTabHost mTabHost;
    Intent intent;
    DBHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_four_skill_tab_host, container, false);
        profile      = (ImageView  ) view.findViewById(R.id.imageView17 );
        mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("fragmenta").setIndicator("",getResources().getDrawable(R.drawable.fourskills)).setContent(intent),
                FourSkillFragment.class,null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator("",getResources().getDrawable(R.drawable.passive)).setContent(intent),
                PassiveSkillFragment.class, null);
        dbHelper = new DBHelper(getContext());
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

        return view;
    }

}
