package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<Long> input = getInput();
        for (int i = 0; i < 10000; i++) {
            input.add((long)0);
        }

        IntCode intcode = new IntCode(input);

        int[] mainCode = {65,44,66,44,65,44,66,44,67,44,65,44,66,44,67,44,65,44,67,10};
        System.out.println("Main Code: " + mainCode.length);
        intcode.addAscii(mainCode);

        int[] aCode = {82,44,54,44,76,44,53,44,53,44,82,44,56,10};
        System.out.println("A Code: " + aCode.length);
        intcode.addAscii(aCode);

        int[] bCode = {82,44,56,44,82,44,54,44,54,44,76,44,56,44,76,44,56,10};
        System.out.println("B Code: " +bCode.length);
        intcode.addAscii(bCode);

        int[] cCode = {76,44,53,44,53,44,82,44,54,44,82,44,54,44,76,44,56,10,121,10};
        System.out.println("C Code: " + cCode.length);
        intcode.addAscii(cCode);






        input.set(0,(long)2);
        try {
            intcode.showScaffolding();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }


    }


    public static ArrayList<Long> getInput(){
        ArrayList<Long> instructions = new ArrayList<>();
        try {
            File day17 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay17.txt");
            Scanner scanner = new Scanner(day17);
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
}
