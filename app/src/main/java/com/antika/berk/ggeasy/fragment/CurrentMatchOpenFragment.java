package com.antika.berk.ggeasy.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.adapter.ParticipantsAdapter;
import com.antika.berk.ggeasy.object.CurrentGameObject;
import com.antika.berk.ggeasy.object.ParticipantListObject;

import java.util.List;

public class CurrentMatchOpenFragment extends Fragment {
    List<ParticipantListObject> participantsItems;
    CurrentGameObject cgo;
    String summonerName;
    TextView tv_name, tv_gameMode;
    ListView lv_participants;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_match_open, container, false);
        tv_name         = (TextView) view.findViewById(R.id.textView16     );
        tv_gameMode     = (TextView) view.findViewById(R.id.textView22     );
        lv_participants = (ListView) view.findViewById(R.id.participants_lv);

        tv_name.setText(summonerName);

        switch (cgo.getGameQueueConfigId())
        {
            case 830 :tv_gameMode.setText(getContext().getString(R.string.against_bot))  ; break;
            case 840 :tv_gameMode.setText(getContext().getString(R.string.against_bot))  ; break;
            case 850 :tv_gameMode.setText(getContext().getString(R.string.against_bot))  ; break;
            case 6 :tv_gameMode.setText(getContext().getString(R.string.ranked))  ; break;
            case 9 :tv_gameMode.setText(getContext().getString(R.string.ranked)) ; break;
            case 41:tv_gameMode.setText(getContext().getString(R.string.ranked)) ; break;
            case 42:tv_gameMode.setText(getContext().getString(R.string.ranked)) ; break;
            case 410:tv_gameMode.setText(getContext().getString(R.string.ranked)) ; break;
            case 430:tv_gameMode.setText(getContext().getString(R.string.blind_pick)) ; break;
            case 420:tv_gameMode.setText(getContext().getString(R.string.ranked)) ; break;
            case 450:tv_gameMode.setText("ARAM") ; break;
            case 470:tv_gameMode.setText(getContext().getString(R.string.ranked_flex)) ; break;
            case 440:tv_gameMode.setText(getContext().getString(R.string.ranked_flex)) ; break;
            case 400:tv_gameMode.setText(getContext().getString(R.string.normal_draft)) ; break;
            case 65:tv_gameMode.setText(getContext().getString(R.string.aram)) ; break;
            case 70:tv_gameMode.setText(getContext().getString(R.string.all_in_one)) ; break;
            case 76:tv_gameMode.setText("URF") ; break;
            case 300:tv_gameMode.setText(getContext().getString(R.string.poro_king)) ; break;
            case 318:tv_gameMode.setText(getContext().getString(R.string.random_urf)) ; break;
            case 990:tv_gameMode.setText(getContext().getString(R.string.star_normal)) ; break;
            case 980:tv_gameMode.setText(getContext().getString(R.string.star_normal)) ; break;
            default:; break;
        }



            //ADCOLONY
           /* AdColonyAppOptions app_options = new AdColonyAppOptions()
                    .setUserID( "unique_user_id" );
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
                    ad.show();
                    Log.d( TAG, "onRequestFilled" );
                }
                @Override
                public void onRequestNotFilled( AdColonyZone zone )
                {
                    Log.d( TAG, "onRequestNotFilled");
                }
                @Override
                public void onOpened( AdColonyInterstitial ad )
                {
                    Log.d( TAG, "onOpened" );
                }
                @Override
                public void onExpiring( AdColonyInterstitial ad )
                {
                    Log.d( TAG, "onExpiring" );
                }
            };
            AdColony.requestInterstitial( ZONE_ID, listener, ad_options );

*/





        ParticipantsAdapter participantsAdapter = new ParticipantsAdapter(getActivity(), participantsItems,summonerName);
        lv_participants.setAdapter(participantsAdapter);


        return view;
    }

    public void setParameters(CurrentGameObject cgo, String summonerName, List<ParticipantListObject> participantsItems){
        this.cgo               = cgo;
        this.summonerName      = summonerName;
        this.participantsItems = participantsItems;
    }
}
