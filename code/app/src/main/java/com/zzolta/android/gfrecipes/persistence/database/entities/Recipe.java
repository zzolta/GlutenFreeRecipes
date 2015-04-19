package com.zzolta.android.gfrecipes.persistence.database.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zzolta.android.gfrecipes.content.Contract;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (C) 2015 Zolta Szekely
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    @DatabaseField(columnName = Contract.Recipe.IMAGE_PATH)
    private String imagePath;
    @DatabaseField(columnName = Contract.Recipe.SOURCE_RECIPE_URL)
    private String sourceRecipeUrl;
    @DatabaseField(columnName = Contract.Recipe.MY_RECIPE)
    private boolean myRecipe;

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

    public String getImagePath() {
        return imagePath;
    }

    public Recipe setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public String getSourceRecipeUrl() {
        return sourceRecipeUrl;
    }

    public Recipe setSourceRecipeUrl(String sourceRecipeUrl) {
        this.sourceRecipeUrl = sourceRecipeUrl;
        return this;
    }

    public Boolean getMyRecipe() {
        return myRecipe;
    }

    public Recipe setMyRecipe(Boolean myRecipe) {
        this.myRecipe = myRecipe;
        return this;
    }
}
