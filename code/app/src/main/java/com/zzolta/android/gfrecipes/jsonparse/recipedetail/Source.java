
package com.zzolta.android.gfrecipes.jsonparse.recipedetail;

import com.google.gson.annotations.Expose;

public class Source {

    @Expose
    private String sourceRecipeUrl;
    @Expose
    private String sourceSiteUrl;
    @Expose
    private String sourceDisplayName;

    /**
     * @return The sourceRecipeUrl
     */
    public String getSourceRecipeUrl() {
        return sourceRecipeUrl;
    }

    /**
     * @param sourceRecipeUrl The sourceRecipeUrl
     */
    public void setSourceRecipeUrl(String sourceRecipeUrl) {
        this.sourceRecipeUrl = sourceRecipeUrl;
    }

    public Source withSourceRecipeUrl(String sourceRecipeUrl) {
        this.sourceRecipeUrl = sourceRecipeUrl;
        return this;
    }

    /**
     * @return The sourceSiteUrl
     */
    public String getSourceSiteUrl() {
        return sourceSiteUrl;
    }

    /**
     * @param sourceSiteUrl The sourceSiteUrl
     */
    public void setSourceSiteUrl(String sourceSiteUrl) {
        this.sourceSiteUrl = sourceSiteUrl;
    }

    public Source withSourceSiteUrl(String sourceSiteUrl) {
        this.sourceSiteUrl = sourceSiteUrl;
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

    public Source withSourceDisplayName(String sourceDisplayName) {
        this.sourceDisplayName = sourceDisplayName;
        return this;
    }
}
