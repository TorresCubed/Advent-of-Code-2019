package com.company;


import java.util.ArrayList;

public class solar {

    private ArrayList<Moon> system = new ArrayList<>();

    public solar() {
    }


    public void addMoon(Moon addition) {
        this.system.add(addition);
    }

    public ArrayList<Moon> getSystem() {
        return system;
    }


}
