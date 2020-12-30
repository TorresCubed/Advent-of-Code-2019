package com.company;

import java.util.ArrayList;

public class Coordinate {
    private int x;
    private int y;
    private char identifier;
    private Coordinate northNeighbor = null;
    private Coordinate eastNeighbor = null;
    private Coordinate southNeighbor = null;
    private Coordinate westNeighbor = null;

    public Coordinate(int x, int y, char identifier) {
        this.x = x;
        this.y = y;
        this.identifier = identifier;
    }

    public void setIdentifier(char identifier) {
        this.identifier = identifier;
    }

    public Coordinate getNorthNeighbor() {
        return northNeighbor;
    }

    public Coordinate getEastNeighbor() {
        return eastNeighbor;
    }

    public Coordinate getSouthNeighbor() {
        return southNeighbor;
    }

    public Coordinate getWestNeighbor() {
        return westNeighbor;
    }

    public void setNorthNeighbor(Coordinate northNeighbor) {
        this.northNeighbor = northNeighbor;
    }

    public void setEastNeighbor(Coordinate eastNeighbor) {
        this.eastNeighbor = eastNeighbor;
    }

    public void setSouthNeighbor(Coordinate southNeighbor) {
        this.southNeighbor = southNeighbor;
    }

    public void setWestNeighbor(Coordinate westNeighbor) {
        this.westNeighbor = westNeighbor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getIdentifier() {
        return identifier;
    }

    public ArrayList<Coordinate> shareNeighbors() {
        ArrayList<Coordinate> allNeighbors = new ArrayList<>();
        allNeighbors.add(this.northNeighbor);
        allNeighbors.add(this.eastNeighbor);
        allNeighbors.add(this.southNeighbor);
        allNeighbors.add(this.westNeighbor);
        return allNeighbors;
    }

}
