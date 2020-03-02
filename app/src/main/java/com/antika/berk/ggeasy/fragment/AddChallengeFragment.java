package com.antika.berk.ggeasy.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.adapter.ChallengeMissionAdapter;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.MissionHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.MatchIdObject;
import com.antika.berk.ggeasy.object.SummonerObject;
import com.antika.berk.ggeasy.object.UserObject;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddChallengeFragment extends DialogFragment {

    GridView mission_gv;
    DBHelper dbHelper;
    TextView puan;
    Button add_btn;
    MissionHelper missionHelper;
    ChallangeFragment cf;
    public void setChallengeFragment(ChallangeFragment cf){
        this.cf=cf;
    }

    int secili_gorev = 999999999;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_challenge, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mission_gv=(GridView)view.findViewById(R.id.mission_gv);
        puan=(TextView)view.findViewById(R.id.textView59);
        add_btn = (Button) view.findViewById(R.id.button12);
        missionHelper=new MissionHelper(getContext());
        ChallengeMissionAdapter adapter1=new ChallengeMissionAdapter(getActivity());
        mission_gv.setAdapter(adapter1);
        final View[] row2 = new View[1];
        mission_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (row2[0] != null) {
                    row2[0].setBackgroundResource(android.R.color.transparent);
                }
                row2[0] = view;
                view.setBackgroundResource(R.color.primary_light);
                secili_gorev = position;
                puan.setText("x "+missionHelper.gorev_puan.get(position));
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( secili_gorev != 999999999)
                    new addChallenge().execute(""+(secili_gorev+1));
                else
                    Toast.makeText(getContext(),getContext().getString(R.string.try_again), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
    private class addChallenge extends AsyncTask<String,String,String> {

        BlankFragment progress;
        MatchIdObject user1MatchId;

        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... strings) {
            RiotApiHelper apiHelper=new RiotApiHelper();
            dbHelper=new DBHelper(getContext());
            UserObject uo=dbHelper.getUser();
            SummonerObject so=apiHelper.getSumonner(uo.getEmail(),uo.getRegion());
            try{
                user1MatchId=apiHelper.getMatchIDs(so.getAccountId(),uo.getRegion(),1).get(0);
                JSONObject object;
                if (user1MatchId.getMatchID()!= null ){
                    object = new JSONObject( apiHelper.readURL("http://ggeasylol.com/json/set_challenge.php?userName="+dbHelper.getUser().getEmail().replaceAll(" ","_")+"&region="+dbHelper.getUser().getRegion()+"&mission="+strings[0]+"&matchID="+user1MatchId.getMatchID()));
                    return object.getString("durum");
                }
                else
                    return "HATA";

            }
            catch (Exception e){
                return "HATA";
            }



        }

        @Override
        protected void onPostExecute(String s) {
            progress.dismiss();
            if(!s.equals("HATA")){
                if (s.equals("yeniyok")){

                }
                else if (s.equals("bekleyen")){

                }
                else if (s.equals("bulundu")){

                }
                else { //bulundu

                }
                cf.yenile();
                getDialog().dismiss();

            }

            else
                Toast.makeText(getContext(),s, Toast.LENGTH_LONG).show();
        }

    }
}
