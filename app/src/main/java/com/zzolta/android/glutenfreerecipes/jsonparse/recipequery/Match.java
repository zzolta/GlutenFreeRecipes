
package com.zzolta.android.glutenfreerecipes.jsonparse.recipequery;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Match {

    @Expose
    private ImageUrlsBySize imageUrlsBySize;
    @Expose
    private String sourceDisplayName;
    @Expose
    private List<String> ingredients = new ArrayList<String>();
    @Expose
    private String id;
    @Expose
    private List<String> smallImageUrls = new ArrayList<String>();
    @Expose
    private String recipeName;
    @Expose
    private Integer totalTimeInSeconds;
    @Expose
    private Attributes attributes;
    @Expose
    private Object flavors;
    @Expose
    private Integer rating;

    /**
     * @return The imageUrlsBySize
     */
    public ImageUrlsBySize getImageUrlsBySize() {
        return imageUrlsBySize;
    }

    /**
     * @param imageUrlsBySize The imageUrlsBySize
     */
    public void setImageUrlsBySize(ImageUrlsBySize imageUrlsBySize) {
        this.imageUrlsBySize = imageUrlsBySize;
    }

    public Match withImageUrlsBySize(ImageUrlsBySize imageUrlsBySize) {
        this.imageUrlsBySize = imageUrlsBySize;
        return this;
    }

    /**
     * @return The sourceDisplayName
     */
    public String getSourceDisplayName() {
        return sourceDisplayName;
    }

    /**
     * @param sourceDisplayName The sourceDisplayName
     */
    public void setSourceDisplayName(String sourceDisplayName) {
        this.sourceDisplayName = sourceDisplayName;
    }

    public Match withSourceDisplayName(String sourceDisplayName) {
        this.sourceDisplayName = sourceDisplayName;
        return this;
    }

    /**
     * @return The ingredients
     */
    public List<String> getIngredients() {
        return ingredients;
    }

    /**
     * @param ingredients The ingredients
     */
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Match withIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

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

    public Match withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The smallImageUrls
     */
    public List<String> getSmallImageUrls() {
        return smallImageUrls;
    }

    /**
     * @param smallImageUrls The smallImageUrls
     */
    public void setSmallImageUrls(List<String> smallImageUrls) {
        this.smallImageUrls = smallImageUrls;
    }

    public Match withSmallImageUrls(List<String> smallImageUrls) {
        this.smallImageUrls = smallImageUrls;
        return this;
    }

    /**
     * @return The recipeName
     */
    public String getRecipeName() {
        return recipeName;
    }

    /**
     * @param recipeName The recipeName
     */
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public Match withRecipeName(String recipeName) {
        this.recipeName = recipeName;
        return this;
    }

    /**
     * @return The totalTimeInSeconds
     */
    public Integer getTotalTimeInSeconds() {
        return totalTimeInSeconds;
    }

    /**
     * @param totalTimeInSeconds The totalTimeInSeconds
     */
    public void setTotalTimeInSeconds(Integer totalTimeInSeconds) {
        this.totalTimeInSeconds = totalTimeInSeconds;
    }

    public Match withTotalTimeInSeconds(Integer totalTimeInSeconds) {
        this.totalTimeInSeconds = totalTimeInSeconds;
        return this;
    }

    /**
     * @return The attributes
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * @param attributes The attributes
     */
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Match withAttributes(Attributes attributes) {
        this.attributes = attributes;
        return this;
    }

    /**
     * @return The flavors
     */
    public Object getFlavors() {
        return flavors;
    }

    /**
     * @param flavors The flavors
     */
    public void setFlavors(Object flavors) {
        this.flavors = flavors;
    }

    public Match withFlavors(Object flavors) {
        this.flavors = flavors;
        return this;
    }

    /**
     * @return The rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * @param rating The rating
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Match withRating(Integer rating) {
        this.rating = rating;
        return this;
    }
}
