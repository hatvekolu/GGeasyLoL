package com.antika.berk.ggeasy.object;

/**
 * Created by Lenovo- on 6.5.2017.
 */

public class Item2Object {
    private String fromTo,version;


    public Item2Object(String fromTo,String version){
        this.fromTo=fromTo;this.version=version;
    }

    public String getFromTo(){
        return fromTo;
    }
    public String getVersion(){
        return version;
    }
}
