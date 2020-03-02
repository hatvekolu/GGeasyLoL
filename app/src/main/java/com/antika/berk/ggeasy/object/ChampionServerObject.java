package com.antika.berk.ggeasy.object;


import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ChampionServerObject extends ArrayList<Parcelable> {
    private String championID;
    private String championKey;
    private String championName;
    private String version;
    private String ip;
    private String rp;
    private List<TavsiyeObject> to;
    private List<ChampionSkillObject> championSkillObjects;
    private List<ChampionSkinObject> championSkinObjects;
    private ChampionStatObject cso;
    private CounterObject co;
    private CounterObject ao;
    public ChampionServerObject(String championID,String championKey, String championName,String version,String ip,String rp,List<TavsiyeObject> to,List<ChampionSkillObject> championSkillObjects,List<ChampionSkinObject> championSkinObjects,ChampionStatObject cso,
                                CounterObject co,CounterObject ao) {

        this.championID    = championID;
        this.championKey   = championKey;
        this.championName  = championName;
        this.version  = version;
        this.ip  = ip;
        this.rp  = rp;
        this.to = to;
        this.championSkillObjects = championSkillObjects;
        this.championSkinObjects = championSkinObjects;
        this.cso = cso;
        this.co = co;
        this.ao = ao;

    }
    public String getChampionID() {
        return championID;
    }
    public String getChampionKey() {
        return championKey;
    }
    public String getChampionName() {
        return championName;
    }
    public String getVersion() {
        return version;
    }
    public String getIp() {
        return ip;
    }
    public String getRp() {
        return rp;
    }
    public List<TavsiyeObject> getTavsiye() {
        return to;
    }
    public List<ChampionSkillObject> getChampionSkillObjects() {
        return championSkillObjects;
    }
    public List<ChampionSkinObject> getChampionSkinObjects() {
        return championSkinObjects;
    }
    public ChampionStatObject getCso() {
        return cso;
    }
    public CounterObject getCo() {
        return co;
    }
    public CounterObject getAo() {
        return ao;
    }
}
