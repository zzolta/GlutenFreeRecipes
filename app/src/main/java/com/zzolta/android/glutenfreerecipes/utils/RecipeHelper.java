package com.zzolta.android.glutenfreerecipes.utils;

import android.support.annotation.Nullable;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.Image;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.ImageUrlsBySize;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipequery.Match;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Zolta.Szekely on 2015-03-28.
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
