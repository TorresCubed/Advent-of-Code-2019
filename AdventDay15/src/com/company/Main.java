package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ArrayList<Long> instructions = new ArrayList<>();
        try {
            File day15 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay15.txt");
            Scanner scanner = new Scanner(day15);
            String[] data = scanner.nextLine().split(",");

            Arrays.stream(data).forEach(num -> instructions.add(Long.parseLong(num)));
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }



        IntCode intcode = new IntCode(instructions);
        intcode.findOxygen();


    }
}
