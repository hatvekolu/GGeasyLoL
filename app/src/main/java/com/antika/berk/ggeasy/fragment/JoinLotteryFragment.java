package com.antika.berk.ggeasy.fragment;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.SummonerObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinLotteryFragment extends DialogFragment {

    SummonerObject so;

    Button bt_add;
    EditText summonerName;
    Spinner spinner;
    private LotteryFragment f;
    LotteryObject lo;
    public JoinLotteryFragment() {}
    public void setFragment(LotteryFragment f)
    {
        this.f = f;
    }
    public void setLottery(LotteryObject lo){
        this.lo = lo;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (f instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) f).onDismiss(dialog);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_join_lottery, container, false);
        summonerName = (EditText)view.findViewById(R.id.editText4) ;
        spinner = (Spinner) view.findViewById(R.id.spinner);
        bt_add           = (Button  ) view.findViewById(R.id.button3 );
        List<String> categories = new ArrayList<String>();
        categories.add("TR"  );categories.add("EUNE");categories.add("EUW" );categories.add("JP"  );
        categories.add("KR"  );categories.add("LAN" );categories.add("LAS" );categories.add("NA"  );
        categories.add("OCE" );categories.add("RU"  );categories.add("BR"  );categories.add("PBE" );
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (summonerName.getText().length() > 0)
                    new checkUser().execute(summonerName.getText().toString(), spinner.getSelectedItem().toString());
                else
                    Toast.makeText(getContext(),getString(R.string.check_summoner_name_or_region),Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
    private class checkUser extends AsyncTask<String, String, String> {
        BlankFragment progress;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {
            try
            {
                RiotApiHelper riotApiHelper = new RiotApiHelper();

                so = riotApiHelper.getSumonner(values[0],values[1]);

                if(so == null)
                    return "0";

                String link = riotApiHelper.readURL("http://ggeasylol.com/json/join_lottery.php?userID=" + so.getAccountId() +
                        "&lotteryID=" + lo.getID() + "&userName=" + so.getName().replaceAll(" ","_") +
                        "&userIcon=" + so.getIcon() + "&userRegion=" + values[1]) ;
                return "1";
            }
            catch (Exception e){

                return "0";}
        }

        @Override
        protected void onPostExecute(String results)
        {
            if (results.equals("1")){
                progress.dismiss();
                f.yenile();
                dismiss();
            }
            else {
                Toast.makeText(getContext(),getString(R.string.check_summoner_name_or_region),Toast.LENGTH_LONG).show();
                progress.dismiss();
            }



        }
    }
}
