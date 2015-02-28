
package com.zzolta.android.glutenfreerecipes.jsonparse.recipequery;

import com.google.gson.annotations.Expose;

//@Generated("org.jsonschema2pojo")
public class Flavors {

    @Expose
    private Integer piquant;
    @Expose
    private Double meaty;
    @Expose
    private Double sour;
    @Expose
    private Double bitter;
    @Expose
    private Double salty;
    @Expose
    private Double sweet;

    /**
     * @return The piquant
     */
    public Integer getPiquant() {
        return piquant;
    }

    /**
     * @param piquant The piquant
     */
    public void setPiquant(Integer piquant) {
        this.piquant = piquant;
    }

    public Flavors withPiquant(Integer piquant) {
        this.piquant = piquant;
        return this;
    }

    /**
     * @return The meaty
     */
    public Double getMeaty() {
        return meaty;
    }

    /**
     * @param meaty The meaty
     */
    public void setMeaty(Double meaty) {
        this.meaty = meaty;
    }

    public Flavors withMeaty(Double meaty) {
        this.meaty = meaty;
        return this;
    }

    /**
     * @return The sour
     */
    public Double getSour() {
        return sour;
    }

    /**
     * @param sour The sour
     */
    public void setSour(Double sour) {
        this.sour = sour;
    }

    public Flavors withSour(Double sour) {
        this.sour = sour;
        return this;
    }

    /**
     * @return The bitter
     */
    public Double getBitter() {
        return bitter;
    }

    /**
     * @param bitter The bitter
     */
    public void setBitter(Double bitter) {
        this.bitter = bitter;
    }

    public Flavors withBitter(Double bitter) {
        this.bitter = bitter;
        return this;
    }

    /**
     * @return The salty
     */
    public Double getSalty() {
        return salty;
    }

    /**
     * @param salty The salty
     */
    public void setSalty(Double salty) {
        this.salty = salty;
    }

    public Flavors withSalty(Double salty) {
        this.salty = salty;
        return this;
    }

    /**
     * @return The sweet
     */
    public Double getSweet() {
        return sweet;
    }

    /**
     * @param sweet The sweet
     */
    public void setSweet(Double sweet) {
        this.sweet = sweet;
    }

    public Flavors withSweet(Double sweet) {
        this.sweet = sweet;
        return this;
    }
}
