package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            File day6 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay6.txt");
            Scanner scanner = new Scanner(day6);
            ArrayList<String> connections = new ArrayList<String>();
            while (true) {
                if (scanner.hasNext() == true) {
                    connections.add(scanner.nextLine());
                } else {
                    break;
                }
            }
            Map<String, Body> universe = new HashMap<String, Body>();


            for (String conection : connections) {
                String[] parts = conection.split("\\)");
                universe.put(parts[1], new Body(parts[0]));
            }




            int totalOrbits =0;
            for (String name : universe.keySet()) {
                String temp = name;
                while(!temp.equals("COM")) {
                    universe.get(name).incrementOrbits();
                    temp =universe.get(temp).getDirectOrbit();
                }
                totalOrbits+= universe.get(name).getTotalOrbits();
            }

//            System.out.println(totalOrbits);
            int sanCount =0;
            int youCount = 0;
            String youDir = universe.get("YOU").getDirectOrbit();

            for (int u =0; u<universe.get("YOU").getTotalOrbits()-1 ; u++) {
                sanCount =0;
                String sanDir = universe.get("SAN").getDirectOrbit();

                for (int s = 0; s<universe.get("SAN").getTotalOrbits()-1;s++) {

                    if (sanDir.equals(youDir)){
                        System.out.println(youCount+sanCount);
                        break;
                    }

                    sanCount++;
                    sanDir = universe.get(sanDir).getDirectOrbit();
                }

                youDir = universe.get(youDir).getDirectOrbit();
                youCount++;
            }



            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }

    }


}


