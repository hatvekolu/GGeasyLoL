package com.antika.berk.ggeasy.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.SummonerObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends DialogFragment {
    DBHelper dbHelper;
    Button register;
    EditText et_username;
    Spinner sp_server;
    SummonerObject so;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        register     = (Button     ) view.findViewById(R.id.button2     );
        et_username  = (EditText   ) view.findViewById(R.id.editText2   );
        sp_server    = (Spinner    ) view.findViewById(R.id.spinner2    );
        List<String> categories = new ArrayList<String>();
        categories.add("TR"  );categories.add("EUNE");categories.add("EUW" );categories.add("JP"  );
        categories.add("KR"  );categories.add("LAN" );categories.add("LAS" );categories.add("NA"  );
        categories.add("OCE" );categories.add("RU"  );categories.add("BR"  );categories.add("PBE" );
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_server.setAdapter(dataAdapter);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_username.getText().length() > 0)
                    new getData().execute(et_username.getText().toString(),sp_server.getSelectedItem().toString());
                else
                    Toast.makeText(getContext(), getString(R.string.set_summoner_name), Toast.LENGTH_LONG).show();
            }
        });


        return view;

    }
    private class getData extends AsyncTask<String, String, String> {
        BlankFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values) {
            dbHelper = new DBHelper(getActivity());
            RiotApiHelper raHelper = new RiotApiHelper();

            so = raHelper.getSumonner(values[0], values[1]);
            if (so == null) {
                return getString(R.string.check_summoner_name_or_region);
            }
            else
                dbHelper.insertUser(so.getName(),"",values[1],"");


            return null;
        }

        @Override
        protected void onPostExecute(String results) {
            if (results == null) {
                getDialog().dismiss();
                SumonnerFragment cmf = new SumonnerFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(
                        R.id.content_main_page,
                        cmf,"0").commit();
            } else
                Toast.makeText(getContext(), getContext().getString(R.string.check_summoner_name_or_region), Toast.LENGTH_LONG).show();
            progress.dismiss();
        }
    }
}
