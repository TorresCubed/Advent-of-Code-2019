package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.spi.AbstractResourceBundleProvider;

public class IntCode extends Canvas {


    private ArrayList<int[]> fastestPath = new ArrayList<>();
    private Map<int[],Long> grid = new HashMap<>();
    private int relativeBase = 0;
    private ArrayList<Long> code;
    private int[] currentLocation = {25,25};
    private long lastDirection=0;
    private ArrayList<int[]> deadEnds = new ArrayList<>();
    private long output =0;
    private boolean oxygenFound =false;

    public IntCode(ArrayList<Long> code) {
        this.code = code;
    }

    public Map<int[], Long> getGrid() {
        return grid;
    }

    private long target(int initialization, int target) {
        long value;
        if (initialization == 0) {
            value = this.code.get(Math.toIntExact(this.code.get(target)));
        } else if (initialization ==1){
            value=this.code.get(target);
        } else {
            value = this.code.get(Math.toIntExact(this.code.get(target) + relativeBase));
        }
        return value;
    }


    private long location(int initialization, int target) {
        long value;
        if (initialization ==0) {
            value = this.code.get(target);
        } else if (initialization ==1) {
            value = target;
        } else {
            value = this.code.get(target) + relativeBase;
        }
        return value;

    }

    private ArrayList<Long> codeSet(long instructions){
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

    private int intCode(int point) {
        int opCode;
        ArrayList<Long> instructions;

        for (int i = point; i < code.size(); i++) {
            instructions = codeSet(code.get(i));
            opCode = Math.toIntExact(instructions.get(0));

            if (opCode == 1) {
                i += add(standardSplit(i,instructions));
            } else if (opCode == 2) {
                i += multiply(standardSplit(i,instructions));
            } else if (opCode == 3) {
                input(i, instructions);
                i++;
            } else if (opCode == 4) {
                output(i,instructions);
                i++;
                return i;
            } else if (opCode == 5) {
                i = notZero(i,standardSplit(i, instructions));
            } else if (opCode == 6) {
                i = equalZero(i,standardSplit(i, instructions));
            } else if (opCode == 7) {
                i+= ifLess(standardSplit(i,instructions));
            } else if (opCode == 8) {
                i += ifEqual(standardSplit(i, instructions));
            }  else if (opCode == 9) {
                i += relativeBase(i, instructions);
            }else if (opCode == 99) {
                break;
            }
        }
        return 0;
    }

    private long[] standardSplit(int i, ArrayList<Long> instruction) {
        long location = location(Math.toIntExact(instruction.get(3)), i+3);
        long num1 = target(Math.toIntExact(instruction.get(1)), i + 1);
        long num2 = target(Math.toIntExact(instruction.get(2)), i + 2);
        return new long[]{num1, num2, location};
    }


    private int add(long[] split) {
        this.code.set((int) split[2], split[0] + split[1]);
        return 3;
    }

    private int multiply(long[] split) {
        this.code.set((int) split[2], split[0]*split[1]);

        return 3;
    }


    private void output(int i, ArrayList<Long> instructions) {
        this.output = this.code.get((int) location(Math.toIntExact(instructions.get(1)),i+1));
        int[] target = targetLocation();
        this.grid.put(target,this.output);

        if (this.output==1) {
            fastestPath(target);
        }
    }

    private void fastestPath(int[] nextPath){
        boolean addPath = true;
        for (int[] path: fastestPath) {
            if (path[0] == nextPath[0] && path[1] == nextPath[1]) {
                addPath = false;
            }
        }
        if (addPath)
            fastestPath.add(nextPath);
    }

    private int[] targetLocation(){
        int[] target = {0,0};
        boolean giveDirection = false;
        do {
            if (lastDirection == 1) {
                target[0] = currentLocation[0];
                target[1] = currentLocation[1] + 1;
                giveDirection = true;
            } else if (lastDirection == 2) {
                target[0] = currentLocation[0];
                target[1] = currentLocation[1] - 1;
                giveDirection = true;
            } else if (lastDirection == 3) {
                target[0] = currentLocation[0] - 1;
                target[1] = currentLocation[1];
                giveDirection = true;
            } else if (lastDirection == 4) {
                target[0] = currentLocation[0] + 1;
                target[1] = currentLocation[1];
                giveDirection = true;
            } else {
                System.out.println("Error");
            }
        } while (!giveDirection);
        return target;
    }

    private ArrayList<int[]> getSurroundings(){
        ArrayList<int[]> surroundings = new ArrayList<>();
        int[] north = {this.currentLocation[0],this.currentLocation[1]+1};
        int[] south =  {this.currentLocation[0],this.currentLocation[1]-1};
        int[] east = {this.currentLocation[0]+1,this.currentLocation[1]};
        int[] west = {this.currentLocation[0]-1,this.currentLocation[1]};
        surroundings.add(north);
        surroundings.add(south);
        surroundings.add(east);
        surroundings.add(west);
        return surroundings;
    }

    private ArrayList<int[]> assessSurroundings() {
        ArrayList<int[]> knownDirections = new ArrayList<>();
        for (int[] direction : getSurroundings()) {
            for (int[] location : this.grid.keySet()) {
                if (direction[0] == location[0] && direction[1] == location[1]){
                    knownDirections.add(location);

                }
            }
        }
        return knownDirections;
    }


    private int chooseNextDirection() {
        for (int[] direction: getSurroundings()) {
            boolean exists = false;
            for (int[] existing: this.grid.keySet()) {
                if (direction[0] == existing[0] && direction[1] == existing[1]) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                return instructions(direction);
            }
        }
        for (int[] space: assessSurroundings()) {
            if (this.grid.get(space)==1 && deadPath(space)) {
                return instructions(space);
            }
        }
        return 7;
    }

    private boolean deadPath(int[] target) {
        for (int[] deadEnd: this.deadEnds) {
            if (target[0] == deadEnd[0] && target[1] == deadEnd[1]) {
                return false;
            }
        }
        this.fastestPath.removeIf(previous -> target[0] == previous[0] && target[1] == previous[1]);
        this.deadEnds.add(currentLocation);
        return true;
    }


    private int instructions(int[] target) {
        if (target[0] == this.currentLocation[0]+1 && target[1] == this.currentLocation[1]) {
            return 4;
        }else if (target[0] == this.currentLocation[0]-1 && target[1] == this.currentLocation[1]) {
            return  3;
        }else if (target[0] == this.currentLocation[0] && target[1] == this.currentLocation[1]+1) {
            return 1;
        } if (target[0] == this.currentLocation[0] && target[1] == this.currentLocation[1]-1) {
            return 2;
        }
        return 1;
    }

    private void input(int i, ArrayList<Long> instructions) {
        this.lastDirection = chooseNextDirection();
        this.code.set((int) location(Math.toIntExact(instructions.get(1)),i+1),this.lastDirection);
    }

    private int notZero(int i, long[] split) {
        if (split[0] != 0 ) {
            return (int) (split[1] -1);
        } else {
            return i+2;
        }
    }

    private int equalZero(int i, long[] split) {
        if (split[0] ==0) {
            return (int) (split[1] -1);
        } else {
            return i+2;
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
        this.relativeBase += this.code.get( (int) location(Math.toIntExact(instructions.get(1)),i+1));
        return 1;
    }

    private Frame makeFrame(){
        final String title = "Oxygen Bay";
        JFrame frame = new JFrame(title);
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        return frame;
    }

    private Canvas makeCanvas() {
        Frame frame = makeFrame();
        Canvas canvas = new Canvas();
        canvas.setSize(1000,1000);
        canvas.setVisible(true);
        canvas.setFocusable(false);
        frame.add(canvas);
        canvas.createBufferStrategy(1);

        return canvas;
    }

    private void cleanUp() {
        for (int[] dead: this.deadEnds) {
            this.fastestPath.removeIf(previous -> dead[0] == previous[0] && dead[1] == previous[1]);
            if (fastestPath.size()==0) {
            }
        }
        System.out.println(this.fastestPath.size()+1);

    }
    public void findOxygen() throws InterruptedException {
        Canvas canvas = makeCanvas();
        BufferStrategy bufferStrategy;
        Graphics graphics;
        boolean running = true;
        int i = 0;
        bufferStrategy = canvas.getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();
        drawSelf(this.currentLocation,graphics);
        while (running) {

            graphics = bufferStrategy.getDrawGraphics();
            TimeUnit.MILLISECONDS.sleep(0);


            i = intCode(i);
            attemptMove(graphics);

            bufferStrategy.show();
            graphics.dispose();
            if (this.currentLocation[0] ==25 && this.currentLocation[1] ==25) {
                break;
            }
        }
        spreadOxygen(graphics, bufferStrategy, canvas);
    }

    private ArrayList<int[]> getOxygen() {
        ArrayList oxygen = new ArrayList();
        for (int[] location: this.grid.keySet()) {
            if (this.grid.get(location) ==2) {
                oxygen.add(location);
            }
        }
        return oxygen;
    }

    private void spreadOxygen(Graphics g, BufferStrategy bufferStrategy ,Canvas canvas) throws InterruptedException {
        int count = 0;
        bufferStrategy = canvas.getBufferStrategy();
        g = bufferStrategy.getDrawGraphics();
        int dissipate = 0;
        do {
            g = bufferStrategy.getDrawGraphics();
            dissipate = 0;
            ArrayList<int[]> oxygen = getOxygen();
            System.out.println("Spreading");
            for (int[] air : oxygen) {
                this.currentLocation = air;
                ArrayList<int[]> surroundings = getSurroundings();
                for (int[] spread : surroundings) {
                    for (int[] grid : this.grid.keySet()) {
                        if (grid[0] == spread[0] && grid[1] == spread[1]) {
                            if (this.grid.get(grid) == 1) {
                                drawOxygen(grid, g);
                                dissipate++;
                                this.grid.replace(grid, (long) 1, (long) 2);
                            }
                        }
                    }
                }
            }
            TimeUnit.MILLISECONDS.sleep(5);
            count++;
            bufferStrategy.show();
            g.dispose();
        } while(dissipate >0);
        System.out.println(count);
    }

    private boolean attemptMove(Graphics graphics) throws InterruptedException {
        boolean moved = true;
        boolean oxygen = false;
        int[] targetLocation = targetLocation();
        if (this.output == 0) {
            moved = false;
            drawWall(targetLocation,graphics);
        } else if (this.output == 1) {
            drawSelf(targetLocation,graphics);
            replaceSelf(graphics);
        } else if(this.output ==2) {
            System.out.println("SUCCESS");
            this.oxygenFound = true;
            drawOxygen(targetLocation,graphics);
            cleanUp();
            oxygen = true;
        }
        if (moved)
            this.currentLocation = targetLocation.clone();
        return oxygen;
    }




    private void drawWall(int[] target, Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(target[0]*20,target[1]*20,20,20);
    }

    private void drawSelf(int[] target, Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(target[0]*20,target[1]*20,20,20);
    }

    private void replaceSelf(Graphics g) {
        g.clearRect(this.currentLocation[0] * 20, this.currentLocation[1] * 20, 20, 20);

        if (this.oxygenFound) {
            this.oxygenFound = false;
            drawOxygen(this.currentLocation, g);
        }
    }

    private void drawOxygen(int[] target, Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(target[0]*20,target[1]*20,20,20);
    }






}
