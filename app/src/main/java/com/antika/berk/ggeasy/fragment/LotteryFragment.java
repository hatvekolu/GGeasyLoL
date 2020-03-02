package com.antika.berk.ggeasy.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyReward;
import com.adcolony.sdk.AdColonyRewardListener;
import com.adcolony.sdk.AdColonyZone;
import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.adapter.SumonnersAdapter;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.SummonerLottery;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class LotteryFragment extends  Fragment implements DialogInterface.OnDismissListener {

    final private String APP_ID = "app2a54361ac94b4f07aa";
    final private String ZONE_ID = "vz9421fe4bfa5c4b2195";
    final private String TAG = "Lottery";

    private AdColonyInterstitial ad;
    private AdColonyInterstitialListener listener;
    private AdColonyAdOptions ad_options;

    //**********************************************************************************************


    ImageView iv_image;
    TextView tv_name, tv_odul, tv_date, tv_person, tv_katildiniz;
    Button btn_join;
    ListView lv_persons;

    ProgressBar pb_wait;
    boolean show = false;

    LotteryObject lo;
    List<SummonerLottery> summoners = new ArrayList<SummonerLottery>();
    SumonnersAdapter adapter;

    public void setLottery(LotteryObject lo){this.lo = lo;}
    @Override
    public void onResume() {
        super.onResume();
        if(show){
            FragmentManager fm = getFragmentManager();
            JoinLotteryFragment asf = new JoinLotteryFragment();
            asf.setFragment(LotteryFragment.this);
            asf.setLottery(lo);
            asf.show(fm, "Add Sumonner");
            show = false;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_lottery, container, false);
        pb_wait       = (ProgressBar) view.findViewById(R.id.progressBar2);
        iv_image      = (ImageView  ) view.findViewById(R.id.imageView5  );
        tv_name       = (TextView   ) view.findViewById(R.id.textView13  );
        tv_odul       = (TextView   ) view.findViewById(R.id.textView42  );
        tv_date       = (TextView   ) view.findViewById(R.id.textView39  );
        tv_person     = (TextView   ) view.findViewById(R.id.textView40  );
        tv_katildiniz = (TextView   ) view.findViewById(R.id.textView43  );
        btn_join      = (Button     ) view.findViewById(R.id.button4     );
        lv_persons    = (ListView   ) view.findViewById(R.id.list_view   );

        Picasso.with(getContext()).load("http://ggeasylol.com/img/" + lo.getImg()).into(iv_image);
        tv_name.setText(lo.getName());
        tv_odul.setText(lo.getOdul());
        tv_date.setText(getString(R.string.end_date) + lo.getEnd_date());

        if(lo.getStatus().equals("0")){
            tv_person.setText(getString(R.string.continues));
        }
        else if(lo.getStatus().equals("1")){
            btn_join.setVisibility(View.GONE);
            tv_person.setText(getString(R.string.drawing));
        }
        else{
            btn_join.setVisibility(View.GONE);
            tv_person.setText(getString(R.string.winners_on_facebook));

            tv_person.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    try {
                        getContext().getPackageManager()
                                .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
                        intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("fb://page/225183367912890")); //Trys to make intent with FB's URI
                    } catch (Exception e) {
                        intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/GGEasyTR/")); //catches and opens a url to the desired page
                    }
                    startActivity(intent);
                }
            });
        }

        AdColonyAppOptions app_options = new AdColonyAppOptions()
                .setUserID( "unique_user_id" )
                .setKeepScreenOn(true);


        AdColony.configure( getActivity(), app_options, APP_ID, ZONE_ID );

        ad_options = new AdColonyAdOptions().enableConfirmationDialog(false).enableResultsDialog(false);

        AdColony.setRewardListener( new AdColonyRewardListener()
        {
            @Override
            public void onReward( AdColonyReward reward )
            {
                Log.d( TAG, "onReward" );//ÖDÜL KZANMA KODLARI BURAYA
            }
        } );

        listener = new AdColonyInterstitialListener()
        {
            @Override
            public void onRequestFilled( AdColonyInterstitial ad )
            {
                if(lo.getStatus().equals("0")) {
                    LotteryFragment.this.ad = ad;
                    btn_join.setVisibility(View.VISIBLE);
                    pb_wait.setVisibility(View.GONE);
                }
                Log.d( TAG, "onRequestFilled" );
            }
            @Override
            public void onRequestNotFilled( AdColonyZone zone )
            {
                pb_wait.setVisibility(View.GONE);
                Log.d( TAG, "onRequestNotFilled");
            }
            @Override
            public void onOpened( AdColonyInterstitial ad )
            {
                btn_join.setVisibility(View.GONE);
                pb_wait.setVisibility(View.GONE);
                Log.d( TAG, "onOpened" );
            }
            @Override
            public void onExpiring( AdColonyInterstitial ad )
            {
                btn_join.setVisibility(View.GONE);
                pb_wait.setVisibility(View.GONE);
                Log.d( TAG, "onExpiring" );
            }
        };
        AdColony.requestInterstitial( ZONE_ID, listener, ad_options );
        btn_join.setVisibility(View.GONE);

        Picasso.with(getContext()).load("http://ggeasylol.com/img/" + lo.getImg()).into(iv_image);
        tv_name.setText(lo.getName());
        tv_odul.setText(lo.getOdul());
        tv_date.setText(getString(R.string.end_date) + lo.getEnd_date());

        if(lo.getStatus().equals("0")){
            tv_person.setText(getString(R.string.continues));
        }
        else if(lo.getStatus().equals("1")){
            btn_join.setVisibility(View.GONE);
            pb_wait.setVisibility(View.GONE);
            tv_person.setText(getString(R.string.drawing));
        }
        else{
            btn_join.setVisibility(View.GONE);
            pb_wait.setVisibility(View.GONE);
            tv_person.setText(getString(R.string.winners_on_facebook));

            tv_person.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    try {
                        getContext().getPackageManager()
                                .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
                        intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("fb://page/225183367912890")); //Trys to make intent with FB's URI
                    } catch (Exception e) {
                        intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/GGEasyTR/")); //catches and opens a url to the desired page
                    }
                    startActivity(intent);
                }
            });
        }

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.show();
                show = true;
            }

        });

        new getData().execute(lo.getID());

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        new getData().execute(lo.getID());
        btn_join.setVisibility(View.GONE);
    }


    private class getData extends AsyncTask<String, String, String> {
        BlankFragment progress;
        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
            summoners.clear();
        }
        @Override
        protected String doInBackground(String... values)
        {
            String data = new RiotApiHelper().readURL("http://ggeasylol.com/json/get_lottery_joins.php?id=" + values[0]);

            try{
                JSONArray array1 = new JSONArray(data);
                if(array1.length() <= 0) return "0";
                JSONObject obje1;

                for (int i = 0; i < array1.length(); i++){
                    obje1 = array1.getJSONObject(i);
                    summoners.add(new SummonerLottery( obje1.getString("userName"),
                            obje1.getString("userRegion"), obje1.getString("userIcon"),obje1.getInt("durum")));
                }
            }catch (Exception e){}

            return null;
        }
        @Override
        protected void onPostExecute(String results)
        {
            adapter = new SumonnersAdapter(getActivity(), summoners);
            lv_persons.setAdapter(adapter);
            progress.dismiss();
        }
    }
    public void  yenile (){
        new getData().execute(lo.getID());
    }

}