
package com.zzolta.android.glutenfreerecipes.jsonparse.recipequery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("org.jsonschema2pojo")
public class ImageUrlsBySize {

    @SerializedName("90")
    @Expose
    private String _90;

    /**
     * @return The _90
     */
    public String get90() {
        return _90;
    }

    /**
     * @param _90 The 90
     */
    public void set90(String _90) {
        this._90 = _90;
    }

    public ImageUrlsBySize with90(String _90) {
        this._90 = _90;
        return this;
    }
}
