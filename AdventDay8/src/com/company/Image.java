package com.company;

import java.util.ArrayList;

public class Image {

    private ArrayList<ArrayList<Integer>> pixel = new ArrayList<>();
    private int layer = 0;

    public Image() {
    }

    public ArrayList<ArrayList<Integer>> getPixel() {
        return this.pixel;
    }

    public int getLayer() {
        return this.layer;
    }

    public void makeImage(int wide, int tall, ArrayList<Integer> image) {
        int count=0;
        do {
            this.pixel.add(new ArrayList<Integer>());

           for (int t = 0; t<tall; t++) {
               for (int w = 0; w<wide;w++) {
                   this.pixel.get(this.layer).add(image.get(count));
                   count++;
               }
           }
           this.layer++;
        } while (count < image.size());
        layer -=1;
    }

    public int countType(int type, int layer) {
        int count = 0;
        for (int num: this.pixel.get(layer)) {
            if (num == type) {
                count++;
            }
        }
        return count;
    }

    public char finalDisplay(int position) {
        for (ArrayList<Integer> layer:pixel) {
            int temp = layer.get(position);
            if (temp == 0) {
                return ' ';
            } else if (temp == 1) {
                return '@';
            }
        }
        return ' ';
    }
}

