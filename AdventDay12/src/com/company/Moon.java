package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Moon {

    private ArrayList<Integer> position;
    private ArrayList<Integer> velocity = new ArrayList<>();

    public Moon(ArrayList<Integer> position) {
        this.position = position;
        Integer[] otherList = new Integer[] {0,0,0};
        velocity.addAll(Arrays.asList(otherList));
    }

    public ArrayList<Integer> getPosition() {
        return position;
    }

    public void setPosition(ArrayList<Integer> position) {
        this.position = position;
    }

    public ArrayList<Integer> getVelocity() {
        return velocity;
    }

    public void setVelocity(ArrayList<Integer> velocity) {
        this.velocity = velocity;
    }



    public void applyGravity(solar mySystem) {
        int[] gravity;
        for (Moon object: mySystem.getSystem()) {
            if (object != this) {
                for(int i = 0; i<3;i++) {
                    this.velocity.set(i, this.velocity.get(i)+Integer.signum((object.getPosition().get(i) - this.position.get(i))));
                }
            }
        }
    }


    public void applyVelocity() {
        for (int i = 0; i<3; i++) {
            this.position.set(i,this.position.get(i) + this.velocity.get(i));
        }
    }


    public void display() {
        solar.out.println("pos=<x= " + this.position.get(0) + ", y= " + this.position.get(1) + ", z = " + this.position.get(2)
        + ">, vel=<x= " + this.velocity.get(0) + ", y= " + this.velocity.get(1) + ", z = " + this.velocity.get(2) + ">");
    }







}
