
package com.zzolta.android.glutenfreerecipes.jsonparse.recipequery;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

//@Generated("org.jsonschema2pojo")
public class RecipeQueryResult {

    @Expose
    private Attribution attribution;
    @Expose
    private Integer totalMatchCount;
    @Expose
    private FacetCounts facetCounts;
    @Expose
    private List<Match> matches = new ArrayList<Match>();
    @Expose
    private Criteria criteria;

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

    public RecipeQueryResult withAttribution(Attribution attribution) {
        this.attribution = attribution;
        return this;
    }

    /**
     * @return The totalMatchCount
     */
    public Integer getTotalMatchCount() {
        return totalMatchCount;
    }

    /**
     * @param totalMatchCount The totalMatchCount
     */
    public void setTotalMatchCount(Integer totalMatchCount) {
        this.totalMatchCount = totalMatchCount;
    }

    public RecipeQueryResult withTotalMatchCount(Integer totalMatchCount) {
        this.totalMatchCount = totalMatchCount;
        return this;
    }

    /**
     * @return The facetCounts
     */
    public FacetCounts getFacetCounts() {
        return facetCounts;
    }

    /**
     * @param facetCounts The facetCounts
     */
    public void setFacetCounts(FacetCounts facetCounts) {
        this.facetCounts = facetCounts;
    }

    public RecipeQueryResult withFacetCounts(FacetCounts facetCounts) {
        this.facetCounts = facetCounts;
        return this;
    }

    /**
     * @return The matches
     */
    public List<Match> getMatches() {
        return matches;
    }

    /**
     * @param matches The matches
     */
    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public RecipeQueryResult withMatches(List<Match> matches) {
        this.matches = matches;
        return this;
    }

    /**
     * @return The criteria
     */
    public Criteria getCriteria() {
        return criteria;
    }

    /**
     * @param criteria The criteria
     */
    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public RecipeQueryResult withCriteria(Criteria criteria) {
        this.criteria = criteria;
        return this;
    }
}
