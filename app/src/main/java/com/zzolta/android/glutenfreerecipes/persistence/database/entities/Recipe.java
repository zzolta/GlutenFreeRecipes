package com.zzolta.android.glutenfreerecipes.persistence.database.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zzolta.android.glutenfreerecipes.content.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zolta.Szekely on 2015-03-01.
 */
@DatabaseTable(tableName = Contract.Recipe.TABLE_NAME)
public class Recipe {
    @DatabaseField(id = true, columnName = Contract.Recipe._ID)
    private String id;
    @DatabaseField(columnName = Contract.Recipe.NAME)
    private String name;
    @DatabaseField(dataType = DataType.SERIALIZABLE, columnName = Contract.Recipe.INGREDIENTS)
    private ArrayList<String> ingredients;
    @DatabaseField(columnName = Contract.Recipe.TOTAL_TIME_IN_SECONDS)
    private Integer totalTimeInSeconds;
    @DatabaseField(columnName = Contract.Recipe.RATING)
    private Integer rating;

    public String getId() {
        return id;
    }

    public Recipe setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Recipe setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public Recipe setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public Integer getTotalTimeInSeconds() {
        return totalTimeInSeconds;
    }

    public Recipe setTotalTimeInSeconds(Integer totalTimeInSeconds) {
        this.totalTimeInSeconds = totalTimeInSeconds;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public Recipe setRating(Integer rating) {
        this.rating = rating;
        return this;
    }
}
