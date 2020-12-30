package com.company;

import javafx.scene.layout.BorderPane;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IntCode {

    private Map<int[], Long> grid = new HashMap<>();
    long x =0;
    long y = 0;
    boolean xy = true;
    private int relativeBase = 0;
    private int count = 0;
    private ArrayList<Long> code;
    private long output = 0;

    public IntCode(ArrayList<Long> code) {
        this.code = code;
    }

    private long target(int initialization, int target) {
        long value;
        if (initialization == 0) {
            value = this.code.get(Math.toIntExact(this.code.get(target)));
        } else if (initialization == 1) {
            value = this.code.get(target);
        } else {
            value = this.code.get(Math.toIntExact(this.code.get(target) + relativeBase));
        }
        return value;
    }


    private long location(int initialization, int target) {
        long value;
        if (initialization == 0) {
            value = this.code.get(target);
        } else if (initialization == 1) {
            value = target;
        } else {
            value = this.code.get(target) + relativeBase;
        }
        return value;

    }

    private ArrayList<Long> codeSet(long instructions) {
        ArrayList<Long> set = new ArrayList<>();
        long temp = instructions % 100;
        set.add(temp);
        instructions /= 100;
        temp = instructions % 10;
        set.add(temp);
        instructions /= 10;
        temp = instructions % 10;
        set.add(temp);
        temp = (instructions / 10);
        set.add(temp);
        return set;
    }

    public long intCode(ArrayList<Long> code, long x, long y) throws IndexOutOfBoundsException{
        this.x = x;
        this.code = (ArrayList<Long>) code.clone();
        this.y = y;
        int opCode;
        ArrayList<Long> instructions;

        for (int i = 0; i < code.size(); i++) {
            instructions = codeSet(code.get(i));
            opCode = Math.toIntExact(instructions.get(0));

            if (opCode == 1) {
                i += add(standardSplit(i, instructions));
            } else if (opCode == 2) {
                i += multiply(standardSplit(i, instructions));
            } else if (opCode == 3) {
                input(i, instructions);
                i++;
            } else if (opCode == 4) {
                output(i, instructions);
                return this.output;
            } else if (opCode == 5) {
                i = notZero(i, standardSplit(i, instructions));
            } else if (opCode == 6) {
                i = equalZero(i, standardSplit(i, instructions));
            } else if (opCode == 7) {
                i += ifLess(standardSplit(i, instructions));
            } else if (opCode == 8) {
                i += ifEqual(standardSplit(i, instructions));
            } else if (opCode == 9) {
                i += relativeBase(i, instructions);
            } else if (opCode == 99) {
                break;
            }
        }
        return 0;
    }

    private long[] standardSplit(int i, ArrayList<Long> instruction) {
        long location = location(Math.toIntExact(instruction.get(3)), i + 3);
        long num1 = target(Math.toIntExact(instruction.get(1)), i + 1);
        long num2 = target(Math.toIntExact(instruction.get(2)), i + 2);
        return new long[]{num1, num2, location};
    }


    private int add(long[] split) {
        this.code.set((int) split[2], split[0] + split[1]);
        return 3;
    }

    private int multiply(long[] split) {
        this.code.set((int) split[2], split[0] * split[1]);

        return 3;
    }


    private void output(int i, ArrayList<Long> instructions) {
        this.output = this.code.get((int) location(Math.toIntExact(instructions.get(1)), i + 1));
    }


    private void input(int i, ArrayList<Long> instructions) throws IndexOutOfBoundsException {
        long input = 0;
        if (this.xy) {
            input = this.x;
            this.xy = false;
        } else {
            input = this.y;
            this.xy = true;
        }
        this.code.set((int) location(Math.toIntExact(instructions.get(1)), i + 1), input);

    }

    private int notZero(int i, long[] split) {
        if (split[0] != 0) {
            return (int) (split[1] - 1);
        } else {
            return i + 2;
        }
    }

    private int equalZero(int i, long[] split) {
        if (split[0] == 0) {
            return (int) (split[1] - 1);
        } else {
            return i + 2;
        }
    }


    private int ifLess(long[] split) {
        if (split[0] < split[1]) {
            this.code.set((int) split[2], (long) 1);
        } else {
            this.code.set((int) split[2], (long) 0);
        }
        return 3;
    }


    private int ifEqual(long[] split) {
        if (split[0] == split[1]) {
            this.code.set((int) split[2], (long) 1);
        } else {
            this.code.set((int) split[2], (long) 0);
        }
        return 3;
    }

    private int relativeBase(int i, ArrayList<Long> instructions) {
        this.relativeBase += this.code.get((int) location(Math.toIntExact(instructions.get(1)), i + 1));
        return 1;
    }


//
//    private Frame makeFrame(){
//        final String title = "Scaffolding";
//        JFrame frame = new JFrame(title);
//        frame.setSize(1200, 1100);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        frame.setLocationRelativeTo(null);
//        frame.setResizable(false);
//        frame.setVisible(true);
//
//        return frame;
//    }
//
//    private Canvas makeCanvas() {
//        Frame frame = makeFrame();
//        Canvas canvas = new Canvas();
//        canvas.setSize(1200,1100);
//        canvas.setVisible(true);
//        canvas.setFocusable(false);
//        frame.add(canvas);
//        canvas.createBufferStrategy(2);
//
//        return canvas;
//    }
//
//    public void showScaffolding() throws InterruptedException {
//        Canvas canvas = makeCanvas();
//        BufferStrategy bufferStrategy;
//        Graphics graphics;
//        boolean running = true;
//        int i = 0;
//        bufferStrategy = canvas.getBufferStrategy();
//        graphics = bufferStrategy.getDrawGraphics();
////        drawSelf(this.currentLocation,graphics);
////        while (running) {
//
//            graphics = bufferStrategy.getDrawGraphics();
//            TimeUnit.MILLISECONDS.sleep(0);
//
//            intCode(i);
////            attemptMove(graphics);
//
////        }
//
//        System.out.println(this.output);
//
//        for (int[] ship:this.grid.keySet()) {
//            if (this.grid.get(ship) == 35) {
//                drawScaffolding(ship,graphics);
//            } else if(this.grid.get(ship) == 46) {
//                drawSpace(ship,graphics);
//            } else if(this.grid.get(ship) == '^'){
//                drawNorth(ship,graphics);
//            }else if(this.grid.get(ship) == '>'){
//                drawEast(ship,graphics);
//            }else if(this.grid.get(ship) == '<'){
//                drawWest(ship,graphics);
//            }else if(this.grid.get(ship) == 'v'){
//                drawSouth(ship,graphics);
//            }
//        }
////
////        int sum = 0;
////        for (int[] ship:this.grid.keySet()) {
////            if (this.grid.get(ship) == 35) {
////                int count = 0;
////                this.currentLocation = ship;
////                for (int[] surroundings: getSurroundings()){
////                    for (int[] all: this.grid.keySet()) {
////                        if (all[0] == surroundings[0] && all[1] == surroundings[1]) {
////                            if (this.grid.get(all) == 35) {
////                                count++;
////                            }
////                        }
////                    }
////                }
////                if (count ==4) {
////                    sum += (ship[0]*ship[1]);
////                }
////            }
////        }
////        System.out.println(sum);
//
//
//        while (true) {
//            graphics = bufferStrategy.getDrawGraphics();
//            bufferStrategy.show();
////            graphics.dispose();
//        }























//        spreadOxygen(graphics, bufferStrategy, canvas);
//    }
//
//    private ArrayList<int[]> getOxygen() {
//        ArrayList oxygen = new ArrayList();
//        for (int[] location: this.grid.keySet()) {
//            if (this.grid.get(location) ==2) {
//                oxygen.add(location);
//            }
//        }
//        return oxygen;
//    }

//    private void spreadOxygen(Graphics g, BufferStrategy bufferStrategy ,Canvas canvas) throws InterruptedException {
//        int count = 0;
//        bufferStrategy = canvas.getBufferStrategy();
//        g = bufferStrategy.getDrawGraphics();
//        int dissipate = 0;
//        do {
//            g = bufferStrategy.getDrawGraphics();
//            dissipate = 0;
//            ArrayList<int[]> oxygen = getOxygen();
//            System.out.println("Spreading");
//            for (int[] air : oxygen) {
//                this.currentLocation = air;
//                ArrayList<int[]> surroundings = getSurroundings();
//                for (int[] spread : surroundings) {
//                    for (int[] grid : this.grid.keySet()) {
//                        if (grid[0] == spread[0] && grid[1] == spread[1]) {
//                            if (this.grid.get(grid) == 1) {
//                                drawOxygen(grid, g);
//                                dissipate++;
//                                this.grid.replace(grid, (long) 1, (long) 2);
//                            }
//                        }
//                    }
//                }
//            }
//            TimeUnit.MILLISECONDS.sleep(5);
//            count++;
//            bufferStrategy.show();
//            g.dispose();
//        } while(dissipate >0);
//        System.out.println(count);
//    }

//    private boolean attemptMove(Graphics graphics) throws InterruptedException {
//        boolean moved = true;
//        boolean oxygen = false;
//        int[] targetLocation = targetLocation();
//        if (this.output == 0) {
//            moved = false;
//            drawWall(targetLocation,graphics);
//        } else if (this.output == 1) {
//            drawSelf(targetLocation,graphics);
//            replaceSelf(graphics);
//        } else if(this.output ==2) {
//            System.out.println("SUCCESS");
//            this.oxygenFound = true;
//            drawOxygen(targetLocation,graphics);
//            cleanUp();
//            oxygen = true;
//        }
//        if (moved)
//            this.currentLocation = targetLocation.clone();
//        return oxygen;
//    }

//
//
//
//    private void drawSpace(int[] target, Graphics g) {
//        g.setColor(Color.black);
//        g.fillRect(target[0]*20,target[1]*20,20,20);
//    }
//
//    private void drawSelf(int[] target, Graphics g) {
//        g.setColor(Color.BLUE);
//        g.fillOval(target[0]*20,target[1]*20,20,20);
//    }
////
////    private void replaceSelf(Graphics g) {
////        g.clearRect(this.currentLocation[0] * 20, this.currentLocation[1] * 20, 20, 20);
////
////        if (this.oxygenFound) {
////            this.oxygenFound = false;
////            drawOxygen(this.currentLocation, g);
////        }
////    }
//
//    private void drawNorth(int[] target, Graphics g) {
//        g.setColor(Color.RED);
//        g.fillRect(target[0]*20,target[1]*20,20,10);
//    }
//
//    private void drawSouth(int[] target, Graphics g) {
//        g.setColor(Color.RED);
//        g.fillRect(((target[0]*20)+10),target[1]*20,20,10);
//    }
//
//    private void drawEast(int[] target, Graphics g) {
//        g.setColor(Color.RED);
//        g.fillRect(target[0]*20,((target[1]*20)+10),10,20);
//    }
//
//    private void drawWest(int[] target, Graphics g) {
//        g.setColor(Color.RED);
//        g.fillRect(target[0]*20,target[1]*20,10,20);
//    }
//
//    private void drawScaffolding(int[] target, Graphics g) {
//        g.setColor(Color.YELLOW);
//        g.fillRect(target[0]*20,target[1]*20,19,19);
//    }
//
//





}
