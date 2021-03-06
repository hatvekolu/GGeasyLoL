package com.antika.berk.ggeasy.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.object.FourSkillObject;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;


public class PassiveAdapter extends BaseAdapter {
     private LayoutInflater mInflater;
    Context context;
    DBHelper dbHelper;
    int durum,durum2;
    private List<FourSkillObject> fourSkillObjects;

    public PassiveAdapter(Activity activity, List<FourSkillObject> fourSkill) {
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
        if (dbHelper.getMatch("Gorev31").length() == 0){
            dbHelper.insertMatch("0","Gorev31");
            durum2 = 0;
        }
        else {
            durum2 = Integer.parseInt(dbHelper.getMatch("Gorev30"));
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
        ImageView passive;
        ImageView lock;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FourSkillObject fourSkillObject = fourSkillObjects.get(position);
        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView==null)
        {
            rowView = mInflater.inflate(R.layout.passive_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.passive = (ImageView) rowView.findViewById(R.id.imageView80);
            viewHolder.lock=(ImageView)rowView.findViewById(R.id.imageView74);
            rowView.setTag(viewHolder);
        }
        else {
            viewHolder  = (ViewHolder) rowView.getTag();
            durum = Integer.parseInt(dbHelper.getMatch("Gorev30"));
            durum2 = Integer.parseInt(dbHelper.getMatch("Gorev31"));
        }
        if(durum>(fourSkillObjects.size()-1)){
            if (durum2>=position) {
                viewHolder.lock.setVisibility(View.GONE);
                viewHolder.passive.setVisibility(View.VISIBLE);
                Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + fourSkillObjects.get(position).getVersion() + "/img/passive/" + fourSkillObject.getPassive()).into(viewHolder.passive);

            }
            else {
                viewHolder.lock.setVisibility(View.VISIBLE);
                viewHolder.passive.setVisibility(View.GONE);

            }
        }
        else {
            viewHolder.lock.setVisibility(View.VISIBLE);
            viewHolder.passive.setVisibility(View.GONE);
        }



        return rowView;
    }
}
