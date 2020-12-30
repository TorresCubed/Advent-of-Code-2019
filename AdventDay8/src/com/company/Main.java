package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try{
            File day1 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay8.txt");
            Scanner scanner = new Scanner(day1);
            String temp = scanner.nextLine();;
            ArrayList<Integer> pixels = new ArrayList<>();
            for (int i = 0; i<temp.length();i++) {
                pixels.add(temp.charAt(i)-'0');
            }

            Image myImage = new Image();
            myImage.makeImage(25,6,pixels);

            ArrayList<Character> message = new ArrayList<>();

            for (int i =0; i < 25*6; i++) {
                message.add(myImage.finalDisplay(i));
            }

            int messageDisplay =0;
            for (int t = 0; t<6;t++)  {
                for (int w =0; w<25; w++) {
                    System.out.print(message.get(messageDisplay));
                    messageDisplay++;
                }
                System.out.println();
            }






//            int layerLeast =0;
//            int zeroCount=Integer.MAX_VALUE;
//            for (int i =0; i <= myImage.getLayer(); i++) {
//                int count = myImage.countType(0, i);
//                if (count < zeroCount) {
//                    zeroCount = count;
//                    layerLeast = i;
//                }
//            }
//            System.out.println(layerLeast);
//
//            int mult = myImage.countType(1,layerLeast)*myImage.countType(2,layerLeast);
//            System.out.println(mult);

            scanner.close();
        }catch (FileNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
