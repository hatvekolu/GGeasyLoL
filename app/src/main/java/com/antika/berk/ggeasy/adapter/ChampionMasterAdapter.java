package com.antika.berk.ggeasy.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.ChampionMasterObject;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by berke on 6.12.2016.
 */

public class ChampionMasterAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ChampionMasterObject> mKisiListesi;
    private Context context;
    DBHelper dbHelper;
    String version;

    public ChampionMasterAdapter(Activity activity, List<ChampionMasterObject> kisiler) {
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mKisiListesi = kisiler;
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
    public ChampionMasterObject getItem(int position) {
        return mKisiListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView iv_champion_image;
        TextView tv_champion_point;
        ImageView chest;
        ImageView back;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView==null)
        {
            rowView = mInflater.inflate(R.layout.champion_mastery_item,null);
            viewHolder = new ViewHolder();

            viewHolder.iv_champion_image = (ImageView) rowView.findViewById(R.id.imageView22);
            viewHolder.back = (ImageView) rowView.findViewById(R.id.imageView24);
            viewHolder.chest = (ImageView) rowView.findViewById(R.id.imageView36);
            viewHolder.tv_champion_point = (TextView) rowView.findViewById(R.id.textView17);
            rowView.setTag(viewHolder);
        }
        else {
            viewHolder  = (ViewHolder) rowView.getTag();
        }
        ChampionMasterObject champion = mKisiListesi.get(position);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version + "/img/champion/" +
                dbHelper.getChampion(Integer.toString(champion.getChampionId())).getChampionKey() +
                ".png").tag(context).into(viewHolder.iv_champion_image);
        if (champion.getChampionPoints() > 999999) {
            viewHolder.tv_champion_point.setText("" + (champion.getChampionPoints() / 1000000) + " M " + ((champion.getChampionPoints() - 1000000) / 1000) + " K");
        } else if (champion.getChampionPoints() > 999) {
            viewHolder.tv_champion_point.setText("" + (champion.getChampionPoints() / 1000) + " K");
        } else {
            viewHolder.tv_champion_point.setText("" + champion.getChampionPoints());
        }

        if (champion.getChampionLevel()==1) {
            viewHolder.back.setImageResource(R.drawable.seviye1);
        }else if (champion.getChampionLevel()==2) {
            viewHolder.back.setImageResource(R.drawable.seviye2);
        }else if (champion.getChampionLevel()==3) {
            viewHolder.back.setImageResource(R.drawable.seviye3);
        }else if (champion.getChampionLevel()==4) {
            viewHolder.back.setImageResource(R.drawable.seviye4);
        }else if (champion.getChampionLevel()==5) {
            viewHolder.back.setImageResource(R.drawable.seviye5);
        } else if (champion.getChampionLevel()==6) {
            viewHolder.back.setImageResource(R.drawable.seviye6);
        } else if (champion.getChampionLevel()==7) {
            viewHolder.back.setImageResource(R.drawable.seviye7);
        }
        if (champion.isChestGranted())
            viewHolder.chest.setImageResource(R.drawable.chest);
        return rowView;








    }
}
