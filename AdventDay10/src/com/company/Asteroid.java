package com.company;

import java.util.ArrayList;
import java.util.Comparator;

public class Asteroid {

    private int xLoc;
    private int yLoc;
    private int asteroidView=0;
    private ArrayList<int[]> slopeList;

    public Asteroid(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }


    public int getxLoc() {
        return xLoc;
    }

    public int getyLoc() {
        return yLoc;
    }

    public int getAsteroidView() {
        return asteroidView;
    }

    public ArrayList<int[]> getSlopeList() {
        return slopeList;
    }

    public void setSlopeList(ArrayList<int[]> slopeList) {
        this.slopeList = slopeList;
    }

    public void addAsteroidView() {
        this.asteroidView++;
    }



}
