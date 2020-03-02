package com.antika.berk.ggeasy.object;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ItemObject extends ArrayList<Parcelable> {
    private String  id;
    private String name,version,gold,aciklama;

    private List<Item2Object> from;
    private List<Item2Object> to;

    public ItemObject(String id,String name,String version,String gold,String aciklama,List<Item2Object>from,List<Item2Object>to){
        this.id=id;
        this.name=name;
        this.version=version;
        this.gold=gold;
        this.aciklama=aciklama;
        this.from=from;
        this.to=to;
    }
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getVersion(){
        return version;
    }
    public String getGold(){
        return gold;
    }
    public String getAciklama(){
        return aciklama;
    }
    public List<Item2Object>getFrom(){return from;}
    public List<Item2Object>getTo(){return to;}
}