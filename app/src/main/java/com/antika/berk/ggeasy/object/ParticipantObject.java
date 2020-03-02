package com.antika.berk.ggeasy.object;


import java.util.List;

public class ParticipantObject {
    private int spell1Id, spell2Id, profileIconId, championId, teamId;
    private String summonerName,summonerId;
    private List<MasterObject>masterObjects;
    public ParticipantObject( int spell1Id,
                             int  spell2Id,int  profileIconId,int  championId,int  teamId,
                              String  summonerId, String summonerName,List<MasterObject>masterObjects){
        this.spell1Id = spell1Id;
        this.spell2Id = spell2Id;
        this.profileIconId = profileIconId;
        this.championId = championId;
        this.teamId = teamId;
        this.summonerId = summonerId;
        this.summonerName = summonerName;
        this.masterObjects = masterObjects;
    }

    public int getSpell1Id(){return spell1Id;}
    public int getSpell2Id(){return spell2Id;}
    public int getProfileIconId(){return profileIconId;}
    public int getChampionId(){return championId;}
    public int getTeamId(){return teamId;}
    public String getSummonerId(){return summonerId;}
    public String getSummonerName(){return summonerName;}
    public List<MasterObject> getMasterObjects(){return masterObjects;}


}
