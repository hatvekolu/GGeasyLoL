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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.adapter.LotteryAdapter;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class LotteriesFragment extends Fragment {

    ImageView profile;
    ListView lv_lotteries;
    DBHelper dbHelper;
    List<LotteryObject> lotteries = new ArrayList<LotteryObject>();
    LotteryAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lotteries, container, false);

        lv_lotteries = (ListView) view.findViewById(R.id.list_view);
        dbHelper = new DBHelper(getContext());
        profile      = (ImageView  ) view.findViewById(R.id.imageView13 );

        lv_lotteries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LotteryObject data= adapter.getItem(position);

                LotteryFragment cmof = new LotteryFragment();
                cmof.setLottery(data);
                LotteriesFragment.this.getFragmentManager().beginTransaction()
                        .replace(R.id.content_main_page, cmof)
                        .addToBackStack(null)
                        .commit();

                View view1 = getActivity().getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);}
            }
        });
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
        new getData().execute();

        return view;
    }

    private class getData extends AsyncTask<String, String, String> {
        BlankFragment progress;
        @Override
        protected void onPreExecute() {
            FragmentManager fm = getFragmentManager();
            progress = new BlankFragment();
            progress.show(fm, "");
            lotteries.clear();
        }
        @Override
        protected String doInBackground(String... values)
        {

            try{
                String data = new RiotApiHelper().readURL("http://ggeasylol.com/json/get_lotteries.php");
               JSONArray array1 = new JSONArray(data);
                JSONObject obje1;
                for (int i = 0; i < array1.length(); i++){
                    obje1 = array1.getJSONObject(i);
                    lotteries.add(new LotteryObject(obje1.getString("ID"), obje1.getString("name"),
                            obje1.getString("odul"), obje1.getString("img"), obje1.getString("end_date"),
                            obje1.getString("winnerID"), obje1.getString("status")));
                }
                return "0";
            }catch (Exception e){
                return "HATA";
            }


        }
        @Override
        protected void onPostExecute(String results)
        {   if (results.equals("0")){
            adapter = new LotteryAdapter(getActivity(), lotteries);
            lv_lotteries.setAdapter(adapter);

        }
        else
            Toast.makeText(getContext(),getContext().getString(R.string.ops_make_mistake), Toast.LENGTH_LONG).show();

            progress.dismiss();
        }
    }

}