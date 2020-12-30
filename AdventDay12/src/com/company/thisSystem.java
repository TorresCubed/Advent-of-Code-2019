package com.company;

import java.util.ArrayList;

public class thisSystem {


    private ArrayList<Moon> system = new ArrayList<>();

    public thisSystem() {
    }


    public void addMoon(Moon addition) {
        this.system.add(addition);
    }

    public ArrayList<Moon> getSystem() {
        return system;
    }
}
