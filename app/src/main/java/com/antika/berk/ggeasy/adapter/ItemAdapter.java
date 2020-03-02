package com.antika.berk.ggeasy.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.object.ItemObject;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;


public class ItemAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    Context context;
    private List<ItemObject> itemObject;
    private List<ItemObject> mYedek;
    private ItemFilter mFilter = new ItemFilter();
    public ItemAdapter(Activity activity, List<ItemObject> item) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        itemObject = item;
        mYedek=item;
        context=activity;
    }
    @Override
    public int getCount() {
        return itemObject.size();
    }

    @Override
    public ItemObject getItem(int position) {
        return itemObject.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        ImageView logo;
        TextView name;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView==null)
        {
            rowView = mInflater.inflate(R.layout.item_list,null);
            viewHolder = new ViewHolder();
            viewHolder.logo = (ImageView) rowView.findViewById(R.id.imageView35);
            viewHolder.name = (TextView) rowView.findViewById(R.id.textView12);
            rowView.setTag(viewHolder);
        }
        else {
            viewHolder  = (ViewHolder) rowView.getTag();
        }
        ItemObject item = itemObject.get(position);
        Picasso.with(context).load("https://ddragon.leagueoflegends.com/cdn/"+item.getVersion()+"/img/item/"+item.getId()).into(viewHolder.logo);
        viewHolder.name.setText(item.getName());

        return rowView;
    }
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<ItemObject> list = mYedek;

            int count = list.size();
            final ArrayList<ItemObject> nlist = new ArrayList<ItemObject>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getName();
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
            itemObject = (ArrayList<ItemObject>) results.values;
            notifyDataSetChanged();
        }

    }
}
