package com.antika.berk.ggeasy.object;

public class FourSkillObject {
    private String id,skillQ, skillW,skillE,skillR,passive,championName,version;

    public FourSkillObject(String id, String skillQ, String skillW, String skillE, String skillR,String passive, String championName, String version){
        this.id                = id;
        this.skillQ                = skillQ;
        this.skillW                = skillW;
        this.skillE                = skillE;
        this.skillR                = skillR;
        this.passive               = passive;
        this.championName          = championName;
        this.version               = version;
    }

    public String getId()                         {return id;}
    public String getSkillQ()                     {return skillQ;}
    public String getSkillW()                     {return skillW;}
    public String getSkillE()                     {return skillE;}
    public String getSkillR()                     {return skillR;}
    public String getPassive()                    {return passive;}
    public String getChampionName()               {return championName;}
    public String getVersion()                    {return version;}

}
