package com.antika.berk.ggeasy.adapter;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.ChampionObject;
import com.antika.berk.ggeasy.object.ParticipantListObject;
import com.antika.berk.ggeasy.object.SpellObject;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

public class ParticipantsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<ParticipantListObject> mKisiListesi;
    private Context context;
    String sihirdarAdi;
    DBHelper dbHelper;
    String version;
    public ParticipantsAdapter(Activity activity, List<ParticipantListObject> kisiler,String summonerName) {
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mKisiListesi = kisiler;
        sihirdarAdi=summonerName;
        dbHelper =new DBHelper(context);
        if (dbHelper.getMatch("Gorev32").length()>0)
            version = dbHelper.getMatch("Gorev32");
        else
            version = new RiotApiHelper().version;
    }

    @Override
    public int getCount() {
        return mKisiListesi.size();
    }

    @Override
    public ParticipantListObject getItem(int position) {
        return mKisiListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        LinearLayout back;
        RelativeLayout rl_teamcolor1;
        ImageView imageView;
        ImageView spell1;
        ImageView spell2;
        ImageView league;
        ImageView image_mastery;
        TextView tv_champion_point;
        TextView textView;
        TextView tv_k;
        TextView tv_league;
        TextView tv_divison;
        TextView tv_point;
        TextView tv_progress;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView==null)
        {
            rowView = mInflater.inflate(R.layout.match_data_list_item,null);
            viewHolder = new ViewHolder();

            viewHolder.imageView = (ImageView) rowView.findViewById(R.id.simge);
            viewHolder.spell1 = (ImageView) rowView.findViewById(R.id.imageView2);
            viewHolder.spell2 = (ImageView) rowView.findViewById(R.id.imageView);
            viewHolder.league = (ImageView) rowView.findViewById(R.id.imageView14);
            viewHolder.image_mastery = (ImageView) rowView.findViewById(R.id.imageView18);
            viewHolder.tv_champion_point = (TextView) rowView.findViewById(R.id.textView17);
            viewHolder.textView = (TextView) rowView.findViewById(R.id.isimsoyisim);
            viewHolder.tv_k = (TextView) rowView.findViewById(R.id.textView19);
            viewHolder.tv_league = (TextView) rowView.findViewById(R.id.textView26);
            viewHolder.tv_divison = (TextView) rowView.findViewById(R.id.textView27);
            viewHolder.tv_point = (TextView) rowView.findViewById(R.id.textView30);
            viewHolder.tv_progress = (TextView) rowView.findViewById(R.id.textView29);

            viewHolder.back = (LinearLayout) rowView.findViewById(R.id.back);
            viewHolder.rl_teamcolor1 = (RelativeLayout) rowView.findViewById(R.id.colorlayout1);
            rowView.setTag(viewHolder);
            final ParticipantListObject kisi = mKisiListesi.get(position);
            String new_progress = "";
            viewHolder.tv_k.setText(""+kisi.getMaster());


            if(kisi.getMaster_icon()==0)
                viewHolder.image_mastery.setImageResource(R.drawable.seviye0);
            else if(kisi.getMaster_icon()==1)
                viewHolder.image_mastery.setImageResource(R.drawable.seviye1);
            else if(kisi.getMaster_icon()==2)
                viewHolder.image_mastery.setImageResource(R.drawable.seviye2);
            else if(kisi.getMaster_icon()==3)
                viewHolder.image_mastery.setImageResource(R.drawable.seviye3);
            else if(kisi.getMaster_icon()==4)
                viewHolder.image_mastery.setImageResource(R.drawable.seviye4);
            else if(kisi.getMaster_icon()==5)
                viewHolder.image_mastery.setImageResource(R.drawable.seviye5);
            else if(kisi.getMaster_icon()==6)
                viewHolder.image_mastery.setImageResource(R.drawable.seviye6);
            else if(kisi.getMaster_icon()==7)
                viewHolder.image_mastery.setImageResource(R.drawable.seviye7);
            if ((kisi.getMaster())>0) {
                if (kisi.getMaster() > 999999) {
                    viewHolder.tv_k.setText("" + (kisi.getMaster() / 1000000) + " M " + ((kisi.getMaster() - 1000000) / 1000) + " K");
                } else if (kisi.getMaster() > 999) {
                    viewHolder.tv_k.setText("" + (kisi.getMaster() / 1000) + " K");
                } else {
                    viewHolder.tv_k.setText("" + kisi.getMaster());
                }
            }
            if(kisi.getLeague_progress().length()>0)
                viewHolder.tv_progress.setVisibility(View.VISIBLE);
            for (char ch: kisi.getLeague_progress().toCharArray()) {
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

            ChampionObject cmp = dbHelper.getChampion(Integer.toString(kisi.getChampionID()));
            if(dbHelper.getChampion(""+kisi.getChampionID()) == null){

            }
            SpellObject spl1 = dbHelper.getSpell(Integer.toString(kisi.getSpell1()));
            SpellObject spl2 = dbHelper.getSpell(Integer.toString(kisi.getSpell2()));

            Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version + "/img/champion/" + cmp.getChampionKey() + ".png").into(viewHolder.imageView);
            Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version + "/img/spell/" + spl1.getSpellKey() + ".png").into(viewHolder.spell1);
            Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version + "/img/spell/" + spl2.getSpellKey() + ".png").into(viewHolder.spell2);



            switch (kisi.getLeague())
            {
                case ("BRONZE")     : viewHolder.league.setImageResource(R.drawable.bronze); break;
                case ("GRANDMASTER"): viewHolder.league.setImageResource(R.drawable.grandmaster); break;
                case ("CHALLENGER") : viewHolder.league.setImageResource(R.drawable.challenger); break;
                case ("DIAMOND")    : viewHolder.league.setImageResource(R.drawable.diamond); break;
                case ("GOLD")       : viewHolder.league.setImageResource(R.drawable.gold); break;
                case ("MASTER")     : viewHolder.league.setImageResource(R.drawable.master); break;
                case ("PLATINUM")   : viewHolder.league.setImageResource(R.drawable.platinum); break;
                case ("PROVISIONAL"): viewHolder.league.setImageResource(R.drawable.iron); break;
                case ("SILVER")     : viewHolder.league.setImageResource(R.drawable.silver); break;
                default             : viewHolder.league.setImageResource(R.drawable.iron); break;
            }
            if(kisi.getIsim().equals(sihirdarAdi))
                viewHolder.back.setBackgroundColor(0x9177FF07);
            else {
                viewHolder.back.setBackgroundColor(0x00000000);
            }
            viewHolder.textView.setText(kisi.getIsim());
            if (kisi.getLeague().length()>0)
                viewHolder.tv_league.setText(kisi.getLeague());
            else
                viewHolder.tv_league.setText("UNRANKED");
            viewHolder.tv_divison.setText(kisi.getLeague_division());
            viewHolder.tv_point.setText("LP "+kisi.getLeague_point());
            viewHolder.tv_progress.setText(Html.fromHtml(new_progress));


            if(kisi.getTeam() == 100)
                viewHolder.rl_teamcolor1.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            else
                viewHolder.rl_teamcolor1.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        }
        else {
            viewHolder  = (ViewHolder) rowView.getTag();
        }



        return rowView;
    }
}