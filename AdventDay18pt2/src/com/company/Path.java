package com.company;

import java.util.ArrayList;

public class Path implements Cloneable{

    private Coordinate currentCoord;
    private ArrayList<ArrayList<Coordinate>> possiblePaths;
    private ArrayList<ArrayList<Coordinate>> deadEnds = new ArrayList<>();
    private ArrayList<ArrayList<Coordinate>> tempPossiblePath= new ArrayList<>();
    private ArrayList<Coordinate> pathFromStart = new ArrayList<>();
    private ArrayList<Coordinate> availableKeys = new ArrayList<>();
    private ArrayList<Coordinate> ownedKeys = new ArrayList<>();
    private ArrayList<Coordinate> openArea = new ArrayList<>();
    private Coordinate targetKey;
    private int pathLength = 0;

    public Path() {
    }

    public int getPathLength() {
        return pathLength;
    }

    public void setPathLength(int pathLength) {
        this.pathLength = pathLength;
    }

    public Coordinate getTargetKey() {
        return targetKey;
    }

    public Coordinate getCurrentCoord() {
        return currentCoord;
    }

    public void setTempPossiblePath(ArrayList<ArrayList<Coordinate>> tempPossiblePath) {
        this.tempPossiblePath = tempPossiblePath;
    }

    public void setPathFromStart(ArrayList<Coordinate> pathFromStart) {
        this.pathFromStart = pathFromStart;
    }

    public void setAvailableKeys(ArrayList<Coordinate> availableKeys) {
        this.availableKeys = availableKeys;
    }

    public ArrayList<Coordinate> getAvailableKeys() {
        return availableKeys;
    }

    public void setOwnedKeys(ArrayList<Coordinate> ownedKeys) {
        this.ownedKeys = ownedKeys;
    }

    public void clonedOpenArea(ArrayList<Coordinate> openArea) {
        this.openArea = openArea;
    }

    public void setCurrentCoord(Coordinate current){
        this.pathFromStart.add(current);
        this.currentCoord = current;
    }

    public ArrayList<Coordinate> getOwnedKeys(){
        return this.ownedKeys;
    }

    public ArrayList<Coordinate> getOpenArea() {
        setOpenArea(this.currentCoord);
        return openArea;
    }

    public ArrayList<Coordinate> getPathFromStart() {
        return pathFromStart;
    }

    public void setTargetKey(Coordinate targetKey) {
        this.targetKey = targetKey;
    }


    public Path cloneAttributesAndAdd() throws CloneNotSupportedException {
        Path cloned = (Path) this.clone();
        cloned.clonedOpenArea((ArrayList<Coordinate>) this.openArea.clone());
        cloned.setAvailableKeys((ArrayList<Coordinate>) this.availableKeys.clone());
        cloned.setPathFromStart((ArrayList<Coordinate>) this.pathFromStart.clone());
        cloned.setOwnedKeys((ArrayList<Coordinate>) this.ownedKeys.clone());
        cloned.setTempPossiblePath((ArrayList<ArrayList<Coordinate>>) this.tempPossiblePath.clone());
         return cloned;
    }


    public void setOpenArea(Coordinate current){
        for (Coordinate neighbor: current.shareNeighbors()) {
            if (neighbor != null) {
                if (neighbor.getIdentifier() != '#') {
                    addToOpenArea(neighbor);
                }
            }
        }
        assessKeys();
    }

    private void assessKeys() {
        for (Coordinate local: this.openArea) {
            if (Character.isLowerCase(local.getIdentifier()) && !this.availableKeys.contains(local)) {
                this.availableKeys.add(local);
            }
        }
        this.availableKeys.removeAll(this.ownedKeys);
    }

    private boolean checkForKey(Coordinate door) {
        for (Coordinate key: this.ownedKeys) {
            if (Character.toUpperCase(key.getIdentifier()) == door.getIdentifier()) {
                return true;
            }
        }
        return false;
    }


    private void addToOpenArea(Coordinate neighbor){
        boolean add = true;
        for (Coordinate path : this.openArea) {
            if (neighbor == path) {
                add = false;
                break;
            }
        }
        if (add) {
            if (Character.isUpperCase(neighbor.getIdentifier())) {
                if (checkForKey(neighbor)) {
                    this.openArea.add(neighbor);
                    setOpenArea(neighbor);
                }
            } else {
                this.openArea.add(neighbor);
                setOpenArea(neighbor);
            }
        }
    }

    private ArrayList<ArrayList<Coordinate>> firstStep(){
        ArrayList<ArrayList<Coordinate>> possiblePaths = new ArrayList<>();
        for (Coordinate neighbor: this.currentCoord.shareNeighbors()){
            if (neighbor.getIdentifier() != '#') {
                ArrayList<Coordinate> newPath = new ArrayList<>();
                newPath.add(this.currentCoord);
                newPath.add(neighbor);
                possiblePaths.add(newPath);
            }
        }
        return possiblePaths;
    }

    private void nextStep(ArrayList<Coordinate> currentPath){
        Coordinate lastCoord = currentPath.get(currentPath.size()-1);
        if (Character.isUpperCase(lastCoord.getIdentifier())) {
            if (!haveKey(lastCoord)) {
                return;
            }
        }
        boolean first = true;
        Coordinate holding = null;
        for (Coordinate neighbor: lastCoord.shareNeighbors()) {
            if (neighbor.getIdentifier() != '#' && !currentPath.contains(neighbor)){
                if (first) {
                    holding = neighbor;
                    first = false;
                } else {
                    ArrayList<Coordinate> temp = (ArrayList<Coordinate>) currentPath.clone();
                    temp.add(neighbor);
                    this.tempPossiblePath.add(temp);
                }
            }
        }
        if (holding !=null)
            currentPath.add(holding);
    }

    private boolean haveKey(Coordinate door){
        for (Coordinate key: this.ownedKeys){
            if (Character.toUpperCase(key.getIdentifier()) == door.getIdentifier())
                return true;
        }
        return false;
    }

    private void foundKey(ArrayList<Coordinate> fastestPath){
        this.pathFromStart.remove(this.pathFromStart.size()-1);
        this.tempPossiblePath.clear();
        this.pathFromStart.addAll(fastestPath);
        this.pathLength = this.pathFromStart.size()-1;
        this.currentCoord = this.targetKey;
        this.ownedKeys.add(this.targetKey);
        for (Coordinate keyCheck :fastestPath){
            if (Character.isLowerCase(keyCheck.getIdentifier())){
                if (!this.ownedKeys.contains(keyCheck))
                    this.ownedKeys.add(keyCheck);
            }
        }
    }


    public void pathToNextKey(Coordinate targetKey) {
        this.targetKey = targetKey;
        this.possiblePaths = firstStep();
        while (true) {
            for (ArrayList<Coordinate> currentPath : this.possiblePaths) {
                if (currentPath.get(currentPath.size() - 1).getIdentifier() == this.targetKey.getIdentifier()) {
                    foundKey(currentPath);
                    this.possiblePaths.clear();
                    return;
                }
                int size = currentPath.size();
                nextStep(currentPath);
                if (currentPath.size() == size)
                    this.deadEnds.add(currentPath);
            }
            this.possiblePaths.removeAll(this.deadEnds);
            this.possiblePaths.addAll(this.tempPossiblePath);
            this.deadEnds.clear();
            this.tempPossiblePath.clear();
        }
    }

}
