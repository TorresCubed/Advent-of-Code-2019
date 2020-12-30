package com.company;

import java.io.File;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        File todaysInput = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay18.txt");

        CaveSystems myCave = new CaveSystems(todaysInput);
        myCave.drawCave();
//        Path shortest = myCave.findShortestPath();
//        System.out.println("The shortest number of steps to collect all keys: " + (shortest.getPathFromStart().size()-1));
//        System.out.println();
//        for(Coordinate path: shortest.getOwnedKeys()){
//            System.out.print(path.getIdentifier() + ", ");
//        }

    }








}
