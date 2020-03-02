package com.antika.berk.ggeasy.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.object.FourSkillObject;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

public class FourSkillAdapter  extends BaseAdapter {
    private LayoutInflater mInflater;
    Context context;
    private List<FourSkillObject> fourSkillObjects;
    DBHelper dbHelper ;
    int durum;
    public FourSkillAdapter(Activity activity, List<FourSkillObject> fourSkill) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        fourSkillObjects = fourSkill;
        context = activity.getApplicationContext();
        dbHelper = new DBHelper(context);
        if (dbHelper.getMatch("Gorev30").length() == 0){
            dbHelper.insertMatch("0","Gorev30");
            durum = 0;
        }
        else {
            durum = Integer.parseInt(dbHelper.getMatch("Gorev30"));
        }
    }

    @Override
    public int getCount() {
        return fourSkillObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return fourSkillObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        ImageView skill1;
        ImageView skill2;
        ImageView skill3;
        ImageView lock;
        ImageView skill4;
        LinearLayout layout;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FourSkillObject fourSkillObject = fourSkillObjects.get(position);
        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView==null)
        {
            rowView = mInflater.inflate(R.layout.four_skill_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.skill1 = (ImageView) rowView.findViewById(R.id.imageView75);
            viewHolder.skill2 = (ImageView) rowView.findViewById(R.id.imageView76);
            viewHolder.skill3 = (ImageView) rowView.findViewById(R.id.imageView77);
            viewHolder.skill4 = (ImageView) rowView.findViewById(R.id.imageView78);
            viewHolder.layout = (LinearLayout) rowView.findViewById(R.id.lay);
            viewHolder.lock=(ImageView)rowView.findViewById(R.id.imageView74);
            rowView.setTag(viewHolder);
        }
        else {
            viewHolder  = (ViewHolder) rowView.getTag();
            durum = Integer.parseInt(dbHelper.getMatch("Gorev30"));

        }


        if (durum>=position) {
            viewHolder.lock.setVisibility(View.GONE);
            viewHolder.layout.setVisibility(View.VISIBLE);
           Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + fourSkillObjects.get(position).getVersion() + "/img/spell/" + fourSkillObject.getSkillQ()).into(viewHolder.skill1);
           Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + fourSkillObjects.get(position).getVersion() + "/img/spell/" + fourSkillObject.getSkillW()).into(viewHolder.skill2);
           Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + fourSkillObjects.get(position).getVersion() + "/img/spell/" + fourSkillObject.getSkillE()).into(viewHolder.skill3);
           Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + fourSkillObjects.get(position).getVersion() + "/img/spell/" + fourSkillObject.getSkillR()).into(viewHolder.skill4);

        }
        else {
            viewHolder.lock.setVisibility(View.VISIBLE);
            viewHolder.layout.setVisibility(View.GONE);

        }


        return rowView;
    }
}
