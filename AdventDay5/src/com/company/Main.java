package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<Integer> code = new ArrayList<Integer>();
        try{
            File day1 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay7.txt");
            Scanner scanner = new Scanner(day1);
            String[] temp = scanner.nextLine( ).split(",");

            for (String s : temp) {
                code.add(Integer.parseInt(s));
            }
            scanner.close();
        }catch (FileNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        int[] a = {5,6,7,8,9};
        Permutation permutation = new Permutation();
        permutation.heapPermutation(a,a.length);


        int maxOutput = 0;
        int input=0;

        for (int[] set: permutation.getAmplifiers()) {

            int output =0;

            Amplifier ampA = new Amplifier(set[0],(ArrayList<Integer>) code.clone());
            Amplifier ampB = new Amplifier(set[1],(ArrayList<Integer>) code.clone());
            Amplifier ampC = new Amplifier(set[2],(ArrayList<Integer>) code.clone());
            Amplifier ampD = new Amplifier(set[3],(ArrayList<Integer>) code.clone());
            Amplifier ampE = new Amplifier(set[4],(ArrayList<Integer>) code.clone());

            do {
                input = output;
                output = ampE.intCode(ampD.intCode(ampC.intCode(ampB.intCode(ampA.intCode(input)))));
            } while (output !=99);

            if ( input > maxOutput){
                maxOutput = input;
            }

        }

        System.out.println(maxOutput);











    }







    public static void amplifier(ArrayList<Integer> backup, int[] phaseSetting) {
        for (int j = 0; j < phaseSetting.length; j++) {
            Thread thread = new Thread();

            thread.start();
        }
    }


    }



