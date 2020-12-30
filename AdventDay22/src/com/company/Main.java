package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        long deckSize = 119315717514047L;
//        long position = 2020;
//        long shuffles = 101741582076661L;
//
//        long a = 1;
//        long b = 0;
//        long lineModA =0;
//        long lineModB = 0;
//
//        for (String line: getInput()){
//            if (line.substring(0,19).equals("deal with increment ")){
//                lineModA = Integer.parseInt(line.substring(19));
//                lineModB = 0;
//            } else if (line.substring(0,4).equals("cut ")){
//                lineModA = 1;
//                lineModB = -Integer.parseInt(line.substring(4));
//            } else {
//                lineModA = -1;
//                lineModB = -1;
//            }
//            a = (lineModA*a)%deckSize;
//            b = (lineModA*b+lineModB)%deckSize;
//        }
        long a = 1;
        long b = 2;
        for (long i = 101741582076661L; i>0; i++){
            a = b+1;
            b = a*2;
        }
    }

    public static ArrayList<String> getInput(){
        ArrayList<String> instructions = new ArrayList<>();
        try {
            File day19 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay19.txt");
            Scanner scanner = new Scanner(day19);
            while (scanner.hasNextLine()){
                instructions.add(scanner.nextLine());
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        return instructions;
    }

}
