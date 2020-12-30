package com.company;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CaveSystems {

    private ArrayList<AutoRobit> possibleRobits = new ArrayList<>();
    private ArrayList<AutoRobit> redundantRobits = new ArrayList<>();
    private ArrayList<AutoRobit> tempRobits = new ArrayList<>();
    private ArrayList<Vault> vaults = new ArrayList<>();
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


    public  void produceVaults() {
//        ArrayList<Coordinate> vault1 = new ArrayList<>();
//        ArrayList<Coordinate> vault2 = new ArrayList<>();
//        ArrayList<Coordinate> vault3 = new ArrayList<>();
//        ArrayList<Coordinate> vault4 = new ArrayList<>();
//        for (Coordinate point: map){
//            if (point.getX() <= vaultStarts.get(0).getX()+1 && point.getY() <= vaultStarts.get(0).getY()+1){
//                vault1.add(point);
//            }
//            if (point.getX() >= vaultStarts.get(1).getX()-1 && point.getY() <= vaultStarts.get(1).getY()+1){
//                vault2.add(point);
//            }
//            if (point.getX() <= vaultStarts.get(2).getX()+1 && point.getY() >= vaultStarts.get(2).getY()-1){
//                vault3.add(point);
//            }
//            if (point.getX() >= vaultStarts.get(3).getX()-1 && point.getY() >= vaultStarts.get(3).getY()-1){
//                vault4.add(point);
//            }
//        }

        Vault vaultA = new Vault(this.vaultStarts.get(0));
        Vault vaultB = new Vault(this.vaultStarts.get(1));
        Vault vaultC = new Vault(this.vaultStarts.get(2));
        Vault vaultD = new Vault(this.vaultStarts.get(3));
        this.vaults.add(vaultA);
        this.vaults.add(vaultB);
        this.vaults.add(vaultC);
        this.vaults.add(vaultD);

    }

    //===================================================================================================

    public void addRobit(AutoRobit newRobit) {
        this.tempRobits.add(newRobit);
    }

    private void initialPath() throws CloneNotSupportedException {
        AutoRobit newRobits = new AutoRobit(this.vaults,this);
        newRobits.setVaults(this.vaults);
        this.possibleRobits.add(newRobits);
        newRobits.setInitialPaths();
        this.possibleRobits.addAll(this.tempRobits);
        this.tempRobits.clear();
    }

    public AutoRobit findShortestPath() throws CloneNotSupportedException {
        initialPath();
        boolean end = false;
        AutoRobit fastest = new AutoRobit(this.vaults, this);
        fastest.setTotalDistance(999999999);
        int count = 0;
        while (!end){
            if (this.possibleRobits.size() == 0){
                break;
            }
            fastest = makeNextMove(fastest,count);
            evaluateRedundantPaths();
            for (AutoRobit move: this.possibleRobits) {
                move.;
            }
            this.possibleRobits.addAll(this.tempRobits);
            this.tempRobits.clear();
            count++;
        }
        return fastest;
    }


    private AutoRobit makeNextMove(AutoRobit shortest, int count) {
        for (AutoRobit robitMove: this.possibleRobits) {
            if (robitMove.getOwnedKeys().size() == count) {
                robitMove.pathToNextKey();
                if (robitMove.getOwnedKeys().containsAll(this.keyList)) {
                    if (shortest.getTotalDistance() > robitMove.getTotalDistance()) {
                        shortest = robitMove;
                    }
                    this.redundantRobits.add(robitMove);
                }
            }
        }
        return shortest;
    }

    private void evaluateRedundantPaths(){
        System.out.println(this.possibleRobits.size());
        for (AutoRobit compare: this.possibleRobits){
            if (!this.redundantRobits.contains(compare)) {
                for (AutoRobit toThis : this.possibleRobits) {
                    if (!this.redundantRobits.contains(toThis)) {
                        if (compare != toThis) {
                            if (compare.getCurrentCoord() == toThis.getCurrentCoord()) {
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
        this.possibleRobits.removeAll(this.redundantRobits);
        this.redundantRobits.clear();
    }



}
