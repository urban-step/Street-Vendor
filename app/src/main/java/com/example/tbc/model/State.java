package com.example.tbc.model;

import java.io.Serializable;

public class State implements Serializable {
     public int id;
     public String state_name;

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String country_id;

    @Override
    public String toString() {
        return state_name;
    }
    public State(int id, String state_name) {
        this.id = id;
        this.state_name = state_name;
    }

}
