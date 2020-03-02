package com.antika.berk.ggeasy.object;

public class ChampionMasterObject {
    private int championPoints=0, championPointsUntilNextLevel=0, championLevel=0, tokensEarned=0,
            championId=0, championPointsSinceLastLevel=0;
    private boolean chestGranted=false;
    String lastPlayTime;
    public ChampionMasterObject(int championPoints, int championPointsUntilNextLevel, int championLevel,
                                int tokensEarned, int championId, int championPointsSinceLastLevel,
                                String lastPlayTime,boolean chestGranted){
        this.championPoints               = championPoints;
        this.championPointsUntilNextLevel = championPointsUntilNextLevel;
        this.championLevel                = championLevel;
        this.tokensEarned                 = tokensEarned;
        this.championId                   = championId;
        this.championPointsSinceLastLevel = championPointsSinceLastLevel;
        this.lastPlayTime                 = lastPlayTime;
        this.chestGranted                 = chestGranted;
    }

    public int      getChampionPoints(){return championPoints;}
    public int      getChampionPointsUntilNextLevel() {return championPointsUntilNextLevel;}
    public int      getChampionLevel()                {return championLevel;}
    public int       getTokensEarned()                {return tokensEarned;}
    public int      getChampionId()                   {return championId;}
    public int      getChampionPointsSinceLastLevel() {return championPointsSinceLastLevel;}
    public String      getLastPlayTime()                 {return lastPlayTime;}
    public boolean  isChestGranted()                  {return chestGranted;}

}
