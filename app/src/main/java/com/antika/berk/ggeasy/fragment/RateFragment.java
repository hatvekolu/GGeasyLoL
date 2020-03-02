package com.antika.berk.ggeasy.fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.antika.berk.ggeasy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateFragment extends DialogFragment {

    Button yes,no;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate, container, false);
        yes = (Button)view.findViewById(R.id.button10);
        no = (Button)view.findViewById(R.id.button11);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    getDialog().dismiss();
                    startActivity(myAppLinkToMarket);

                } catch (ActivityNotFoundException e) {
                    getDialog().dismiss();
                }
            }
        });

        return view;
    }

}
