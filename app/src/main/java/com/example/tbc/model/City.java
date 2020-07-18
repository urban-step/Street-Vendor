package com.example.tbc.model;

import java.io.Serializable;

public class City implements Serializable {

     public int id;
     public String city_name;

    public String getState_name() {
        return city_name;
    }

    public void setState_name(String state_name) {
        this.city_name = state_name;
    }

    public String state_id;
    @Override
    public String toString() {
        return city_name;
    }
}
