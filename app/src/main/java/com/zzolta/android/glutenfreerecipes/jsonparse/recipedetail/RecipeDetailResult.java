
package com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

//@Generated("org.jsonschema2pojo")
public class RecipeDetailResult {

    @Expose
    private Object yield;
    @Expose
    private List<NutritionEstimate> nutritionEstimates = new ArrayList<NutritionEstimate>();
    @Expose
    private String totalTime;
    @Expose
    private List<Image> images = new ArrayList<Image>();
    @Expose
    private String name;
    @Expose
    private Source source;
    @Expose
    private String id;
    @Expose
    private List<String> ingredientLines = new ArrayList<String>();
    @Expose
    private Attribution attribution;
    @Expose
    private Integer numberOfServings;
    @Expose
    private Integer totalTimeInSeconds;
    @Expose
    private Attributes attributes;
    @Expose
    private Flavors flavors;
    @Expose
    private Integer rating;

    /**
     * @return The yield
     */
    public Object getYield() {
        return yield;
    }

    /**
     * @param yield The yield
     */
    public void setYield(Object yield) {
        this.yield = yield;
    }

    public RecipeDetailResult withYield(Object yield) {
        this.yield = yield;
        return this;
    }

    /**
     * @return The nutritionEstimates
     */
    public List<NutritionEstimate> getNutritionEstimates() {
        return nutritionEstimates;
    }

    /**
     * @param nutritionEstimates The nutritionEstimates
     */
    public void setNutritionEstimates(List<NutritionEstimate> nutritionEstimates) {
        this.nutritionEstimates = nutritionEstimates;
    }

    public RecipeDetailResult withNutritionEstimates(List<NutritionEstimate> nutritionEstimates) {
        this.nutritionEstimates = nutritionEstimates;
        return this;
    }

    /**
     * @return The totalTime
     */
    public String getTotalTime() {
        return totalTime;
    }

    /**
     * @param totalTime The totalTime
     */
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public RecipeDetailResult withTotalTime(String totalTime) {
        this.totalTime = totalTime;
        return this;
    }

    /**
     * @return The images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * @param images The images
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    public RecipeDetailResult withImages(List<Image> images) {
        this.images = images;
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

    public RecipeDetailResult withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The source
     */
    public Source getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(Source source) {
        this.source = source;
    }

    public RecipeDetailResult withSource(Source source) {
        this.source = source;
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

    public RecipeDetailResult withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The ingredientLines
     */
    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    /**
     * @param ingredientLines The ingredientLines
     */
    public void setIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public RecipeDetailResult withIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
        return this;
    }

    /**
     * @return The attribution
     */
    public Attribution getAttribution() {
        return attribution;
    }

    /**
     * @param attribution The attribution
     */
    public void setAttribution(Attribution attribution) {
        this.attribution = attribution;
    }

    public RecipeDetailResult withAttribution(Attribution attribution) {
        this.attribution = attribution;
        return this;
    }

    /**
     * @return The numberOfServings
     */
    public Integer getNumberOfServings() {
        return numberOfServings;
    }

    /**
     * @param numberOfServings The numberOfServings
     */
    public void setNumberOfServings(Integer numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public RecipeDetailResult withNumberOfServings(Integer numberOfServings) {
        this.numberOfServings = numberOfServings;
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

    public RecipeDetailResult withTotalTimeInSeconds(Integer totalTimeInSeconds) {
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

    public RecipeDetailResult withAttributes(Attributes attributes) {
        this.attributes = attributes;
        return this;
    }

    /**
     * @return The flavors
     */
    public Flavors getFlavors() {
        return flavors;
    }

    /**
     * @param flavors The flavors
     */
    public void setFlavors(Flavors flavors) {
        this.flavors = flavors;
    }

    public RecipeDetailResult withFlavors(Flavors flavors) {
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

    public RecipeDetailResult withRating(Integer rating) {
        this.rating = rating;
        return this;
    }
}
