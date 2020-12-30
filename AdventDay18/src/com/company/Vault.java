package com.company;

import java.util.ArrayList;

public class Vault {
    private ArrayList<Coordinate> keyList;
    private Coordinate start;
    private ArrayList<Path> redundantPaths = new ArrayList<>();
    private ArrayList<Path> tempPathList = new ArrayList<>();
    private ArrayList<Path> pathList = new ArrayList<>();
    private ArrayList<Coordinate> ownedKeys = new ArrayList<>()

    public Vault(Coordinate start) {
        this.start = start;
    }

    public void addPath(Path newPath) {
        this.tempPathList.add(newPath);
    }

    public void initialPath() throws CloneNotSupportedException {
        Path newPath = new Path(this);
        newPath.setCurrentCoord(this.start);
        this.pathList.add(newPath);
        newPath.branchNextKeySet();
        this.pathList.addAll(this.tempPathList);
        this.tempPathList.clear();
    }

    public Path findShortestPath() throws CloneNotSupportedException {
        boolean end = false;
        Path shortest = new Path(this);
        shortest.setPathLength(999999999);
        int shortestPathCount = Integer.MAX_VALUE;
        int count = 0;
        while (!end){
            if (this.pathList.size() == 0){
                break;
            }
            shortest = findNextKeySet(shortest,count);
            evaluateRedundantPaths();
            for (Path branch: this.pathList) {
                    branch.branchNextKeySet();
            }
            this.pathList.addAll(this.tempPathList);
            this.tempPathList.clear();
            count++;
        }
        return shortest;
    }


    private Path findNextKeySet(Path shortest, int count) {
        for (Path pathEvaluation: this.pathList) {
            if (pathEvaluation.getOwnedKeys().size() == count) {
                pathEvaluation.pathToNextKey();
                if (pathEvaluation.getOwnedKeys().containsAll(this.keyList)) {
                    if (shortest.getPathLength() > pathEvaluation.getPathLength()) {
                        shortest = pathEvaluation;
                    }
                    this.redundantPaths.add(pathEvaluation);
                }
            }
        }
        return shortest;
    }

    private void evaluateRedundantPaths(){
        System.out.println(this.pathList.size());
        for (Path compare: this.pathList){
            if (!this.redundantPaths.contains(compare)) {
                for (Path toThis : this.pathList) {
                    if (!this.redundantPaths.contains(toThis)) {
                        if (compare != toThis) {
                            if (compare.getCurrentCoord() == toThis.getCurrentCoord()) {
                                if (compare.getOwnedKeys().containsAll(toThis.getOwnedKeys())) {
                                    if (toThis.getOwnedKeys().containsAll(compare.getOwnedKeys())) {
                                        if (toThis.getPathLength() <= compare.getPathLength()) {
                                            this.redundantPaths.add(compare);
                                            break;
                                        } else if (toThis.getPathLength() > compare.getPathLength()) {
                                            this.redundantPaths.add(toThis);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        this.pathList.removeAll(this.redundantPaths);
        this.redundantPaths.clear();
    }

}
