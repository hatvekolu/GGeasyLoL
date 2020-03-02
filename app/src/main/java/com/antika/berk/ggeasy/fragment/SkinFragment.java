package com.antika.berk.ggeasy.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.adapter.ChampionSkinAdapter;
import com.antika.berk.ggeasy.object.ChampionServerObject;

import java.util.ArrayList;
import java.util.List;


public class SkinFragment extends Fragment {
    TextView champion;
    private ChampionServerObject co;
    GridView gridview;
    ChampionSkinAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_skin, container, false);
        gridview= (GridView) view.findViewById(R.id.gridview);
        champion=(TextView)view.findViewById(R.id.textView2);
        Bundle bundle = this.getArguments();
        co = (ChampionServerObject) bundle.getParcelableArrayList("cID");
        adapter=new ChampionSkinAdapter(getActivity(),co.getChampionSkinObjects());
        gridview.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkAndRequestPermissions())
            {
                 } else {
                checkAndRequestPermissions();
            }
        }
        else
        {

        }
        return view;
    }

    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int locationPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),1);
            return false;
        }
        return true;
    }


}
