package com.company;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> instructions = new ArrayList<>();
        int startinValue = 0;
        try {
            File day16 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay16.txt");
            Scanner scanner = new Scanner(day16);
            String input = scanner.nextLine();

            int length = input.length();

            for (int x = 0; x < 10000; x++) {
                for (int i = 0; i < length; i++) {
                    instructions.add(Character.getNumericValue(input.charAt(i)));
                }
            }

            for (int z = 0; z < 7; z++) {
                startinValue *= 10;
                startinValue += instructions.get(z);
            }

        } catch (FileNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        ArrayList<Integer> newInput = new ArrayList<>();

        for (int i = startinValue; i < instructions.size(); i++) {
            newInput.add(instructions.get(i));
        }


        int count = 0;
        while (count < 100) {
            newInput = amplifyInput(newInput);
            count++;
        }

        for (int i = 0; i <8; i++){
            System.out.println(newInput.get(i));
        }
    }


        public static ArrayList<Integer> amplifyInput (ArrayList<Integer> input) {
            int newInput = 0;
            for (int i = input.size()-1; i >= 0; i--) {
                newInput +=input.get(i);
                input.set(i,newInput%10);
            }
            return input;
        }
}
