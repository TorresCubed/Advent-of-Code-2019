package com.company;

import java.util.ArrayList;

public class Permutation {

    private ArrayList<int[]> amplifiers;

    public Permutation() {
        this.amplifiers = new ArrayList<int[]>();
    }

    private void addPermutation(int[] a) {
        int[] temp = a.clone();
        this.amplifiers.add(temp);
    }

    public ArrayList<int[]> getAmplifiers() {
        return amplifiers;
    }

    public void heapPermutation(int[] a, int size) {
        if (size==1)
            addPermutation(a);

        for(int i = 0; i< size; i++) {
            heapPermutation(a,size-1);

            if (size%2 ==1) {
                int temp = a[0];
                a[0] = a[size-1];
                a[size-1] = temp;
            } else {
                int temp = a[i];
                a[i] = a[size-1];
                a[size-1] = temp;
            }
        }
    }


}
