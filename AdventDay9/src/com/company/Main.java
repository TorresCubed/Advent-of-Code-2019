package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Long> code = new ArrayList<Long>();
        try{
            File day9 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay9.txt");
            Scanner scanner = new Scanner(day9);
            String[] temp = scanner.nextLine( ).split(",");

            for (String s : temp) {
                code.add(Long.parseLong(s));
            }
            scanner.close();
        }catch (FileNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        for (long i = 0; i <10000; i++) {
            code.add((long)0);
        }







        IntCode intCode = new IntCode(code);

        intCode.intCode();




    }
}
