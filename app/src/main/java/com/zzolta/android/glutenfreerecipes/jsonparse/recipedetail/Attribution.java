
package com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail;

import com.google.gson.annotations.Expose;

//@Generated("org.jsonschema2pojo")
public class Attribution {

    @Expose
    private String html;
    @Expose
    private String url;
    @Expose
    private String text;
    @Expose
    private String logo;

    /**
     * @return The html
     */
    public String getHtml() {
        return html;
    }

    /**
     * @param html The html
     */
    public void setHtml(String html) {
        this.html = html;
    }

    public Attribution withHtml(String html) {
        this.html = html;
        return this;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public Attribution withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    public Attribution withText(String text) {
        this.text = text;
        return this;
    }

    /**
     * @return The logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo The logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Attribution withLogo(String logo) {
        this.logo = logo;
        return this;
    }
}
