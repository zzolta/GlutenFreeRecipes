
package com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail;

import com.google.gson.annotations.Expose;

public class Image {

    @Expose
    private ImageUrlsBySize imageUrlsBySize;
    @Expose
    private String hostedSmallUrl;
    @Expose
    private String hostedMediumUrl;
    @Expose
    private String hostedLargeUrl;

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

    public Image withImageUrlsBySize(ImageUrlsBySize imageUrlsBySize) {
        this.imageUrlsBySize = imageUrlsBySize;
        return this;
    }

    /**
     * @return The hostedSmallUrl
     */
    public String getHostedSmallUrl() {
        return hostedSmallUrl;
    }

    /**
     * @param hostedSmallUrl The hostedSmallUrl
     */
    public void setHostedSmallUrl(String hostedSmallUrl) {
        this.hostedSmallUrl = hostedSmallUrl;
    }

    public Image withHostedSmallUrl(String hostedSmallUrl) {
        this.hostedSmallUrl = hostedSmallUrl;
        return this;
    }

    /**
     * @return The hostedMediumUrl
     */
    public String getHostedMediumUrl() {
        return hostedMediumUrl;
    }

    /**
     * @param hostedMediumUrl The hostedMediumUrl
     */
    public void setHostedMediumUrl(String hostedMediumUrl) {
        this.hostedMediumUrl = hostedMediumUrl;
    }

    public Image withHostedMediumUrl(String hostedMediumUrl) {
        this.hostedMediumUrl = hostedMediumUrl;
        return this;
    }

    /**
     * @return The hostedLargeUrl
     */
    public String getHostedLargeUrl() {
        return hostedLargeUrl;
    }

    /**
     * @param hostedLargeUrl The hostedLargeUrl
     */
    public void setHostedLargeUrl(String hostedLargeUrl) {
        this.hostedLargeUrl = hostedLargeUrl;
    }

    public Image withHostedLargeUrl(String hostedLargeUrl) {
        this.hostedLargeUrl = hostedLargeUrl;
        return this;
    }
}
