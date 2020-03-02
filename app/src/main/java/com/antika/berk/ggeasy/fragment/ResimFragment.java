package com.antika.berk.ggeasy.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.antika.berk.ggeasy.R;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResimFragment extends DialogFragment {

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }
    ImageView resim;
    Button back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resim, container, false);
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        resim = (ImageView)view.findViewById(R.id.imageView11);
        back = (Button)view.findViewById(R.id.button5);
        Picasso.with(getContext()).load(url).into(resim);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getDialog().dismiss();
            }
        });
        return view;
    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }
}
