package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class IntCode extends Canvas {


    private Map<int[],Integer> grid = new HashMap<>();
    private int relativeBase = 0;
    private ArrayList<Long> code;
    private int outputCount = 0;
    private int paddleX =0;
    private int ballX = 0;
    private int[] output = {0,0};

    public IntCode(ArrayList<Long> code, long input) {
        this.code = code;
        this.code.set(0,input);
    }

    public Map<int[], Integer> getGrid() {
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

    public int intCode(int point) {
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
                i += input(i, instructions);
            } else if (opCode == 4) {
                if ( output(i,instructions) ==-1) {
                    return i + 1;
                } else {
                    i++;
                }
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


    private int output(int i, ArrayList<Long> instructions) {
        long output = this.code.get((int) location(Math.toIntExact(instructions.get(1)),i+1));

        if (this.outputCount == 2) {
            this.outputCount =0;
            boolean check = false;
            for (int[] points: this.grid.keySet()) {
                if (this.output[0] == points[0] && this.output[1] == points[1]) {
                    this.grid.replace(points,(int) output);
                    checkLocation(points);
                    check = true;
                    return -1;
                }
            }
            if(!check){
                this.grid.put(this.output.clone(), (int) output);
            }
        } else {
            this.output[this.outputCount] = (int)output;
            this.outputCount++;
        }
        return 1;
    }

    private void checkLocation(int[] key) {
        if (this.grid.get(key) == 3) {
            this.paddleX = key[0];
        } else if (this.grid.get(key) == 4) {
            this.ballX = key[0];
        }
    }


    private int input(int i, ArrayList<Long> instructions) {
        long input =0;

        if (this.paddleX > this.ballX) {
            input = -1;
        }else if (this.paddleX < this.ballX) {
            input = 1;
        } else  {
            input = 0;
        }

        this.code.set((int) location(Math.toIntExact(instructions.get(1)),i+1),input);
        return 1;
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

    public void playGame() throws InterruptedException {
        int width = 0;
        int height = 0;
        int i = intCode(0);
        for (int[] coord : this.grid.keySet()) {
            if (coord[0] > width) {
                width = coord[0];
            }
            if (coord[1] > height) {
                height = coord[1];
            }
        }

        final int widthF = width * 20 + 40;
        final int heightF = height * 20 + 100;

        final String title = "Test Window";

        //Creating the frame.
        JFrame frame = new JFrame(title);

        frame.setSize(widthF, heightF);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLocationRelativeTo(null);
//        frame.setResizable(false);
        frame.setVisible(true);

        //Creating the canvas.
        Canvas canvas = new Canvas();

//        canvas.setSize(widthF, heightF);
//        canvas.setBackground(Color.BLACK);
        canvas.setVisible(true);
//        canvas.setFocusable(false);


        //Putting it all together.
        frame.add(canvas);

        canvas.createBufferStrategy(3);

        boolean running = true;

        BufferStrategy bufferStrategy;
        Graphics graphics;

        while (running) {
            bufferStrategy = canvas.getBufferStrategy();
            graphics = bufferStrategy.getDrawGraphics();
            graphics.clearRect(0, 0, widthF, heightF);
            i = intCode(i);



            for (int[] coord : this.grid.keySet()) {
                if (coord[0] == -1 && coord[1] == 0) {
                    String score = String.valueOf(this.grid.get(coord));
                    scoreboard(score, graphics, ((widthF / 2) - 20), (heightF - 55));
                }
                if (this.grid.get(coord) == 1) {
                    drawWall(coord[0], coord[1], graphics);
                } else if (this.grid.get(coord) == 2) {
                    drawBlock(coord[0], coord[1], graphics);
                } else if (this.grid.get(coord) == 3) {
                    drawPaddle(coord[0], coord[1], graphics);
                } else if (this.grid.get(coord) == 4) {
                    drawBall(coord[0], coord[1], graphics);
                }
            }
            bufferStrategy.show();
            graphics.dispose();
        }
    }

//



    public void scoreboard(String score, Graphics g, int x, int y) {
        g.setColor(Color.RED);
        g.drawString(score,x, y);
    }

    public void drawWall(int x, int y, Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x*20,y*20,20,20);
    }

    public void drawBall(int x, int y, Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x*20,y*20,20,20);
    }

    public void drawPaddle(int x, int y, Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x*20,y*20,20,10);
    }

    public void drawBlock(int x, int y, Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRoundRect(x*20,y*20,20,20,5,5);
    }






}
