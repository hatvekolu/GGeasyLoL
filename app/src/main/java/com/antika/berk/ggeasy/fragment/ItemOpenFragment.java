package com.antika.berk.ggeasy.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.adapter.ItemFromAdapter;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.ItemObject;

import it.sephiroth.android.library.picasso.Picasso;


public class ItemOpenFragment extends DialogFragment {
    ImageView logo;
    TextView isim,aciklama,gold;
    RiotApiHelper riotApiHelper ;
    GridView from,to;
    private ItemObject io;

    public void setItem(ItemObject io) {
        this.io = io;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_item_open, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        logo=(ImageView)view.findViewById(R.id.imageView37);
        isim=(TextView)view.findViewById(R.id.textView14);
        gold=(TextView)view.findViewById(R.id.textView20);
        aciklama=(TextView) view.findViewById(R.id.textView24);
        from=(GridView)view.findViewById(R.id.gerekli);
        to=(GridView)view.findViewById(R.id.gelis);
        riotApiHelper=new RiotApiHelper();


        if(io.getFrom().size()>0){
            ItemFromAdapter adapter=new ItemFromAdapter(getActivity(),io.getFrom());
            from.setAdapter(adapter);
        }
        if(io.getTo().size()>0){
            ItemFromAdapter adapter1=new ItemFromAdapter(getActivity(),io.getTo());
            to.setAdapter(adapter1);
        }

        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/"+io.getVersion()+"/img/item/"+io.getId()).into(logo);
        isim.setText(io.getName());
        aciklama.setText(io.getAciklama());
        gold.setText(io.getGold());

        return view;
    }


}
