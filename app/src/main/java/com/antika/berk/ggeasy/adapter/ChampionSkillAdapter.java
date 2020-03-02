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
import com.antika.berk.ggeasy.object.ChampionSkillObject;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;


public class ChampionSkillAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    Context context;
    private List<ChampionSkillObject> skillList;
    DBHelper dbHelper;
    String version;
    public ChampionSkillAdapter(Activity activity, List<ChampionSkillObject> skills) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        skillList = skills;
        context=activity.getApplicationContext();
        dbHelper = new DBHelper(context);
        if (dbHelper.getMatch("Gorev32").length()>0)
            version = dbHelper.getMatch("Gorev32");
        else
            version = new RiotApiHelper().version;
    }
    @Override
    public int getCount() {
        return skillList.size();
    }

    @Override
    public Object getItem(int position) {
        return skillList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        ImageView skillLogo;
        TextView skillDescription;
        TextView skillName;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView==null)
        {
            rowView = mInflater.inflate(R.layout.skill_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.skillLogo = (ImageView) rowView.findViewById(R.id.skill_logo);
            viewHolder.skillDescription = (TextView) rowView.findViewById(R.id.skill_description);
            viewHolder.skillName = (TextView) rowView.findViewById(R.id.skillName);
            rowView.setTag(viewHolder);
        }
        else {
            viewHolder  = (ViewHolder) rowView.getTag();
        }

        final ChampionSkillObject skill = skillList.get(position);
        viewHolder.skillDescription.setText("\t"+skill.getDescription());

        if(position==0)
            viewHolder.skillName.setText(skill.getSkillName()+" [Q]");
        else if(position==1)
            viewHolder.skillName.setText(skill.getSkillName()+" [W]");
        else if(position==2)
            viewHolder.skillName.setText(skill.getSkillName()+" [E]");
        else if(position==3)
            viewHolder.skillName.setText(skill.getSkillName()+" [R]");
        else
            viewHolder.skillName.setText(skill.getSkillName()+" [P]");
        if(position==4)
            Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version + "/img/passive/"+skill.getImage()).into(viewHolder.skillLogo);
        else
            Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version + "/img/spell/"+skill.getImage()).into(viewHolder.skillLogo);


        return rowView;
    }
}
