package com.antika.berk.ggeasy.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.MatchHistoryObject;
import com.antika.berk.ggeasy.object.MatchIdObject;
import com.antika.berk.ggeasy.object.SummonerObject;
import com.antika.berk.ggeasy.object.UserObject;

import java.util.ArrayList;
import java.util.List;


public class MatchHistoryFragment extends Fragment {

    List<MatchHistoryObject>matchHistoryObjects;
    List<MatchIdObject>matchIdObjects;
    EditText et_username;
    Spinner sp_server;
    Button bt_getdata;
    DBHelper dbHelper;
    UserObject uo;
    ListView fri_lv;
    SummonerObject so;
    ImageView profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_match_history, container, false);
        et_username  = (EditText   ) view.findViewById(R.id.editText2   );
        sp_server    = (Spinner    ) view.findViewById(R.id.spinner2    );
        bt_getdata   = (Button     ) view.findViewById(R.id.button2     );
        profile      = (ImageView  ) view.findViewById(R.id.imageView17 );
        List<String> categories = new ArrayList<String>();
        categories.add("TR"  );categories.add("EUNE");categories.add("EUW" );categories.add("JP"  );
        categories.add("KR"  );categories.add("LAN" );categories.add("LAS" );categories.add("NA"  );
        categories.add("OCE" );categories.add("RU"  );categories.add("BR"  );categories.add("PBE" );
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_server.setAdapter(dataAdapter);
        bt_getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_username.getText().length() > 0){
                    new getData().execute(et_username.getText().toString(),sp_server.getSelectedItem().toString());

                }
                else
                    Toast.makeText(getContext(), getString(R.string.set_summoner_name), Toast.LENGTH_LONG).show();
            }
        });
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
    private class getData extends AsyncTask<String,String,String> {

        BlankFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... strings) {
            RiotApiHelper apiHelper=new RiotApiHelper();
            matchHistoryObjects=new ArrayList<MatchHistoryObject>();
            matchIdObjects=new ArrayList<MatchIdObject>();

            so=apiHelper.getSumonner(strings[0],strings[1]);
            try {


                matchIdObjects=apiHelper.getMatchIDs(so.getAccountId(),strings[1],12);
                matchHistoryObjects=apiHelper.getHistory(matchIdObjects,strings[1],so.getId());
                for (int i=0; i<matchHistoryObjects.size(); i++){
                    if (dbHelper.getChampion(Integer.toString(matchHistoryObjects.get(i).getChampion())) == null)
                        dbHelper.insertChampion(apiHelper.getStaticChampion(matchHistoryObjects.get(i).getChampion()));

                    if (dbHelper.getSpell(Integer.toString(matchHistoryObjects.get(i).getSpell1())) == null)
                        dbHelper.insertSpell(apiHelper.getStaticSpell(matchHistoryObjects.get(i).getSpell1()));

                    if (dbHelper.getSpell(Integer.toString(matchHistoryObjects.get(i).getSpell2())) == null)
                        dbHelper.insertSpell(apiHelper.getStaticSpell(matchHistoryObjects.get(i).getSpell2()));
                }



                return "tamam";
            }

            catch (Exception e) {

                return "HATA";

            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("HATA"))
                Toast.makeText(getContext(),getContext().getString(R.string.check_summoner_name_or_region),Toast.LENGTH_LONG).show();
            else {
                MatchHistoryOpenFragment cmof = new MatchHistoryOpenFragment();
                cmof.setData(matchHistoryObjects);

                MatchHistoryFragment.this.getFragmentManager().beginTransaction()
                        .replace(R.id.content_main_page, cmof, "")
                        .addToBackStack(null)
                        .commit();

                View view1 = getActivity().getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);}
            }

            progress.dismiss();


        }
    }

}
