package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<Long> input = getInput();
        for (int i = 0; i < 300000; i++) {
            input.add((long)0);
        }
        IntCode intcode = new IntCode(input);

        for(int y = 0; y<687; y++){
            System.out.println();
            int count = 0;
            for (int x = 0; x <300; x++){
                try {
                    long result = intcode.intCode(input, x, y);
                    if (result == 1)
                        count++;
                    System.out.print(result);
                } catch (IndexOutOfBoundsException e) {
                    input = MORECODE(input);
                    x--;
                }
            }
            System.out.print(count);
        }


//        int x = 807;
//        int y = 586;
//        int checkX;
//        int checkY;
//        int tempx = x;
//        boolean pulled = false;
//        boolean end = false;
//        do {
//            if (pulled) {
//                y++;
//                x++;
//                tempx = x;
//                pulled = false;
//            }
//            tempx++;
//            checkX = tempx+100;
//            checkY = y-100;
//            try {
//                if (intcode.intCode(input, tempx, y) == 1) {
//                    pulled = true;
//                    end = intcode.intCode(input, checkX, checkY) == 1;
//                }
//            } catch (IndexOutOfBoundsException e){
//                System.out.println("Space Added");
//                input = MORECODE(input);
//                pulled = false;
//                tempx-=2;
//            }
//            System.out.println(tempx +", " + y);
//        } while(!end);
//
//
//        System.out.println(x + "," + (y-100));
//
    }


    public static ArrayList<Long> getInput(){
        ArrayList<Long> instructions = new ArrayList<>();
        try {
            File day19 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay19.txt");
            Scanner scanner = new Scanner(day19);
            String[] data = scanner.nextLine().split(",");
            for (String point:data) {
                instructions.add(Long.parseLong(point));
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        return instructions;
    }


    public static ArrayList<Long> MORECODE(ArrayList<Long> input){
        for (int i = 0; i < 1000; i++) {
            input.add((long)0);
        }
        return input;
    }
}
