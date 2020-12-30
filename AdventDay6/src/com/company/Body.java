package com.company;

public class Body {
    private String directOrbit;
    private int totalOrbits=0;

    public Body(String directOrbit) {
        this.directOrbit = directOrbit;
    }

    public String getDirectOrbit() {
        return directOrbit;
    }


    public int getTotalOrbits() {
        return totalOrbits;
    }

    public void incrementOrbits() {
        this.totalOrbits++;
    }
}
