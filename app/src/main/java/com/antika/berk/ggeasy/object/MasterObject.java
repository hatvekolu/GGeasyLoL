package com.antika.berk.ggeasy.object;

public class MasterObject {
    private int id, rank;

    public MasterObject(int id, int rank){
        this.id   = id;
        this.rank = rank;
    }

    public int getId()  {return id;}
    public int getRank(){return rank;}
}
