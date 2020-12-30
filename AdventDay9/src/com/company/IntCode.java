package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class IntCode {


    private int relativeBase = 0;
    private ArrayList<Long> code;

    public IntCode(ArrayList<Long> code) {
        this.code = code;
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
        int input;
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
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
                scanner.close();
                location = (int) location(mode1, i + 1);
                this.code.set(location, (long) input);
                i++;
            } else if (opCode == 4) {
                location = (int) location(mode1, i + 1);
                i++;
                System.out.println(this.code.get(location));
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


}
