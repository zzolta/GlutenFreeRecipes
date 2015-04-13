
package com.zzolta.android.gfrecipes.jsonparse.recipedetail;

import com.google.gson.annotations.Expose;

public class Unit {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String abbreviation;
    @Expose
    private String plural;
    @Expose
    private String pluralAbbreviation;
    @Expose
    private Boolean decimal;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public Unit withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Unit withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * @param abbreviation The abbreviation
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Unit withAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        return this;
    }

    /**
     * @return The plural
     */
    public String getPlural() {
        return plural;
    }

    /**
     * @param plural The plural
     */
    public void setPlural(String plural) {
        this.plural = plural;
    }

    public Unit withPlural(String plural) {
        this.plural = plural;
        return this;
    }

    /**
     * @return The pluralAbbreviation
     */
    public String getPluralAbbreviation() {
        return pluralAbbreviation;
    }

    /**
     * @param pluralAbbreviation The pluralAbbreviation
     */
    public void setPluralAbbreviation(String pluralAbbreviation) {
        this.pluralAbbreviation = pluralAbbreviation;
    }

    public Unit withPluralAbbreviation(String pluralAbbreviation) {
        this.pluralAbbreviation = pluralAbbreviation;
        return this;
    }

    /**
     * @return The decimal
     */
    public Boolean getDecimal() {
        return decimal;
    }

    /**
     * @param decimal The decimal
     */
    public void setDecimal(Boolean decimal) {
        this.decimal = decimal;
    }

    public Unit withDecimal(Boolean decimal) {
        this.decimal = decimal;
        return this;
    }
}
