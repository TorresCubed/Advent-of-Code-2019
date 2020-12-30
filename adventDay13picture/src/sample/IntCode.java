package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IntCode {


    private Map<int[],Integer> grid = new HashMap<>();
    private int relativeBase = 0;
    private ArrayList<Long> code;
    private int outputCount = 0;
    private int[] output = {0,0};

    public IntCode(ArrayList<Long> code) {
        this.code = code;
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

    public void intCode() {
        int opCode;
        ArrayList<Long> instructions;

        for (int i = 0; i < code.size(); i++) {
            instructions = codeSet(code.get(i));
            opCode = Math.toIntExact(instructions.get(0));

            if (opCode == 1) {
                i += add(standardSplit(i,instructions));
            } else if (opCode == 2) {
                i += multiply(standardSplit(i,instructions));
            } else if (opCode == 3) {

            } else if (opCode == 4) {
                i += output(i,instructions);
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
            this.grid.put(this.output.clone(),(int)output);
        } else {
            this.output[this.outputCount] = (int)output;
            this.outputCount++;
        }
        return 1;
    }


    private int input() {
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






}
