
package com.zzolta.android.gfrecipes.jsonparse.recipedetail;

import com.google.gson.annotations.Expose;

public class NutritionEstimate {

    @Expose
    private String attribute;
    @Expose
    private String description;
    @Expose
    private Double value;
    @Expose
    private Unit unit;

    /**
     * @return The attribute
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * @param attribute The attribute
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public NutritionEstimate withAttribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public NutritionEstimate withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @return The value
     */
    public Double getValue() {
        return value;
    }

    /**
     * @param value The value
     */
    public void setValue(Double value) {
        this.value = value;
    }

    public NutritionEstimate withValue(Double value) {
        this.value = value;
        return this;
    }

    /**
     * @return The unit
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * @param unit The unit
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public NutritionEstimate withUnit(Unit unit) {
        this.unit = unit;
        return this;
    }
}
