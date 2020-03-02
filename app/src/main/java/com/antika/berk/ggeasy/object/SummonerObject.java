package com.antika.berk.ggeasy.object;

public class SummonerObject {
    private String key, name,id,accountId,puuid;
    private int  icon, lvl;

    public SummonerObject(String key, String id, String accountId, String puuid, String name, int icon, int lvl)
    {
        this.key  = key;
        this.id   = id;
        this.accountId   = accountId;
        this.puuid   = puuid;
        this.name = name;
        this.icon = icon;
        this.lvl  = lvl;
    }

    public String getKey() {return key;}
    public String getId()     {return id;}
    public String getAccountId(){return accountId;}
    public String getPuuid(){return puuid;}
    public String getName(){return name;}
    public int getIcon()   {return icon;}
    public int getLvl()    {return lvl;}
}
