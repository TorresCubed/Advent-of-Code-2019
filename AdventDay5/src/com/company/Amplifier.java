package com.company;

import java.util.ArrayList;

public class Amplifier {

    private int i;
    private boolean amp;
    private int phase;
    private ArrayList<Integer> code;

    public Amplifier(int phase, ArrayList<Integer> code) {
        this.i = 0;
        this.phase = phase;
        this.amp = false;
        this.code = code;
    }

    private int target(int initialization, int target) {
        int value;
        if (initialization == 0) {
            value = this.code.get(this.code.get(target));
        } else{
            value=this.code.get(target);
        }
        return value;
    }

    private int location(int initialization, int target) {
        int value;
        if (initialization ==0) {
            value = this.code.get(target);
        } else {
            value = target;
        }
        return value;

    }

    private ArrayList<Integer> codeSet(int instructions){
        ArrayList<Integer> set = new ArrayList<>();
        int temp = instructions % 100;
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

    public int intCode(int recieved) {
        int input = 0;
        int opCode;
        int num1;
        int num2;
        int location;
        int mode1;
        int mode2;
        int mode3;
        ArrayList<Integer> instructions;
        for (int i = this.i; i < code.size(); i++) {
            instructions = codeSet(code.get(i));
            opCode = instructions.get(0);
            mode1 = instructions.get(1);
            mode2 = instructions.get(2);
            mode3 = instructions.get(3);
            if (opCode == 1) {
                num1 = target(mode1, i + 1);
                num2 = target(mode2, i + 2);
                location = location(mode3, i + 3);
                code.set(location, num1 + num2);
                i += 3;
            } else if (opCode == 2) {
                num1 = target(mode1, i + 1);
                num2 = target(mode2, i + 2);
                location = location(mode3, i + 3);
                code.set(location, num1 * num2);
                i += 3;
            } else if (opCode == 3) {
                if (!this.amp) {
                    input = this.phase;
                    this.amp = true;
                } else {
                    input = recieved;
                }
                location = location(mode1, i + 1);
                this.code.set(location, input);
                i++;
            } else if (opCode == 4) {
                location = location(mode1, i + 1);
                i++;
                this.i = i;
                return  this.code.get(location);
            } else if (opCode == 5) {
                num1 = target(mode1, i + 1);
                num2 = target(mode2, i + 2);
                if (num1 != 0) {
                    i = num2 - 1;
                } else {
                    i += 2;
                }
            } else if (opCode == 6) {
                num1 = target(mode1, i + 1);
                num2 = target(mode2, i + 2);
                if (num1 == 0) {
                    i = num2 - 1;
                } else {
                    i += 2;
                }
            } else if (opCode == 7) {
                num1 = target(mode1, i + 1);
                num2 = target(mode2, i + 2);
                location = location(mode3, i + 3);
                if (num1 < num2) {
                    this.code.set(location, 1);
                } else {
                    this.code.set(location, 0);
                }
                i += 3;
            } else if (opCode == 8) {
                num1 = target(mode1, i + 1);
                num2 = target(mode2, i + 2);
                location = location(mode3, i + 3);
                if (num1 == num2) {
                    this.code.set(location, 1);
                } else {
                    this.code.set(location, 0);
                }
                i += 3;
            } else if (opCode == 99) {
                return opCode;
            }
        }
        return 99;
    }



}
