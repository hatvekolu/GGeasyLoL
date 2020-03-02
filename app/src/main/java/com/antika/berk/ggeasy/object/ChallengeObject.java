package com.antika.berk.ggeasy.object;

/**
 * Created by Lenovo- on 5.9.2019.
 */

public class ChallengeObject {

    private String id,tarih,mission,user1Match,user2Match,status,user1Name,user2Name,user1Region,user2Region,user1Icon,user2Icon;
    private String puan;
    private boolean winnerUser;
    public ChallengeObject(String id, String tarih,String mission,String user1Match,String user2Match,String status, String user1Name,String user2Name,String user1Region,String user2Region,String user1Icon,String user2Icon,String puan,boolean winnerUser){
        this.id   = id;
        this.tarih = tarih;
        this.mission = mission;
        this.user1Match = user1Match;
        this.user2Match=user2Match;
        this.status=status;
        this.user1Name = user1Name;
        this.user2Name = user2Name;
        this.user1Region = user1Region;
        this.user2Region=user2Region;
        this.user1Icon = user1Icon;
        this.user2Icon=user2Icon;
        this.puan=puan;
        this.winnerUser   = winnerUser;
    }

    public String getId()  {return id;}
    public String getTarih(){return tarih;}
    public String getMission(){return mission;}
    public String getUser1Match(){return user1Match;}
    public String getUser2Match(){return user2Match;}
    public String getStatus()  {return status;}
    public String getUser1Name(){return user1Name;}
    public String getUser2Name(){return user2Name;}
    public String getUser1Region(){return user1Region;}
    public String getUser2Region(){return user2Region;}
    public String getUser1Icon(){return user1Icon;}
    public String getUser2Icon(){return user2Icon;}
    public String getPuan(){return puan;}
    public boolean getWinnerUser(){return winnerUser;}






}