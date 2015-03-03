package com.zzolta.android.glutenfreerecipes.persistence.database;

import android.test.AndroidTestCase;
import com.j256.ormlite.dao.Dao;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Zolta.Szekely on 2015-03-01.
 */
public class TestDb extends AndroidTestCase {

    private Dao<Recipe, String> dao;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        deleteDatabase();

        dao = RecipeDBHelper.getInstance(this.mContext).getDao();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        RecipeDBHelper.getInstance(this.mContext).getConnectionSource().close();
    }

    public void deleteDatabase() {
        mContext.deleteDatabase(RecipeDBHelper.DATABASE_NAME);
    }

    public void testDatabaseCreation() throws SQLException {
        assertTrue(dao.isTableExists());
    }

    public void testInsertRecipe() throws SQLException {
        insertRecipe();
        assertNotNull(dao.queryForId("1"));
    }

    private void insertRecipe() throws SQLException {
        final Recipe recipe = new Recipe()
                                  .setId("1")
                                  .setName("Test")
                                  .setIngredients(new ArrayList<>(Arrays.asList("onion", "pepper")))
                                  .setRating(4)
                                  .setTotalTimeInSeconds(500);
        dao.create(recipe);
    }
}
