
package com.zzolta.android.gfrecipes.jsonparse.recipedetail;

import com.google.gson.annotations.Expose;

public class Flavors {

    @Expose
    private Double Sweet;
    @Expose
    private Double Salty;
    @Expose
    private Double Meaty;
    @Expose
    private Double Sour;
    @Expose
    private Double Bitter;
    @Expose
    private Double Piquant;

    /**
     * @return The Sweet
     */
    public Double getSweet() {
        return Sweet;
    }

    /**
     * @param Sweet The Sweet
     */
    public void setSweet(Double Sweet) {
        this.Sweet = Sweet;
    }

    public Flavors withSweet(Double Sweet) {
        this.Sweet = Sweet;
        return this;
    }

    /**
     * @return The Salty
     */
    public Double getSalty() {
        return Salty;
    }

    /**
     * @param Salty The Salty
     */
    public void setSalty(Double Salty) {
        this.Salty = Salty;
    }

    public Flavors withSalty(Double Salty) {
        this.Salty = Salty;
        return this;
    }

    /**
     * @return The Meaty
     */
    public Double getMeaty() {
        return Meaty;
    }

    /**
     * @param Meaty The Meaty
     */
    public void setMeaty(Double Meaty) {
        this.Meaty = Meaty;
    }

    public Flavors withMeaty(Double Meaty) {
        this.Meaty = Meaty;
        return this;
    }

    /**
     * @return The Sour
     */
    public Double getSour() {
        return Sour;
    }

    /**
     * @param Sour The Sour
     */
    public void setSour(Double Sour) {
        this.Sour = Sour;
    }

    public Flavors withSour(Double Sour) {
        this.Sour = Sour;
        return this;
    }

    /**
     * @return The Bitter
     */
    public Double getBitter() {
        return Bitter;
    }

    /**
     * @param Bitter The Bitter
     */
    public void setBitter(Double Bitter) {
        this.Bitter = Bitter;
    }

    public Flavors withBitter(Double Bitter) {
        this.Bitter = Bitter;
        return this;
    }

    /**
     * @return The Piquant
     */
    public Double getPiquant() {
        return Piquant;
    }

    /**
     * @param Piquant The Piquant
     */
    public void setPiquant(Double Piquant) {
        this.Piquant = Piquant;
    }

    public Flavors withPiquant(Double Piquant) {
        this.Piquant = Piquant;
        return this;
    }
}
