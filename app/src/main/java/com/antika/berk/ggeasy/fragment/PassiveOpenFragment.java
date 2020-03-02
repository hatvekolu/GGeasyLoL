package com.antika.berk.ggeasy.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.helper.DBHelper;
import com.antika.berk.ggeasy.object.FourSkillObject;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Random;

import it.sephiroth.android.library.picasso.Picasso;


public class PassiveOpenFragment extends DialogFragment {
    PassiveSkillFragment psf;
    public void setFragment(PassiveSkillFragment psf) {
        this.psf = psf;
    }
    ImageView passive;
    String gelen,sonuc="",son;
    TextView ekran;
    String []key ={"a","b","c","d","e","f","g","h","i","ı","j","k","l","m","n","o","p","r","s","q","t","u","v","y","z"," ",".","'"};
    Button key1,key2,key3,key4,key5,key6,key7,key8,key9,key10,key11,key12,key13,key14,sifirla;
    String []x;
    String p="";
    DBHelper dbHelper;
    int  veri;
    private FourSkillObject fo;

    public void setItem(FourSkillObject fo) {
        this.fo = fo;
    }
    public void setVeri(int veri) {
        this.veri = veri;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_passive_open, container, false);


        dbHelper=new DBHelper(getContext());
        ekran=(TextView) view.findViewById(R.id.textView94);
        passive=(ImageView)view.findViewById(R.id.passive);
        key1=(Button)view.findViewById(R.id.button27);
        key2=(Button)view.findViewById(R.id.button28);
        key3=(Button)view.findViewById(R.id.button29);
        key4=(Button)view.findViewById(R.id.button30);
        key5=(Button)view.findViewById(R.id.button31);
        key6=(Button)view.findViewById(R.id.button32);
        key7=(Button)view.findViewById(R.id.button33);
        key8=(Button)view.findViewById(R.id.button34);
        key9=(Button)view.findViewById(R.id.button35);
        key10=(Button)view.findViewById(R.id.button36);
        key11=(Button)view.findViewById(R.id.button37);
        key12=(Button)view.findViewById(R.id.button38);
        key13=(Button)view.findViewById(R.id.button39);
        key14=(Button)view.findViewById(R.id.button40);
        sifirla=(Button)view.findViewById(R.id.sifir);
        sifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p="";
                x=null;
                key1.setVisibility(View.VISIBLE);key6.setVisibility(View.VISIBLE);key11.setVisibility(View.VISIBLE);
                key2.setVisibility(View.VISIBLE);key7.setVisibility(View.VISIBLE);key12.setVisibility(View.VISIBLE);
                key3.setVisibility(View.VISIBLE);key8.setVisibility(View.VISIBLE);key13.setVisibility(View.VISIBLE);
                key4.setVisibility(View.VISIBLE);key9.setVisibility(View.VISIBLE);key14.setVisibility(View.VISIBLE);
                key5.setVisibility(View.VISIBLE);key10.setVisibility(View.VISIBLE);
                yenile();
            }
        });
        key1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ekran.length();i++){
                    if (String.valueOf(ekran.getText().charAt(i)).equals("_")){
                        x[i]=key1.getText().toString().toUpperCase();
                        key1.setVisibility(View.INVISIBLE);
                        break;
                    }

                }
                p="";
                for (int i=0;i<x.length;i++){
                    p=p+x[i];
                }
                ekran.setText(p);
                if (ekran.getText().toString().toLowerCase().equals(son.toString().toLowerCase())){
                    TastyToast.makeText(getContext(), getString(R.string.congratulations), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    if(Integer.parseInt(dbHelper.getMatch("Gorev31"))<=veri)
                        dbHelper.insertMatch(""+(veri+1), "Gorev31");
                    psf.yenile();
                    getDialog().dismiss();
                }


            }

        });
        key2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ekran.length();i++){
                    if (String.valueOf(ekran.getText().charAt(i)).equals("_")){
                        x[i]=key2.getText().toString().toUpperCase();
                        key2.setVisibility(View.INVISIBLE);
                        break;
                    }

                }
                p="";
                for (int i=0;i<x.length;i++){
                    p=p+x[i];
                }
                ekran.setText(p);
                if (ekran.getText().toString().toLowerCase().equals(son.toString().toLowerCase())){
                    TastyToast.makeText(getContext(), getString(R.string.congratulations), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    if(Integer.parseInt(dbHelper.getMatch("Gorev31"))<=veri)
                        dbHelper.insertMatch(""+(veri+1), "Gorev31");
                    psf.yenile();
                    getDialog().dismiss();
                }

            }
        });
        key3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ekran.length();i++){
                    if (String.valueOf(ekran.getText().charAt(i)).equals("_")){
                        x[i]=key3.getText().toString().toUpperCase();
                        key3.setVisibility(View.INVISIBLE);
                        break;
                    }

                }
                p="";
                for (int i=0;i<x.length;i++){
                    p=p+x[i];
                }
                ekran.setText(p);
                if (ekran.getText().toString().toLowerCase().equals(son.toString().toLowerCase())){
                    TastyToast.makeText(getContext(), getString(R.string.congratulations), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    if(Integer.parseInt(dbHelper.getMatch("Gorev31"))<=veri)
                        dbHelper.insertMatch(""+(veri+1), "Gorev31");
                    psf.yenile();
                    getDialog().dismiss();
                }

            }
        });
        key4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ekran.length();i++){
                    if (String.valueOf(ekran.getText().charAt(i)).equals("_")){
                        x[i]=key4.getText().toString().toUpperCase();
                        key4.setVisibility(View.INVISIBLE);
                        break;
                    }

                }
                p="";
                for (int i=0;i<x.length;i++){
                    p=p+x[i];
                }
                ekran.setText(p);
                if (ekran.getText().toString().toLowerCase().equals(son.toString().toLowerCase())){
                    TastyToast.makeText(getContext(), getString(R.string.congratulations), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    if(Integer.parseInt(dbHelper.getMatch("Gorev31"))<=veri)
                        dbHelper.insertMatch(""+(veri+1), "Gorev31");
                    psf.yenile();
                    getDialog().dismiss();
                }

            }
        });
        key5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ekran.length();i++){
                    if (String.valueOf(ekran.getText().charAt(i)).equals("_")){
                        x[i]=key5.getText().toString().toUpperCase();
                        key5.setVisibility(View.INVISIBLE);
                        break;
                    }

                }
                p="";
                for (int i=0;i<x.length;i++){
                    p=p+x[i];
                }
                ekran.setText(p);
                if (ekran.getText().toString().toLowerCase().equals(son.toString().toLowerCase())){
                    TastyToast.makeText(getContext(), getString(R.string.congratulations), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    if(Integer.parseInt(dbHelper.getMatch("Gorev31"))<=veri)
                        dbHelper.insertMatch(""+(veri+1), "Gorev31");
                    psf.yenile();
                    getDialog().dismiss();
                }

            }
        });
        key6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ekran.length();i++){
                    if (String.valueOf(ekran.getText().charAt(i)).equals("_")){
                        x[i]=key6.getText().toString().toUpperCase();
                        key6.setVisibility(View.INVISIBLE);
                        break;
                    }

                }
                p="";
                for (int i=0;i<x.length;i++){
                    p=p+x[i];
                }
                ekran.setText(p);
                if (ekran.getText().toString().toLowerCase().equals(son.toString().toLowerCase())){
                    TastyToast.makeText(getContext(), getString(R.string.congratulations), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    if(Integer.parseInt(dbHelper.getMatch("Gorev31"))<=veri)
                        dbHelper.insertMatch(""+(veri+1), "Gorev31");
                    psf.yenile();
                    getDialog().dismiss();
                }

            }
        });
        key7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ekran.length();i++){
                    if (String.valueOf(ekran.getText().charAt(i)).equals("_")){
                        x[i]=key7.getText().toString().toUpperCase();
                        key7.setVisibility(View.INVISIBLE);
                        break;
                    }

                }
                p="";
                for (int i=0;i<x.length;i++){
                    p=p+x[i];
                }
                ekran.setText(p);
                if (ekran.getText().toString().toLowerCase().equals(son.toString().toLowerCase())){
                    TastyToast.makeText(getContext(), getString(R.string.congratulations), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    if(Integer.parseInt(dbHelper.getMatch("Gorev31"))<=veri)
                        dbHelper.insertMatch(""+(veri+1), "Gorev31");
                    psf.yenile();
                    getDialog().dismiss();
                }

            }
        });
        key8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ekran.length();i++){
                    if (String.valueOf(ekran.getText().charAt(i)).equals("_")){
                        x[i]=key8.getText().toString().toUpperCase();
                        key8.setVisibility(View.INVISIBLE);
                        break;
                    }

                }
                p="";
                for (int i=0;i<x.length;i++){
                    p=p+x[i];
                }
                ekran.setText(p);
                if (ekran.getText().toString().toLowerCase().equals(son.toString().toLowerCase())){
                    TastyToast.makeText(getContext(), getString(R.string.congratulations), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    if(Integer.parseInt(dbHelper.getMatch("Gorev31"))<=veri)
                        dbHelper.insertMatch(""+(veri+1), "Gorev31");
                    psf.yenile();
                    getDialog().dismiss();
                }

            }
        });
        key9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ekran.length();i++){
                    if (String.valueOf(ekran.getText().charAt(i)).equals("_")){
                        x[i]=key9.getText().toString().toUpperCase();
                        key9.setVisibility(View.INVISIBLE);
                        break;
                    }

                }
                p="";
                for (int i=0;i<x.length;i++){
                    p=p+x[i];
                }
                ekran.setText(p);
                if (ekran.getText().toString().toLowerCase().equals(son.toString().toLowerCase())){
                    TastyToast.makeText(getContext(), getString(R.string.congratulations), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    if(Integer.parseInt(dbHelper.getMatch("Gorev31"))<=veri)
                        dbHelper.insertMatch(""+(veri+1), "Gorev31");
                    psf.yenile();
                    getDialog().dismiss();
                }

            }
        });
        key10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ekran.length();i++){
                    if (String.valueOf(ekran.getText().charAt(i)).equals("_")){
                        x[i]=key10.getText().toString().toUpperCase();
                        key10.setVisibility(View.INVISIBLE);
                        break;
                    }

                }
                p="";
                for (int i=0;i<x.length;i++){
                    p=p+x[i];
                }
                ekran.setText(p);
                if (ekran.getText().toString().toLowerCase().equals(son.toString().toLowerCase())){
                    TastyToast.makeText(getContext(), getString(R.string.congratulations), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    if(Integer.parseInt(dbHelper.getMatch("Gorev31"))<=veri)
                        dbHelper.insertMatch(""+(veri+1), "Gorev31");
                    psf.yenile();
                    getDialog().dismiss();
                }

            }
        });
        key11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ekran.length();i++){
                    if (String.valueOf(ekran.getText().charAt(i)).equals("_")){
                        x[i]=key11.getText().toString().toUpperCase();
                        key11.setVisibility(View.INVISIBLE);
                        break;
                    }

                }
                p="";
                for (int i=0;i<x.length;i++){
                    p=p+x[i];
                }
                ekran.setText(p);
                if (ekran.getText().toString().toLowerCase().equals(son.toString().toLowerCase())){
                    TastyToast.makeText(getContext(), getString(R.string.congratulations), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    if(Integer.parseInt(dbHelper.getMatch("Gorev31"))<=veri)
                        dbHelper.insertMatch(""+(veri+1), "Gorev31");
                    psf.yenile();
                    getDialog().dismiss();
                }

            }
        });
        key12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ekran.length();i++){
                    if (String.valueOf(ekran.getText().charAt(i)).equals("_")){
                        x[i]=key12.getText().toString().toUpperCase();
                        key12.setVisibility(View.INVISIBLE);
                        break;
                    }

                }
                p="";
                for (int i=0;i<x.length;i++){
                    p=p+x[i];
                }
                ekran.setText(p);
                if (ekran.getText().toString().toLowerCase().equals(son.toString().toLowerCase())){
                    TastyToast.makeText(getContext(), getString(R.string.congratulations), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    if(Integer.parseInt(dbHelper.getMatch("Gorev31"))<=veri)
                        dbHelper.insertMatch(""+(veri+1), "Gorev31");
                    psf.yenile();
                    getDialog().dismiss();
                }

            }
        });
        key13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ekran.length();i++){
                    if (String.valueOf(ekran.getText().charAt(i)).equals("_")){
                        x[i]=key13.getText().toString().toUpperCase();
                        key13.setVisibility(View.INVISIBLE);
                        break;
                    }

                }
                p="";
                for (int i=0;i<x.length;i++){
                    p=p+x[i];
                }
                ekran.setText(p);
                if (ekran.getText().toString().toLowerCase().equals(son.toString().toLowerCase())){
                    TastyToast.makeText(getContext(), getString(R.string.congratulations), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    if(Integer.parseInt(dbHelper.getMatch("Gorev31"))<=veri)
                        dbHelper.insertMatch(""+(veri+1), "Gorev31");
                    psf.yenile();
                    getDialog().dismiss();
                }

            }
        });
        key14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ekran.length();i++){
                    if (String.valueOf(ekran.getText().charAt(i)).equals("_")){
                        x[i]=key14.getText().toString().toUpperCase();
                        key14.setVisibility(View.INVISIBLE);
                        break;
                    }

                }
                p="";
                for (int i=0;i<x.length;i++){
                    p=p+x[i];
                }
                ekran.setText(p);
                if (ekran.getText().toString().toLowerCase().equals(son.toString().toLowerCase())){
                    TastyToast.makeText(getContext(), getString(R.string.congratulations), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    if(Integer.parseInt(dbHelper.getMatch("Gorev31"))<=veri)
                        dbHelper.insertMatch(""+(veri+1), "Gorev31");
                    psf.yenile();
                    getDialog().dismiss();
                }

            }
        });
        yenile();

        return view;
    }

    public static String scramble( Random random, String inputString )
    {
        // Convert your string into a simple char array:
        char a[] = inputString.toCharArray();

        // Scramble the letters using the standard Fisher-Yates shuffle,
        for( int i=0 ; i<a.length ; i++ )
        {
            int j = random.nextInt(a.length);
            // Swap letters
            char temp = a[i]; a[i] = a[j];  a[j] = temp;
        }

        return new String( a );
    }
    public void yenile(){
        gelen=fo.getChampionName();
        son=gelen;
        x=new String[gelen.length()];
        for (int i=0;i<gelen.length();i++){
            x[i]="_";
        }
        String k="";
        Random r = new Random();
        for(int i=0;i<(14-son.length());i++){
            k=k+key[r.nextInt(key.length)];
        }

        gelen=gelen+k;
        sonuc=scramble(r,gelen);
        for (int i=0;i<x.length;i++){
            p=p+x[i];
        }
        ekran.setText(p);
        key1.setText(String.valueOf(sonuc.charAt(0)));
        key2.setText(String.valueOf(sonuc.charAt(1)));
        key3.setText(String.valueOf(sonuc.charAt(2)));
        key4.setText(String.valueOf(sonuc.charAt(3)));
        key5.setText(String.valueOf(sonuc.charAt(4)));
        key6.setText(String.valueOf(sonuc.charAt(5)));
        key7.setText(String.valueOf(sonuc.charAt(6)));
        key8.setText(String.valueOf(sonuc.charAt(7)));
        key9.setText(String.valueOf(sonuc.charAt(8)));
        key10.setText(String.valueOf(sonuc.charAt(9)));
        key11.setText(String.valueOf(sonuc.charAt(10)));
        key12.setText(String.valueOf(sonuc.charAt(11)));
        key13.setText(String.valueOf(sonuc.charAt(12)));
        key14.setText(String.valueOf(sonuc.charAt(13)));

        Picasso.with(getContext()).load("http://ddragon.leagueoflegends.com/cdn/" + fo.getVersion() + "/img/passive/" + fo.getPassive()).into(passive);

    }
}
