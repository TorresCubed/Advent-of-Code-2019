package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int count = 0;
        for (int i =387638; i <919123; i++) {
            boolean asc = true;
            boolean twice = false;
            ArrayList<Integer> parsed = new ArrayList<Integer>();
            int temp = i;
            for (int j =0; j < 6; j++) {
                int set = temp%10;
                parsed.add(set);
                temp = (int) Math.floor(temp/10);
            }
            int max = 10;

            for (int f = 0; f <6; f++) {
                if (max < parsed.get(f)) {
                    asc = false;
                } else {
                    max = parsed.get(f);
                }
            }

            if ((parsed.get(parsed.size()-1) == parsed.get(parsed.size()-2)) && (parsed.get(parsed.size()-1) != parsed.get(parsed.size()-3))) {
                twice = true;
            }
            if ((parsed.get(0) == parsed.get(1)) && (parsed.get(0) != parsed.get(2))) {
                twice = true;
            }
            for (int g = 1; g<parsed.size()-2; g++) {
                if ((parsed.get(g) == parsed.get(g+1)) && (parsed.get(g) != parsed.get(g+2)) && (parsed.get(g) != parsed.get(g-1))) {
                    twice = true;
                }
            }
            if (asc == true && twice == true) {
                count++;
            }

        }
        System.out.println(919123-387638);
        System.out.println(count);

    }
}
