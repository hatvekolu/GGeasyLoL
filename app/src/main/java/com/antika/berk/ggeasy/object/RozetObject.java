package com.antika.berk.ggeasy.object;

/**
 * Created by Lenovo- on 5.9.2019.
 */

public class RozetObject {
    private String mission;
    private int total;

    public RozetObject(String mission, int total){
        this.mission                = mission;
        this.total                = total;

    }

    public String getMission()                         {return mission;}
    public int getTotal()                               {return total;}
}
