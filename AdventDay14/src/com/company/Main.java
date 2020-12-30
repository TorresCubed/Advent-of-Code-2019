package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {


        ArrayList<String> data = new ArrayList<>();
        try {
            File day14 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay14.txt");
            Scanner scanner = new Scanner(day14);
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                data.add(line);
            }


            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        Map<String,Reaction> chemistry = new HashMap<>();

        for (String reaction: data) {
            Reaction temp = new Reaction(reaction);
            chemistry.put(temp.getProductName(),temp);
        }

////        long ore=chemistry.get("FUEL").produceProduct(1,chemistry);
//            for (String each: chemistry.keySet()) {
//                chemistry.get(each).setRemainder(0);
//            }
//        long oreHarvest = (long)1000000000000.0;
//
//        int x1 = 10000;
//        int x2 = 100000;
//        long y1 = chemistry.get("FUEL").produceProduct(x1,chemistry);
//        for (String each: chemistry.keySet()) {
//            chemistry.get(each).setRemainder(0);
//        }
//        long y2 =chemistry.get("FUEL").produceProduct(x2,chemistry);
//
//        double slope = (double)(y2-y1)/(x2-x1);
//        double b = y1 - slope*x1;
//        long fuel = (long)Math.ceil((oreHarvest-b)/slope);
//        System.out.println(fuel);

//        int maxFuel = (int)Math.floor((double)oreHarvest/ore);
//        System.out.println(maxFuel);
//        double test = (long)(1000000000000.0 / ore);
//        long test2 = (chemistry.get("FUEL").produceProduct(((long)(1000000000000.0) / ore),chemistry));
//
//        double test3 = (1000000000000.0/(double)test2);
//        System.out.println(test);
//        System.out.println(test2);
//        System.out.println(test3);
//        System.out.println(test*test3);

        long temp =0;
        long min = 2371000;

        do {
            min++;
            for (String each: chemistry.keySet()) {
                chemistry.get(each).setRemainder(0);
            }
            temp = chemistry.get("FUEL").produceProduct(min,chemistry);
        } while(temp <= 1000000000000.0);
        System.out.println(min-1);


//        for (String reaction: chemistry.keySet()) {
//            System.out.println(chemistry.get(reaction).getProductName());
//        }


    }
}
