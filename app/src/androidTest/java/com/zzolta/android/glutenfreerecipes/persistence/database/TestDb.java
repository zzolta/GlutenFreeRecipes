package com.zzolta.android.glutenfreerecipes.persistence.database;

import android.test.AndroidTestCase;
import com.j256.ormlite.dao.Dao;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
