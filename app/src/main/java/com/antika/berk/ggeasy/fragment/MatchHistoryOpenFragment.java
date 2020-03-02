package com.antika.berk.ggeasy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.adapter.MatchHistoryAdapter;
import com.antika.berk.ggeasy.object.MatchHistoryObject;

import java.util.List;


public class MatchHistoryOpenFragment extends Fragment {

    List<MatchHistoryObject>matchHistoryObjects;
    public void setData(List<MatchHistoryObject>matchHistoryObjects){

        this.matchHistoryObjects=matchHistoryObjects;
    }
    ListView history;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_match_history_open, container, false);
        history=(ListView)view.findViewById(R.id.history);
        MatchHistoryAdapter adapter=new MatchHistoryAdapter(getActivity(),matchHistoryObjects);
        history.setAdapter(adapter);

        return view;
    }

}
