
package com.zzolta.android.glutenfreerecipes.jsonparse.recipequery;

import com.google.gson.annotations.Expose;

//@Generated("org.jsonschema2pojo")
public class Criteria {

    @Expose
    private Object excludedIngredients;
    @Expose
    private Object allowedIngredients;
    @Expose
    private Object terms;
    @Expose
    private Boolean requirePictures;

    /**
     * @return The excludedIngredients
     */
    public Object getExcludedIngredients() {
        return excludedIngredients;
    }

    /**
     * @param excludedIngredients The excludedIngredients
     */
    public void setExcludedIngredients(Object excludedIngredients) {
        this.excludedIngredients = excludedIngredients;
    }

    public Criteria withExcludedIngredients(Object excludedIngredients) {
        this.excludedIngredients = excludedIngredients;
        return this;
    }

    /**
     * @return The allowedIngredients
     */
    public Object getAllowedIngredients() {
        return allowedIngredients;
    }

    /**
     * @param allowedIngredients The allowedIngredients
     */
    public void setAllowedIngredients(Object allowedIngredients) {
        this.allowedIngredients = allowedIngredients;
    }

    public Criteria withAllowedIngredients(Object allowedIngredients) {
        this.allowedIngredients = allowedIngredients;
        return this;
    }

    /**
     * @return The terms
     */
    public Object getTerms() {
        return terms;
    }

    /**
     * @param terms The terms
     */
    public void setTerms(Object terms) {
        this.terms = terms;
    }

    public Criteria withTerms(Object terms) {
        this.terms = terms;
        return this;
    }

    /**
     * @return The requirePictures
     */
    public Boolean getRequirePictures() {
        return requirePictures;
    }

    /**
     * @param requirePictures The requirePictures
     */
    public void setRequirePictures(Boolean requirePictures) {
        this.requirePictures = requirePictures;
    }

    public Criteria withRequirePictures(Boolean requirePictures) {
        this.requirePictures = requirePictures;
        return this;
    }
}
