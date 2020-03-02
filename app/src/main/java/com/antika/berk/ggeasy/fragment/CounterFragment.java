package com.antika.berk.ggeasy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.adapter.TavsiyeAdapter;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.ChampionServerObject;

import it.sephiroth.android.library.picasso.Picasso;


public class CounterFragment extends Fragment {
    private ChampionServerObject co;

    ImageView logo,c1,c2,c3,c4,c5,a1,a2,a3,a4,a5;
    TextView ct1,ct2,ct3,ct4,ct5,at1,at2,at3,at4,at5,against,counter;

    ListView tavsiyeler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_counter, container, false);
        Bundle bundle = this.getArguments();
        co = (ChampionServerObject) bundle.getParcelableArrayList("cID");

        against=(TextView) view.findViewById(R.id.textView66);
        counter=(TextView) view.findViewById(R.id.textView64);
        tavsiyeler=(ListView)view.findViewById(R.id.tav_lv);


        logo=(ImageView)view.findViewById(R.id.imageView41);


        c1=(ImageView)view.findViewById(R.id.imageView46);
        c2=(ImageView)view.findViewById(R.id.imageView45);
        c3=(ImageView)view.findViewById(R.id.imageView44);
        c4=(ImageView)view.findViewById(R.id.imageView43);
        c5=(ImageView)view.findViewById(R.id.imageView42);

        a1=(ImageView)view.findViewById(R.id.imageView50);
        a2=(ImageView)view.findViewById(R.id.imageView52);
        a3=(ImageView)view.findViewById(R.id.imageView51);
        a4=(ImageView)view.findViewById(R.id.imageView48);
        a5=(ImageView)view.findViewById(R.id.imageView47);

        ct1=(TextView) view.findViewById(R.id.textView77);
        ct2=(TextView) view.findViewById(R.id.textView76);
        ct3=(TextView) view.findViewById(R.id.textView75);
        ct4=(TextView) view.findViewById(R.id.textView74);
        ct5=(TextView) view.findViewById(R.id.textView73);

        at1=(TextView)view.findViewById(R.id.textView82);
        at2=(TextView)view.findViewById(R.id.textView81);
        at3=(TextView)view.findViewById(R.id.textView80);
        at4=(TextView)view.findViewById(R.id.textView79);
        at5=(TextView)view.findViewById(R.id.textView78);
        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + co.getVersion() + "/img/champion/" + co.getChampionKey() + ".png").into(logo);

        against.setText(co.getChampionName()+" "+getContext().getString(R.string.strong));
        counter.setText(co.getChampionName()+" "+getContext().getString(R.string.weak));

        TavsiyeAdapter adapter=new TavsiyeAdapter(getActivity(),co.getTavsiye());
        tavsiyeler.setAdapter(adapter);

        ct1.setText(co.getCo().getCounter1());
        ct2.setText(co.getCo().getCounter2());
        ct3.setText(co.getCo().getCounter3());
        ct4.setText(co.getCo().getCounter4());
        ct5.setText(co.getCo().getCounter5());

        at1.setText(co.getAo().getCounter1());
        at2.setText(co.getAo().getCounter2());
        at3.setText(co.getAo().getCounter3());
        at4.setText(co.getAo().getCounter4());
        at5.setText(co.getAo().getCounter5());

        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/champion/" + co.getCo().getCounter1() + ".png").into(c1);
        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/champion/" + co.getCo().getCounter2() + ".png").into(c2);
        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/champion/" + co.getCo().getCounter3() + ".png").into(c3);
        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/champion/" + co.getCo().getCounter4() + ".png").into(c4);
        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/champion/" + co.getCo().getCounter5() + ".png").into(c5);

        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/champion/" + co.getAo().getCounter1() + ".png").into(a1);
        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/champion/" + co.getAo().getCounter2() + ".png").into(a2);
        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/champion/" + co.getAo().getCounter3() + ".png").into(a3);
        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/champion/" + co.getAo().getCounter4() + ".png").into(a4);
        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/champion/" + co.getAo().getCounter5() + ".png").into(a5);


        return view;
    }



}
