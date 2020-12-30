package com.company;

import java.util.ArrayList;

public class AutoRobit implements Cloneable {

    private ArrayList<Coordinate> openArea = new ArrayList<>();
    private ArrayList<Coordinate> ownedKeys = new ArrayList<>();
    private CaveSystems myCave;
    private ArrayList<Coordinate> currentCoords = new ArrayList<>();
    private ArrayList<Coordinate> availableKeys = new ArrayList<>();
    private ArrayList<Path> vaultList = new ArrayList<>();
    private Coordinate targetKey;
    private ArrayList<Coordinate> startPoints = new ArrayList<>();
    private int totalDistance=0;

    public AutoRobit(CaveSystems myCave, ArrayList<Coordinate> startPoints) {
        initialize(startPoints);
        this.myCave = myCave;
    }


    private void initialize(ArrayList<Coordinate> startPoints) {
        this.startPoints = startPoints;
        this.currentCoords = (ArrayList<Coordinate>) startPoints.clone();
        for (Coordinate start: this.currentCoords) {
            Path newPath = new Path();
            newPath.setCurrentCoord(start);
            this.vaultList.add(newPath);
        }
    }

    public ArrayList<Coordinate> getAvailableKeys() {
        return availableKeys;
    }

    public ArrayList<Coordinate> getCurrentCoords() {
        return currentCoords;
    }

    public void setAvailableKeys(ArrayList<Coordinate> availableKeys) {
        this.availableKeys = availableKeys;
    }

    public Coordinate getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(Coordinate targetKey) {
        this.targetKey = targetKey;
    }
    public ArrayList<Path> getPathList() {
        return vaultList;
    }

    public void setPathList(ArrayList<Path> pathList) {
        this.vaultList = pathList;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public ArrayList<Coordinate> getOwnedKeys() {
        return ownedKeys;
    }

    public void setOwnedKeys(ArrayList<Coordinate> ownedKeys) {
        this.ownedKeys = ownedKeys;
    }


    private void addPath(AutoRobit newRobit) throws CloneNotSupportedException {
        newRobit.setOwnedKeys((ArrayList<Coordinate>) this.ownedKeys.clone());
        newRobit.setAvailableKeys((ArrayList<Coordinate>) this.availableKeys.clone());
        newRobit.getPathList().clear();
        for (Path list: this.vaultList) {
            newRobit.getPathList().add(list.cloneAttributesAndAdd());
        }
        this.myCave.addRobit(newRobit);
    }

    private void assessOwnedKeys(){
        for (Path vault:this.vaultList) {
            for (Coordinate pathOwned : vault.getOwnedKeys()) {
                boolean add = true;
                if (this.ownedKeys.contains(pathOwned)) {
                    add = false;
                }
                if (add)
                    this.ownedKeys.add(pathOwned);
            }
        }

    }

    private void provideKeyList(){
        assessOwnedKeys();
        for (Path path: this.vaultList){
            path.setOwnedKeys(this.ownedKeys);
        }
    }
    private void getOpenArea(){
        this.openArea.clear();
        for (Path path: this.vaultList){
            path.getOpenArea().clear();
            path.setOpenArea(path.getCurrentCoord());
            this.openArea.addAll(path.getOpenArea());
        }
    }

    private void determineAvailableKeys(){
        this.availableKeys.clear();
        for (Path vault: this.vaultList){
            this.availableKeys.addAll(vault.getAvailableKeys());
        }
    }

    private void setTargetsAndSplit() throws CloneNotSupportedException {
        boolean first = true;
        for (Coordinate available: this.availableKeys) {
            if (first) {
                this.targetKey = available;
                first = false;
            } else {
                AutoRobit newRobit = new AutoRobit(this.myCave, this.startPoints);
                newRobit.setTargetKey(available);
                addPath(newRobit);
            }
        }
    }

    private void assessTotalDistance() {
        int distance =0;
        for (Path path: this.vaultList){
            distance+= path.getPathLength();
        }
        this.totalDistance = distance;
    }

    public void breakout() throws CloneNotSupportedException {
        getOpenArea();
        determineAvailableKeys();
        setTargetsAndSplit();
    }

    public void findNextKey(){
        this.vaultList.get(determineVaultNumber()).pathToNextKey(this.targetKey);
        provideKeyList();
        int totalMoves = 0;
        for (Path path : this.vaultList){
            totalMoves += path.getPathLength();
        }
        this.totalDistance = totalMoves;
        for (int i = 0; i <this.currentCoords.size();i++){
            this.currentCoords.set(i,this.vaultList.get(i).getCurrentCoord());
        }
    }

    private int determineVaultNumber() {
        for (int i = 0; i <this.vaultList.size(); i++){
            if (this.vaultList.get(i).getAvailableKeys().contains(this.targetKey)){
                return i;
            }
        }
        return 4;
    }



}
