package com.antika.berk.ggeasy.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.helper.RiotApiHelper;
import com.antika.berk.ggeasy.object.ChampionObject;
import com.antika.berk.ggeasy.object.MatchHistoryObject;
import com.antika.berk.ggeasy.object.SpellObject;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;
import it.sephiroth.android.library.picasso.Transformation;

/**
 * Created by Lenovo- on 21.9.2017.
 */

public class MatchHistoryAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    Context context;
    DBHelper dbHelper;
    String version;

    private List<MatchHistoryObject> matchHistoryObjects;
    public MatchHistoryAdapter(Activity activity, List<MatchHistoryObject> match) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        matchHistoryObjects = match;
        context=activity.getApplicationContext();
        dbHelper =new DBHelper(context);
        if (dbHelper.getMatch("Gorev32").length()>0)
            version = dbHelper.getMatch("Gorev32");
        else
            version = new RiotApiHelper().version;
    }
    @Override
    public int getCount() {
        return matchHistoryObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return matchHistoryObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        ImageView logo;
        ImageView spell1;
        ImageView spell2;
        ImageView item1;
        ImageView item2;
        ImageView item3;
        ImageView item4;
        ImageView item5;
        ImageView item6;
        ImageView item7;
        TextView win;
        TextView level;
        TextView tv_gameMode;
        TextView kda;
        TextView minion;
        TextView gold;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView==null)
        {
            rowView = mInflater.inflate(R.layout.match_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.logo = (ImageView) rowView.findViewById(R.id.imageView97);
            viewHolder.spell1 = (ImageView) rowView.findViewById(R.id.imageView101);
            viewHolder.spell2 = (ImageView) rowView.findViewById(R.id.imageView102);
            viewHolder.item1 = (ImageView) rowView.findViewById(R.id.imageView103);
            viewHolder.item2 = (ImageView) rowView.findViewById(R.id.imageView104);
            viewHolder.item3 = (ImageView) rowView.findViewById(R.id.imageView105);
            viewHolder.item4 = (ImageView) rowView.findViewById(R.id.imageView106);
            viewHolder.item5 = (ImageView) rowView.findViewById(R.id.imageView107);
            viewHolder.item6 = (ImageView) rowView.findViewById(R.id.imageView108);
            viewHolder.item7 = (ImageView) rowView.findViewById(R.id.imageView111);
            viewHolder.win = (TextView) rowView.findViewById(R.id.textView88);
            viewHolder.level = (TextView) rowView.findViewById(R.id.textView50);
            viewHolder.tv_gameMode = (TextView) rowView.findViewById(R.id.textView102);
            viewHolder.kda = (TextView) rowView.findViewById(R.id.textView103);
            viewHolder.minion = (TextView) rowView.findViewById(R.id.textView104);
            viewHolder.gold = (TextView) rowView.findViewById(R.id.textView105);
            rowView.setTag(viewHolder);
        }
        else {
            viewHolder  = (ViewHolder) rowView.getTag();
        }
        MatchHistoryObject matchHistoryObject = matchHistoryObjects.get(position);
        viewHolder.level.setText(""+matchHistoryObject.getChampLevel());
        SpellObject so1=dbHelper.getSpell(""+matchHistoryObject.getSpell1());
        SpellObject so2=dbHelper.getSpell(""+matchHistoryObject.getSpell2());
        ChampionObject co=dbHelper.getChampion(""+matchHistoryObject.getChampion());
        viewHolder.kda.setText(""+matchHistoryObject.getKills()+"/"+matchHistoryObject.getDeaths()+"/"+matchHistoryObject.getAssists());
        viewHolder.minion.setText(""+matchHistoryObject.getMinionsKilled());
        viewHolder.gold.setText(""+matchHistoryObject.getGoldEarned());

        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version + "/img/champion/" + co.getChampionKey() + ".png").transform(new CircleTransform()).into(viewHolder.logo);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version + "/img/spell/" + so1.getSpellKey() + ".png").into(viewHolder.spell1);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version + "/img/spell/" + so2.getSpellKey() + ".png").into(viewHolder.spell2);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version +"/img/item/"+matchHistoryObject.getItem1()+".png").into(viewHolder.item1);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version +"/img/item/"+matchHistoryObject.getItem2()+".png").into(viewHolder.item2);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version +"/img/item/"+matchHistoryObject.getItem3()+".png").into(viewHolder.item3);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version +"/img/item/"+matchHistoryObject.getItem4()+".png").into(viewHolder.item4);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version +"/img/item/"+matchHistoryObject.getItem5()+".png").into(viewHolder.item5);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version +"/img/item/"+matchHistoryObject.getItem6()+".png").into(viewHolder.item6);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/" + version +"/img/item/"+matchHistoryObject.getItem7()+".png").into(viewHolder.item7);

       if (matchHistoryObject.isWinner()){
           viewHolder.win.setText(R.string.win);
           viewHolder.win.setTextColor(0xff669900);
       }
       else {
           viewHolder.win.setText(context.getString(R.string.defeat));
           viewHolder.win.setTextColor(0xFFCC0000);
       }

        switch (matchHistoryObject.getGameMode())
        {

            case 830 :viewHolder.tv_gameMode.setText(context.getString(R.string.against_bot))  ; break;
            case 840 :viewHolder.tv_gameMode.setText(context.getString(R.string.against_bot))  ; break;
            case 850 :viewHolder.tv_gameMode.setText(context.getString(R.string.against_bot))  ; break;
            case 6 :viewHolder.tv_gameMode.setText(context.getString(R.string.ranked))  ; break;
            case 9 :viewHolder.tv_gameMode.setText(context.getString(R.string.ranked)) ; break;
            case 41:viewHolder.tv_gameMode.setText(context.getString(R.string.ranked)) ; break;
            case 42:viewHolder.tv_gameMode.setText(context.getString(R.string.ranked)) ; break;
            case 410:viewHolder.tv_gameMode.setText(context.getString(R.string.ranked)) ; break;
            case 430:viewHolder.tv_gameMode.setText(context.getString(R.string.blind_pick)) ; break;
            case 420:viewHolder.tv_gameMode.setText(context.getString(R.string.ranked)) ; break;
            case 450:viewHolder.tv_gameMode.setText("ARAM") ; break;
            case 470:viewHolder.tv_gameMode.setText(context.getString(R.string.ranked_flex)) ; break;
            case 440:viewHolder.tv_gameMode.setText(context.getString(R.string.ranked_flex)) ; break;
            case 400:viewHolder.tv_gameMode.setText(context.getString(R.string.normal_draft)) ; break;
            case 65:viewHolder.tv_gameMode.setText(context.getString(R.string.aram)) ; break;
            case 70:viewHolder.tv_gameMode.setText(context.getString(R.string.all_in_one)) ; break;
            case 76:viewHolder.tv_gameMode.setText("URF") ; break;
            case 300:viewHolder.tv_gameMode.setText(context.getString(R.string.poro_king)) ; break;
            case 318:viewHolder.tv_gameMode.setText(context.getString(R.string.random_urf)) ; break;
            case 990:viewHolder.tv_gameMode.setText(context.getString(R.string.star_normal)) ; break;
            case 980:viewHolder.tv_gameMode.setText(context.getString(R.string.star_normal)) ; break;
            default:; break;
        }
        return rowView;
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
