package com.company;

import java.io.File;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        File todaysInput = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay18.txt");

        CaveSystems myCave = new CaveSystems(todaysInput);
        myCave.drawCave();
        System.out.println();
        System.out.println();
        AutoRobit quickest = myCave.collectAllKeys();
        System.out.println("The shortest number of steps to collect all keys: " + (quickest.getTotalDistance()));
        System.out.println();
        for (Coordinate keys: quickest.getOwnedKeys()) {
            System.out.print(keys.getIdentifier() + ", ");
        }

    }
}
