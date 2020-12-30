package com.company;

import java.util.ArrayList;

public class AutoRobit {


    private ArrayList<Coordinate> ownedKeys = new ArrayList<>();
    private ArrayList<Vault> vaults = new ArrayList<>();
    private CaveSystems myCave;
    private int totalDistance;

    public AutoRobit(ArrayList<Vault> vaults, CaveSystems myCave) {
        this.myCave = myCave;
        this.vaults = vaults;
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

    public ArrayList<Vault> getVaults() {
        return vaults;
    }

    public void setVaults(ArrayList<Vault> vaults) {
        this.vaults = vaults;
    }

    public void setInitialPaths() throws CloneNotSupportedException {
        for (Vault valut: vaults){
            valut.initialPath();
        }
    }

    private void assessKey(Coordinate key){
        boolean add = true;
        for (Coordinate owned:this.ownedKeys){
            if (key == owned){
                add = false;
            }
        }
        if (add)
            this.ownedKeys.add(key);
    }

    public void makeNextMove(){
        for (Vault vault: vaults){
            vault.setOwnedKeys(this.ownedKeys);
            //move
            for (Coordinate key: vault.getOwnedKeys()){
                assessKey(key);
            }
        }
    }


}
