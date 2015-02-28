
package com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

//@Generated("org.jsonschema2pojo")
public class Attributes {

    @Expose
    private List<String> cuisine = new ArrayList<String>();

    /**
     * @return The cuisine
     */
    public List<String> getCuisine() {
        return cuisine;
    }

    /**
     * @param cuisine The cuisine
     */
    public void setCuisine(List<String> cuisine) {
        this.cuisine = cuisine;
    }

    public Attributes withCuisine(List<String> cuisine) {
        this.cuisine = cuisine;
        return this;
    }
}
