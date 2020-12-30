package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Reaction {

    Map<String, Long> reactants = new HashMap<>();
    private long productStoich;
    private String productName;
    private long remainder = 0;


    public Reaction(String totalReaction) {
        reactionBreakdown(totalReaction);
    }

    public void setRemainder(long remainder) {
        this.remainder = remainder;
    }

    public String getProductName() {
        return productName;
    }

    //Start with Fuel, and Find the components of Fuel
        //call Reaction to produce fuel
        //call Reaction to produce components to produce Fuel using stochiometre necessary to produce components
        //leave each Reaction object with "remainders"
    //determine how many "Ore" you would need to complete enough reactions up the chain to produce fuel


//        if (this.reactants.keySet().equals("ORE")){
//        return this.reactants.get("ORE")*stochiometricRequirement;
//    }
//    int stochiometry = stochiometricRequirement*this.productStoich;
//        return produceProduct()


    public long produceProduct(long stochiometricRequirement, Map<String, Reaction> reactionList) {
        long ore = 0;
        if (stochiometricRequirement <= this.remainder) {
            this.remainder -= stochiometricRequirement;
            return 0;
        }
        long requirement = stochiometricRequirement-this.remainder;
        this.remainder =0;
        long modulo = requirement%this.productStoich;
        if (modulo != 0) {
            this.remainder += this.productStoich- modulo;
        }

        for (String each: this.reactants.keySet()) {
            long reactionsNecessary = (long) Math.ceil((double) requirement/this.productStoich);
            long componentRequirements = reactionsNecessary*this.reactants.get(each);

            if (each.equals("ORE")){
                return componentRequirements;
            }

            ore += reactionList.get(each).produceProduct(componentRequirements,reactionList);
        }
        return ore;
    }


    private void reactionBreakdown(String reaction) {
        String[] components = reaction.split("[,=>]+");
        String[] product = components[components.length-1].trim().split(" ");
        assignProduct(Arrays.copyOf(components,components.length-1));
        this.productStoich = Long.parseLong(product[0]);
        this.productName = product[1];
    }

    private void assignProduct(String[] reactants) {
        for (String each: reactants) {
            String[] splitted = each.trim().split(" ");
            this.reactants.put(splitted[1],Long.parseLong(splitted[0]));
        }

    }


}
