package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int y = 0;
        int xlength = 0;
        ArrayList<Asteroid> map = new ArrayList<>();
        try{
            File day10 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay10.txt");
            Scanner scanner = new Scanner(day10);

            do {
                char[] temp = scanner.nextLine().toCharArray();
                for (int x = 0; x < temp.length; x++) {
                    if (temp[x] == '#') {

                        map.add(new Asteroid(x,-y));
                    }
                }
                xlength = temp.length;
                y++;
            } while (scanner.hasNext());

            scanner.close();
        }catch (FileNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        xlength -=1;
        y -=1;


        Asteroid most = new Asteroid(0,0);
        for (Asteroid object: map) {
            asteriodVisualization(map,object);
            if (object.getAsteroidView() > most.getAsteroidView()) {
                most = object;
            }
        }



        ArrayList<int[]> posX = new ArrayList<>();
        ArrayList<int[]> negX = new ArrayList<>();

        for (int[] rsq:most.getSlopeList()) {
            if (rsq[1] >=0 ) {
                posX.add(rsq);
            } else {
                negX.add(rsq);
            }
        }

        sortSlopes(posX);
        sortSlopes(negX);

        ArrayList<int[]> finalList = new ArrayList<>();

        for (int[] clockWise: posX) {
            finalList.add(clockWise);
        }
        for (int[] clockWise: negX) {
            finalList.add(clockWise);
        }


        for (int[] yup: finalList) {
            System.out.println(yup[1] + ", " + yup[0]);
        }

        System.out.println(most.getxLoc() + ", " + most.getyLoc());
        System.out.println(finalList.get(199)[1] + ", " + finalList.get(199)[0]);
        System.out.println(((most.getxLoc()+finalList.get(199)[1])*100)-(most.getyLoc()+finalList.get(199)[0]));






//        for (Asteroid object: map) {
//            System.out.println("[" + object.getxLoc() + ", " + object.getyLoc() + "]");
////        }
    }


    public static void asteriodVisualization(ArrayList<Asteroid> map, Asteroid question) {
        int[] tempSlope;
        ArrayList<int[]> haveSeen= new ArrayList< >();

        for (Asteroid object: map) {
            if (object != question) {
                boolean blocked = false;
                tempSlope = getSlope(question, object);
                double slopeNew = (double)tempSlope[0]/tempSlope[1];
                int newYDir=0;
                int newXDir = 0;
                if (tempSlope[0] != 0){
                    newYDir = tempSlope[0] / Math.abs(tempSlope[0]);
                }
                if (tempSlope[1] != 0){
                    newXDir = tempSlope[1] / Math.abs(tempSlope[1]);
                }

                for (int i = 0; i < haveSeen.size(); i++) {
                    int yDir=0;
                    int xDir = 0;
                    double slopeOld = (double)haveSeen.get(i)[0]/haveSeen.get(i)[1];
                    if (haveSeen.get(i)[0] != 0){
                        yDir = haveSeen.get(i)[0] / Math.abs(haveSeen.get(i)[0]);
                    }
                    if (haveSeen.get(i)[1] != 0){
                        xDir = haveSeen.get(i)[1] / Math.abs(haveSeen.get(i)[1]);
                    }
                    if ((slopeNew == slopeOld) && (newYDir == yDir) && (newXDir == xDir)) {
                        haveSeen.set(i,tempSlope);
                        blocked = true;
                        break;
                    }
                }

                if (!blocked) {
                    question.addAsteroidView();
                    haveSeen.add(tempSlope);
                }
            }
        }
        question.setSlopeList(haveSeen);
    }


    public static int[] getSlope(Asteroid start, Asteroid end) {
        int rise = end.getyLoc()- start.getyLoc();
        int run = end.getxLoc() - start.getxLoc();
        int[] slope = {rise,run};
        return slope;
    }
//
//    public static int[] laser(Asteroid yaBoy, int x, int y, int[] currentLast) {
//        int rise = yaBoy.getyLoc() - y;
//        int run = yaBoy.getxLoc() - x;
//        int[] slope = {rise,run};
//
//        int yDir=0;
//        int xDir = 0;
//        double laserSlope = (double)slope[0]/slope[1];
//        if (slope[0] != 0){
//            yDir = slope[0] / Math.abs(slope[0]);
//        }
//        if (slope[1] != 0){
//            xDir = slope[1] / Math.abs(slope[1]);
//        }
//
//        for (int[] targetSlope: yaBoy.getSlopeList()) {
//            int newYDir = 0;
//            double slopeNew = (double)targetSlope[0]/targetSlope[1];
//            int newXDir = 0;
//            if (targetSlope[0] != 0) {
//                newYDir = targetSlope[0] / Math.abs(targetSlope[0]);
//            }
//            if (targetSlope[1] != 0) {
//                newXDir = targetSlope[1] / Math.abs(targetSlope[1]);
//            }
//            if ((slopeNew == laserSlope) && (newYDir == yDir) && (newXDir == xDir)) {
//                int[] temp = targetSlope;
//                yaBoy.getSlopeList().remove(targetSlope);
//                return temp;
//            }
//        }
//
//    return currentLast;
//    }

    public static int compareSlope(int[] existing, int[]adding) {

        double slopeFirst = (double)existing[0]/existing[1];
        double slopeSecond = (double)adding[0]/adding[1];
        if (existing[1] == 0){
            slopeFirst=-9999;
        }
        if (adding[1] == 0){
            slopeSecond=-9999;
        }

        int temp = (int)((slopeFirst-slopeSecond)/Math.abs(slopeFirst-slopeSecond));
        return temp;
    }

    public static void sortSlopes(ArrayList<int[]> list){
        int count;
        do {
            count=0;
            int[] temp;
            for (int i = 0; i <list.size()-1;i++) {
                temp = list.get(i);
                if (compareSlope(temp,list.get(i+1)) <0) {
                    list.set(i,list.get(i+1));
                    list.set(i+1,temp);
                    count++;
                }
            }
        }while(count !=0);
    }



}
