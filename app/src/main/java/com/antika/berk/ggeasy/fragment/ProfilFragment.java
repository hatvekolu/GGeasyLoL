package com.antika.berk.ggeasy.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.adapter.RozetAdapter;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.ChampionMasterObject;
import com.antika.berk.ggeasy.object.LeagueObject;
import com.antika.berk.ggeasy.object.RozetObject;
import com.antika.berk.ggeasy.object.SummonerObject;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;
import it.sephiroth.android.library.picasso.Transformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {
    SummonerObject so;
    List<LeagueObject> leagues;
    List<ChampionMasterObject> masteries;
    List<RozetObject> rozetObjects;
    DBHelper dbHelper;
    ImageView anaLogo,icon;
    TextView name;
    TextView  tv_summonerLvl, tv_leaugeTier, tv_leagueDivision,
            tv_leagueWin, tv_leagueLost, tv_leaguePoint, tv_leagueProgress,slash,meydanOkuma;
    ImageView  iv_leagueTier;
    GridView rozets;
    RozetAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        anaLogo = (ImageView) view.findViewById(R.id.imageView28) ;
        icon = (ImageView) view.findViewById(R.id.imageView29) ;
        name = (TextView)view.findViewById(R.id.textView45) ;
        dbHelper = new DBHelper(getContext());
        rozets = (GridView) view.findViewById(R.id.rozets);
        tv_summonerLvl    = (TextView ) view.findViewById(R.id.textView15 );
        tv_leaugeTier     = (TextView ) view.findViewById(R.id.textView32 );
        tv_leagueDivision = (TextView ) view.findViewById(R.id.textView33 );
        tv_leagueWin      = (TextView ) view.findViewById(R.id.textView38 );
        slash             = (TextView ) view.findViewById(R.id.textView37 );
        meydanOkuma             = (TextView ) view.findViewById(R.id.textView48 );
        tv_leagueLost     = (TextView ) view.findViewById(R.id.textView36 );
        tv_leaguePoint    = (TextView ) view.findViewById(R.id.textView35 );
        tv_leagueProgress = (TextView ) view.findViewById(R.id.textView18 );
        iv_leagueTier     = (ImageView) view.findViewById(R.id.imageView10);
        new getData().execute(dbHelper.getUser().getEmail(),dbHelper.getUser().getRegion());



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
        protected String doInBackground(String... values)
        {

            RiotApiHelper raHelper = new RiotApiHelper();
            so        = raHelper.getSumonner(values[0], values[1]);
            if(so == null){ return getString(R.string.check_summoner_name_or_region);}
            String token = FirebaseInstanceId.getInstance().getToken();
            rozetObjects = raHelper.getRozet(values[0].replaceAll(" ","_"),values[1],getString(R.string.language3),token,""+so.getIcon());
            leagues   = raHelper.getSummonerLeague(so.getId(), values[1]);
            masteries = raHelper.getChampionMasteries(so.getId(), values[1]);

            for (int i = 0; i < masteries.size(); i++) {
                if (dbHelper.getChampion(Integer.toString(masteries.get(i).getChampionId())) == null)
                    dbHelper.insertChampion(raHelper.getStaticChampion(masteries.get(i).getChampionId()));
            }


            return null;
        }

        @Override
        protected void onPostExecute(String results)
        {
            if(results == null)
            {   if (masteries.size() >0)
                    Picasso.with(getContext()).load("https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+dbHelper.getChampion(Integer.toString(masteries.get(0).getChampionId())).getChampionKey()+"_0.jpg").into(anaLogo);
                Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + new RiotApiHelper().version + "/img/profileicon/" + so.getIcon() + ".png").transform(new CircleTransform()).into(icon);
                name.setText(dbHelper.getUser().getEmail().toString());
                LeagueObject lo;
                try{lo = leagues.get(0);}
                catch(Exception e){lo = new LeagueObject("", "", "UNRANKED", "", 0, 0, 0, false, false, false, false, "", 0, 0, 0);}

                String new_progress = "";
                for (char ch: lo.getMiniSeriesprogress().toCharArray()) {
                    if(ch == 'L')
                    {
                        new_progress+="<font color='#e60000'> X </font>";
                    }
                    else if(ch == 'W')
                    {
                        new_progress+="<font color='#33cc33'> O </font>";
                    }
                    else if(ch == 'N')
                    {
                        new_progress+="<font color='#e6b800'> - </font>";
                    }
                }
                slash.setVisibility(View.VISIBLE);
                tv_leaugeTier.setText(lo.getTier());
                tv_leagueDivision.setText(lo.getDivision());
                tv_leagueWin.setVisibility(View.VISIBLE);
                tv_leagueWin.setText(Integer.toString(lo.getWins()));
                tv_leagueLost.setVisibility(View.VISIBLE);
                tv_leagueLost.setText(Integer.toString(lo.getLosses()));
                tv_leaguePoint.setText(Integer.toString(lo.getLeaguePoints())+ "LP");
                tv_leagueProgress.setText(Html.fromHtml(new_progress));
                tv_summonerLvl.setVisibility(View.VISIBLE);
                tv_summonerLvl.setText(""+so.getLvl());
                switch (lo.getTier())
                {
                    case ("BRONZE")     : iv_leagueTier.setImageResource(R.drawable.bronze     ); break;
                    case ("CHALLENGER") : iv_leagueTier.setImageResource(R.drawable.challenger ); break;
                    case ("DIAMOND")    : iv_leagueTier.setImageResource(R.drawable.diamond    ); break;
                    case ("GOLD")       : iv_leagueTier.setImageResource(R.drawable.gold       ); break;
                    case ("MASTER")     : iv_leagueTier.setImageResource(R.drawable.master     ); break;
                    case ("PLATINUM")   : iv_leagueTier.setImageResource(R.drawable.platinum   ); break;
                    case ("GRANDMASTER"): iv_leagueTier.setImageResource(R.drawable.grandmaster); break;
                    case ("PROVISIONAL"): iv_leagueTier.setImageResource(R.drawable.iron       ); break;
                    case ("SILVER")     : iv_leagueTier.setImageResource(R.drawable.silver     ); break;
                    default             : iv_leagueTier.setImageResource(R.drawable.iron       ); break;
                }
                adapter = new RozetAdapter(getActivity(), rozetObjects);
                rozets.setAdapter(adapter);
                meydanOkuma.setVisibility(View.VISIBLE);

            }
            else
                Toast.makeText(getContext(), getContext().getString(R.string.try_again), Toast.LENGTH_LONG).show();
            progress.dismiss();
        }
    }
    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}
