package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class IntCode {


    private int x;
    private int direction;
    private int y;
    private ArrayList<int[]> panels = new ArrayList<>();
    private int relativeBase = 0;
    private ArrayList<Long> code;

    public IntCode(ArrayList<Long> code) {
        this.code = code;
        this.x = 0;
        this.direction = 90;
        this.y = 0;
    }


    public ArrayList<int[]> getPanels() {
        return panels;
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

    public void intCode() {
        int[] starting = {0,0,1};
        this.panels.add(starting);
        boolean outputDef = true;
        long newColor;
        long directionChoice;
        int input=0;
        int opCode;
        long num1;
        long num2;
        int location;
        int mode1;
        int mode2;
        int mode3;
        ArrayList<Long> instructions;
        for (int i = 0; i < code.size(); i++) {
            instructions = codeSet(code.get(i));
            opCode = Math.toIntExact(instructions.get(0));
            mode1 = Math.toIntExact(instructions.get(1));
            mode2 = Math.toIntExact(instructions.get(2));
            mode3 = Math.toIntExact(instructions.get(3));
            if (opCode == 1) {
                num1 =  target(mode1, i + 1);
                num2 =  target(mode2, i + 2);
                location = (int) location(mode3, i + 3);
                this.code.set(location, num1 + num2);
                i += 3;
            } else if (opCode == 2) {
                num1 =  target(mode1, i + 1);
                num2 =  target(mode2, i + 2);
                location = (int) location(mode3, i + 3);
                this.code.set(location, num1 * num2);
                i += 3;
            } else if (opCode == 3) {
                boolean haveBeen = false;
                for (int[] been: panels) {
                    if (been[0] == this.x && been[1] == this.y) {
                        input = been[2];
                        haveBeen = true;
                    }
                }
                if (!haveBeen) {
                    input = 0;
                }

                location = (int) location(mode1, i + 1);
                this.code.set(location, (long)input);
                i++;
            } else if (opCode == 4) {
                location = (int) location(mode1, i + 1);
                i++;
                if (outputDef) {
                    newColor = this.code.get(location);
                    outputDef = false;
                    boolean haveBeen = false;
                    for (int[] been: panels) {
                        if (been[0] == this.x && been[1] == this.y) {
                            haveBeen = true;
                            been[2] = (int) newColor;
                        }
                    }
                    if (!haveBeen) {
                        int[] newPanel = {this.x, this.y, (int) newColor};
                        this.panels.add(newPanel);
                    }
                } else {
                    directionChoice = this.code.get(location);
                    if (directionChoice == 0) {
                        this.direction += 90;
                    } else if (directionChoice == 1) {
                        this.direction -=90;
                    }
                    move();
                    outputDef = true;
                }
            } else if (opCode == 5) {
                num1 = target(mode1, i + 1);
                num2 = target(mode2, i + 2);
                if (num1 != 0) {
                    i = (int) (num2 - 1);
                } else {
                    i += 2;
                }
            } else if (opCode == 6) {
                num1 = target(mode1, i + 1);
                num2 = target(mode2, i + 2);
                if (num1 == 0) {
                    i = (int) (num2 - 1);
                } else {
                    i += 2;
                }
            } else if (opCode == 7) {
                num1 = target(mode1, i + 1);
                num2 = target(mode2, i + 2);
                location = (int) location(mode3, i + 3);
                if (num1 < num2) {
                    this.code.set(location, (long) 1);
                } else {
                    this.code.set(location, (long) 0);
                }
                i += 3;
            } else if (opCode == 8) {
                num1 = target(mode1, i + 1);
                num2 = target(mode2, i + 2);
                location = (int) location(mode3, i + 3);
                if (num1 == num2) {
                    this.code.set(location, (long) 1);
                } else {
                    this.code.set(location, (long) 0);
                }
                i += 3;
            }  else if (opCode == 9) {
                location = (int) location(mode1, i + 1);
                i++;
                this.relativeBase += this.code.get(location);
            }else if (opCode == 99) {
                break;
            }
        }
    }


    private void move() {
        int direction = this.direction%360;
        if (Math.abs(direction) == 90) {
            this.y += direction/Math.abs(direction);
        } else if(Math.abs(direction) == 270) {
            this.y -= direction/Math.abs(direction);
        } else if (direction== 0) {
            this.x += 1;
        } else if(Math.abs(direction) == 180) {
            this.x -= 1;
        }
    }

    public void sortLocations() {
        int[] temp;
        int count = 0;

        do {
            count =0;
            for (int y = 0; y <this.panels.size()-1; y++) {
                temp = this.panels.get(y);
                if (this.panels.get(y+1)[1] > temp[1]){
                    this.panels.set(y,this.panels.get(y+1));
                    this.panels.set(y+1,temp);
                    count++;
                }
            }
        } while (count != 0);

        do {
            count = 0;
            for (int x = 0; x <this.panels.size()-1; x++) {
                if (this.panels.get(x)[1] == this.panels.get(x+1)[1]) {
                    temp = this.panels.get(x);
                    if (this.panels.get(x + 1)[0] < temp[0]) {
                        this.panels.set(x, this.panels.get(x + 1));
                        this.panels.set(x + 1, temp);
                        count++;
                    }
                }
            }
        } while (count != 0);

    }

    public void addloc(int[] space) {
        this.panels.add(space);
    }


}
