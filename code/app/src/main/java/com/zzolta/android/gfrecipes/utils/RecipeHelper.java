package com.zzolta.android.gfrecipes.utils;

import android.support.annotation.Nullable;
import com.zzolta.android.gfrecipes.jsonparse.recipedetail.Image;
import com.zzolta.android.gfrecipes.jsonparse.recipedetail.ImageUrlsBySize;
import com.zzolta.android.gfrecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.gfrecipes.jsonparse.recipequery.Match;
import com.zzolta.android.gfrecipes.persistence.database.entities.Recipe;

import java.util.ArrayList;
import java.util.Collection;
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
public final class RecipeHelper {
    private RecipeHelper() {
    }

    public static List<Recipe> matchRecipes(Collection<Match> matches) {
        final List<Recipe> recipes = new ArrayList<>(matches.size());
        for (final Match match : matches) {
            recipes.add(new Recipe()
                            .setId(match.getId())
                            .setName(match.getRecipeName())
                            .setImagePath(match.getImageUrlsBySize().get90())
                            .setRating(match.getRating())
                            .setTotalTimeInSeconds(match.getTotalTimeInSeconds()));
        }
        return recipes;
    }

    @Nullable
    public static String getImage(RecipeDetailResult recipeDetailResult) {
        String imageUrlsBySize360 = null;
        final List<Image> images = recipeDetailResult.getImages();
        if (images != null && images.size() > 0) {
            final Image image = images.get(0);
            if (image != null) {
                final ImageUrlsBySize imageUrlsBySize = image.getImageUrlsBySize();
                if (imageUrlsBySize != null) {
                    imageUrlsBySize360 = imageUrlsBySize.get360();
                }
            }
        }
        return imageUrlsBySize360;
    }

    public static Recipe convertRecipe(RecipeDetailResult recipeDetailResult) {
        final Recipe recipe = new Recipe()
                                  .setId(recipeDetailResult.getId())
                                  .setName(recipeDetailResult.getName())
                                  .setIngredients(new ArrayList<>(recipeDetailResult.getIngredientLines()))
                                  .setRating(recipeDetailResult.getRating())
                                  .setTotalTimeInSeconds(recipeDetailResult.getTotalTimeInSeconds())
                                  .setSourceRecipeUrl(recipeDetailResult.getSource().getSourceRecipeUrl());

        final String imageUrlsBySize360 = getImage(recipeDetailResult);

        if (imageUrlsBySize360 != null) {
            recipe.setImagePath(imageUrlsBySize360);
        }

        return recipe;
    }
}
