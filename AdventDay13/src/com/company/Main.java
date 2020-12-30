package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main extends Canvas {

    public static void main(String[] args) throws InterruptedException {


        ArrayList<Long> instructions = new ArrayList<>();
        try {
            File day12 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay12.txt");
            Scanner scanner = new Scanner(day12);
            String[] data = scanner.nextLine().split(",");

            for (String num : data) {
                instructions.add(Long.parseLong(num));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        for (long i = 0; i < 10000; i++) {
            instructions.add((long) 0);
        }

        IntCode game = new IntCode(instructions, 2);
//
//        int blockCount = 0;
//        for (Integer object : game.getGrid().values()) {
//            if (object.intValue() == 2) {
//                blockCount++;
//            }
//        }
//        System.out.println(game.getGrid().size());
        game.playGame();
//        int width = 0;
//        int height = 0;
//
//        for (int[] coord : game.getGrid().keySet()) {
//            if (coord[0] > width) {
//                width = coord[0];
//            }
//            if (coord[1] > height) {
//                height = coord[1];
//            }
//        }
//
//        final int widthF = width*20+40;
//        final int heightF = height*20+100;
//
//        final String title = "Test Window";
//
//        //Creating the frame.
//        JFrame frame = new JFrame(title);
//
//        frame.setSize(widthF, heightF);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        frame.setLocationRelativeTo(null);
////        frame.setResizable(false);
//        frame.setVisible(true);
//
//        //Creating the canvas.
//        Canvas canvas = new Canvas();
//
////        canvas.setSize(widthF, heightF);
////        canvas.setBackground(Color.BLACK);
//        canvas.setVisible(true);
////        canvas.setFocusable(false);
//
//
//        //Putting it all together.
//        frame.add(canvas);
//
//        canvas.createBufferStrategy(3);
//
//        boolean running = true;
//
//        BufferStrategy bufferStrategy;
//        Graphics graphics;
//
//        while (running) {
//            bufferStrategy = canvas.getBufferStrategy();
//            graphics = bufferStrategy.getDrawGraphics();
//            graphics.clearRect(0, 0, widthF, heightF);
//
//
//
//            for (int[] coord : game.getGrid().keySet()) {
//                if (coord[0] == -1 && coord[1] ==0) {
//                    String score = String.valueOf(game.getGrid().get(coord));
//                    scoreboard(score, graphics,((widthF/2)-20),(heightF-55));
//                }
//                if (game.getGrid().get(coord) == 1) {
//                    drawWall(coord[0],coord[1],graphics);
//                } else if (game.getGrid().get(coord) == 2) {
//                    drawBlock(coord[0],coord[1],graphics);
//                } else if (game.getGrid().get(coord) == 3) {
//                    drawPaddle(coord[0],coord[1],graphics);
//                } else if (game.getGrid().get(coord) == 4) {
//                    drawBall(coord[0],coord[1],graphics);
//                }
//            }
//            bufferStrategy.show();
//            graphics.dispose();
//        }

//        TimeUnit.SECONDS.sleep(1);

    }

//    public static void scoreboard(String score, Graphics g, int x, int y) {
//        g.setColor(Color.RED);
//        g.drawString(score,x, y);
//    }
//
//    public static void drawWall(int x, int y, Graphics g) {
//        g.setColor(Color.DARK_GRAY);
//        g.fillRect(x*20,y*20,20,20);
//    }
//
//    public static void drawBall(int x, int y, Graphics g) {
//        g.setColor(Color.BLUE);
//        g.fillOval(x*20,y*20,20,20);
//    }
//
//    public static void drawPaddle(int x, int y, Graphics g) {
//        g.setColor(Color.RED);
//        g.drawLine(x*20,y*20,x*20+20,y*20+20);
//    }
//
//    public static void drawBlock(int x, int y, Graphics g) {
//        g.setColor(Color.BLACK);
//        g.fillRoundRect(x*20,y*20,20,20,5,5);
//    }
}

