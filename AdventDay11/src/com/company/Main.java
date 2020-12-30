package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<Long> instructions = new ArrayList<>();
        try{
            File day11 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay11.txt");
            Scanner scanner = new Scanner(day11);
            String[] data = scanner.nextLine().split(",");

            for (String num: data) {
                instructions.add(Long.parseLong(num));
            }
            scanner.close();
        }catch (FileNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        for (long i = 0; i <10000; i++) {
            instructions.add((long) 0);
        }

        IntCode hullPainter = new IntCode(instructions);

        hullPainter.intCode();

        hullPainter.sortLocations();

        int[] temp = {0,0,99};

        ArrayList<int[]> tempList = new ArrayList<>();
        for (int i = 0; i < hullPainter.getPanels().size(); i++) {
            if (hullPainter.getPanels().get(i)[0] - temp[0] <1) {
                if (hullPainter.getPanels().get(i)[1] == temp[1]) {
                    for (int j = 1; j < (hullPainter.getPanels().get(i)[0] - temp[0]); j++) {
                        int[] space = {(hullPainter.getPanels().get(i)[0] + j), hullPainter.getPanels().get(i)[1], 0};
                        tempList.add(space);
                        System.out.println(space[0]);
                    }
                } else if (hullPainter.getPanels().get(i)[0] != 0) {
                    int[] space = {(0), hullPainter.getPanels().get(i)[1], 0};
                    tempList.add(space);
                }
            }
            temp = hullPainter.getPanels().get(i);
        }

//        int[] temp3 = {999,999,999};
//        for (int[] panels: hullPainter.getPanels()) {
//            if (panels[1] == temp3[1]) {
//                if (panels[2] == 0) {
//                    System.out.print("   ");
//                } else{
//                    System.out.print("///");
//                }
//            } else {
//                System.out.println();
//            }
//            temp3 = panels;
//        }

        for (int[] space: tempList) {
            hullPainter.addloc(space);
        }

        hullPainter.sortLocations();

        ;

        int[] temp2 = {999,999,999};
        for (int[] panels: hullPainter.getPanels()) {
            if (panels[1] == temp2[1]) {
                if (panels[2] == 0) {
                    System.out.print("   ");
                } else{
                    System.out.print("///");
                }
            } else {
                System.out.println();
            }
            temp2 = panels;
        }



        for (int[] hull: hullPainter.getPanels()) {
            System.out.println(hull[0] + ", " + hull[1]);
        }




    }
}
