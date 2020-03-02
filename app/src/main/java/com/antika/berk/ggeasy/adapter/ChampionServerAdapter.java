package com.antika.berk.ggeasy.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.object.ChampionServerObject;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;


public class ChampionServerAdapter extends BaseAdapter implements Filterable{
    private LayoutInflater mInflater;
    private List<ChampionServerObject> mKisiListesi;
    private List<ChampionServerObject> mYedek;
    private Context context;
    private ItemFilter mFilter = new ItemFilter();

    public ChampionServerAdapter(Activity activity, List<ChampionServerObject> kisiler) {
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mKisiListesi = kisiler;
        mYedek = kisiler;
    }

    @Override
    public int getCount() {
        return mKisiListesi.size();
    }

    @Override
    public ChampionServerObject getItem(int position) {
        return mKisiListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        ImageView iv_champion_image;
        TextView tv_champion_name;
        TextView rp;
        TextView ip;
        LinearLayout layout;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView==null)
        {
            rowView = mInflater.inflate(R.layout.champion_server_item,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_champion_image = (ImageView) rowView.findViewById(R.id.imageView71);
            viewHolder.tv_champion_name = (TextView) rowView.findViewById(R.id.textView92);
            viewHolder.ip = (TextView) rowView.findViewById(R.id.textView7);
            viewHolder.rp = (TextView) rowView.findViewById(R.id.textView6);
            viewHolder.layout = (LinearLayout) rowView.findViewById(R.id.layout1);
            rowView.setTag(viewHolder);
        }
        else {
            viewHolder  = (ViewHolder) rowView.getTag();
        }
        ChampionServerObject champion = mKisiListesi.get(position);
        Picasso.with(context).load("https://ddragon.leagueoflegends.com/cdn/"+champion.getVersion()+"/img/champion/" + champion.getChampionKey() + ".png").into(viewHolder.iv_champion_image);
        if (champion.getRp().length() == 0)
            viewHolder.layout.setVisibility(View.GONE);
        else
            viewHolder.layout.setVisibility(View.VISIBLE);
        viewHolder.ip.setText(champion.getIp());
        viewHolder.rp.setText(champion.getRp());

        viewHolder.tv_champion_name.setText(champion.getChampionName());

        return rowView;
    }


    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<ChampionServerObject> list = mYedek;

            int count = list.size();
            final ArrayList<ChampionServerObject> nlist = new ArrayList<ChampionServerObject>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getChampionName()+list.get(i).getRp()+list.get(i).getIp();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mKisiListesi = (ArrayList<ChampionServerObject>) results.values;
            notifyDataSetChanged();
        }

    }
}

