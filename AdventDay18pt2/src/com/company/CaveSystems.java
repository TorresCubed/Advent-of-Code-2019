package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CaveSystems {

    private ArrayList<AutoRobit> possibleRobits = new ArrayList<>();
    private ArrayList<AutoRobit> redundantRobits = new ArrayList<>();
    private ArrayList<AutoRobit> tempRobits = new ArrayList<>();
    private ArrayList<Coordinate> map = new ArrayList<>();
    private ArrayList<Coordinate> vaultStarts = new ArrayList<>();
    private ArrayList<Coordinate> keyList = new ArrayList<>();

    public CaveSystems(File todaysInput) {
        readCaveSystem(todaysInput);
        connectCaveSystem();
        reduceComplexity();

    }

    public ArrayList<Coordinate> getVaultStarts() {
        return vaultStarts;
    }

    public void setVaultStarts(ArrayList<Coordinate> vaultStarts) {
        this.vaultStarts = vaultStarts;
    }

    public ArrayList<AutoRobit> getPossibleRobits() {
        return possibleRobits;
    }

    public void setPossibleRobits(ArrayList<AutoRobit> possibleRobits) {
        this.possibleRobits = possibleRobits;
    }

    public ArrayList<AutoRobit> getTempRobits() {
        return tempRobits;
    }

    public void setTempRobits(ArrayList<AutoRobit> tempRobits) {
        this.tempRobits = tempRobits;
    }

    public ArrayList<Coordinate> getStart() {
        return vaultStarts;
    }

    public ArrayList<Coordinate> getMap() {
        return map;
    }

    private void readCaveSystem(File todaysInput){
        try {
            Scanner scanner = new Scanner(todaysInput);
            produceCaveSystem(scanner);
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private void connectCaveSystem() {
        for (Coordinate target: this.map) {
            if (target.getNorthNeighbor() == null)
                assignNorthNeighbor(target);
            if (target.getEastNeighbor() == null)
                assignEastNeighbor(target);
            if (target.getSouthNeighbor() == null)
                assignSouthNeighbor(target);
            if (target.getWestNeighbor() == null)
                assignWestNeighbor(target);
        }
    }

    private void assignNorthNeighbor(Coordinate current) {
        for (Coordinate map: this.map) {
            if (map.getX() == current.getX()) {
                if (map.getY() == current.getY()+1){
                    current.setNorthNeighbor(map);
                    map.setSouthNeighbor(current);
                    break;
                }
            }
        }
    }

    private void assignEastNeighbor(Coordinate current) {
        for (Coordinate map: this.map) {
            if (map.getY() == current.getY()) {
                if (map.getX() == current.getX()+1){
                    current.setEastNeighbor(map);
                    map.setWestNeighbor(current);
                    break;
                }
            }
        }
    }

    private void assignWestNeighbor(Coordinate current) {
        for (Coordinate map: this.map) {
            if (map.getY() == current.getY()) {
                if (map.getX() == current.getX()-1){
                    current.setWestNeighbor(map);
                    map.setEastNeighbor(current);
                    break;
                }
            }
        }
    }

    private void assignSouthNeighbor(Coordinate current) {
        for (Coordinate map: this.map) {
            if (map.getX() == current.getX()) {
                if (map.getY() == current.getY()-1){
                    current.setSouthNeighbor(map);
                    map.setNorthNeighbor(current);
                    break;
                }
            }
        }
    }


    private void produceCaveSystem(Scanner scanner) {
        int y = 0;
        while (scanner.hasNext()) {
            int x= 0;
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                Coordinate temp = new Coordinate(x, y, line.charAt(i));
                if (line.charAt(i) == '@') {
                    this.vaultStarts.add(temp);
                }
                if (Character.isLowerCase(line.charAt(i)))
                    this.keyList.add(temp);
                this.map.add(temp);
                x++;
            }
            y++;
        }
    }

    public void drawCave() {
        int temp = -1;
        for (Coordinate coordinate: this.map) {
            if (coordinate.getX() <temp){
                System.out.println();
            }
            System.out.print(coordinate.getIdentifier());
            temp = coordinate.getX();
        }
    }

    public void addRobit(AutoRobit newRobit) {
        this.tempRobits.add(newRobit);
    }



    private void reduceComplexity(){
        int change = 0;
        do {
            change = 0;
            for (Coordinate point : map) {
                if (point.getIdentifier() == '.') {
                    int count = 0;
                    for (Coordinate neighbor : point.shareNeighbors()) {
                        if (neighbor.getIdentifier() == '#') {
                            count++;
                        }
                    }
                    if (count == 3) {
                        point.setIdentifier('#');
                        change++;
                    }
                }
            }
        } while (change >0);
    }

    private void initialRobits() throws CloneNotSupportedException {
        AutoRobit newAutorobit = new AutoRobit(this,this.vaultStarts);
        this.possibleRobits.add(newAutorobit);
        newAutorobit.breakout();
        this.possibleRobits.addAll(this.tempRobits);
        this.tempRobits.clear();
    }


    public AutoRobit collectAllKeys() throws CloneNotSupportedException {
        initialRobits();
        AutoRobit quickest = new AutoRobit(this, this.vaultStarts);
        quickest.setTotalDistance(9999999);
        int count = 0;
        while (true) {
            if (this.possibleRobits.size() == 0) {
                break;
            }
            quickest = findNextKey(quickest,count);
            count++;
            evaluateRedundantRobits(count);
            for (AutoRobit variation: this.possibleRobits) {
                if (variation.getOwnedKeys().size() == count)
                    variation.breakout();
            }
            this.possibleRobits.addAll(this.tempRobits);
            this.tempRobits.clear();

        }
        return quickest;
    }


    private AutoRobit findNextKey(AutoRobit quickest, int count){
        for (AutoRobit variation: this.possibleRobits){
            if (variation.getOwnedKeys().size() == count){
                variation. findNextKey();
                if (variation.getOwnedKeys().containsAll(this.keyList)){
                    if (quickest.getTotalDistance() > variation.getTotalDistance()){
                        quickest = variation;
                    }
                    this.redundantRobits.add(variation);
                }
            }
        }
        return quickest;
    }

    private void evaluateRedundantRobits(int count) {
        System.out.println(this.possibleRobits.size());
        for (AutoRobit compare: this.possibleRobits) {
            if (compare.getOwnedKeys().size() == count) {
                if (!this.redundantRobits.contains(compare)) {
                    for (AutoRobit toThis : this.possibleRobits) {
                        if (toThis.getOwnedKeys().size() == count) {
                            if (!this.redundantRobits.contains(toThis)) {
                                if (compare != toThis) {
                                    if (compare.getCurrentCoords().containsAll(toThis.getCurrentCoords())) {
                                        if (compare.getOwnedKeys().containsAll(toThis.getOwnedKeys())) {
                                            if (toThis.getOwnedKeys().containsAll(compare.getOwnedKeys())) {
                                                if (toThis.getTotalDistance() <= compare.getTotalDistance()) {
                                                    this.redundantRobits.add(compare);
                                                    break;
                                                } else if (toThis.getTotalDistance() > compare.getTotalDistance()) {
                                                    this.redundantRobits.add(toThis);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        this.possibleRobits.removeAll(this.redundantRobits);
        this.redundantRobits.clear();
    }



















}
