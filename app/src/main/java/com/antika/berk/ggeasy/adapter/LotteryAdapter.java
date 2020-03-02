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
import com.antika.berk.ggeasy.fragment.LotteryObject;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo- on 19.8.2019.
 */

public class LotteryAdapter extends BaseAdapter  {
    private LayoutInflater mInflater;
    private List<LotteryObject> mKisiListesi;
    private Context context;

    public LotteryAdapter(Activity activity, List<LotteryObject> kisiler) {
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mKisiListesi = kisiler;
    }

    @Override
    public int getCount() {
        return mKisiListesi.size();
    }

    @Override
    public LotteryObject getItem(int position) {
        return mKisiListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.lottery_list_item, null);

        ImageView iv_img  = (ImageView) satirView.findViewById(R.id.simge      );
        TextView  tv_name = (TextView ) satirView.findViewById(R.id.isimsoyisim);
        TextView tv_date = (TextView ) satirView.findViewById(R.id.textView5  );
        TextView  tv_odul = (TextView ) satirView.findViewById(R.id.textView41 );
        TextView  tv_pers = (TextView ) satirView.findViewById(R.id.textView10 );

        LotteryObject lottery = mKisiListesi.get(position);

        Picasso.with(context).load("http://ggeasylol.com/img/" + lottery.getImg()).into(iv_img);

        tv_name.setText(lottery.getName());
        tv_date.setText(lottery.getEnd_date());
        tv_odul.setText(lottery.getOdul());

        if(lottery.getStatus().equals("0"))
            tv_pers.setText(context.getString(R.string.continues));
        else if(lottery.getStatus().equals("1"))
            tv_pers.setText(context.getString(R.string.drawing));
        else
            tv_pers.setText(context.getString(R.string.its_over));

        return satirView;
    }




}