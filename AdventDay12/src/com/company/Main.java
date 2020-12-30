package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

//        try{
//            File day11 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay11.txt");
//            Scanner scanner = new Scanner(day11);
//            String[] data = scanner.nextLine().split(",");
//
//
//            scanner.close();
//        }catch (FileNotFoundException e) {
//            System.out.println("IOException: " + e.getMessage());
//        }

        ArrayList<Integer> positioning = new ArrayList<>();
        positioning.add(-8);
        positioning.add(-10);
        positioning.add(0);

        Moon A = new Moon(positioning);

        positioning.set(0,5);
        positioning.set(1,5);
        positioning.set(2,10);
        Moon B = new Moon(positioning);

        positioning.set(0,2);
        positioning.set(1,-7);
        positioning.set(2,3);

        Moon C = new Moon(positioning);

        positioning.set(0,9);
        positioning.set(1,-8);
        positioning.set(2,-3);

        Moon D = new Moon(positioning);

        solar mySystem = new solar();
        mySystem.addMoon(A);
        mySystem.addMoon(B);
        mySystem.addMoon(C);
        mySystem.addMoon(D);


        for (Moon object: mySystem.getSystem()) {
            object.display();
        }








    }



}
