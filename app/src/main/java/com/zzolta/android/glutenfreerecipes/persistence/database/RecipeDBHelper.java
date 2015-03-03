package com.zzolta.android.glutenfreerecipes.persistence.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;

import java.sql.SQLException;

/**
 * Created by Zolta.Szekely on 2015-03-01.
 */
public class RecipeDBHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "recipe.db";
    public static final int DATABASE_VERSION = 1;

    private static final String LOG_TAG = RecipeDBHelper.class.getName();

    private static RecipeDBHelper instance;

    private Dao<Recipe, String> recipeDao;

    private RecipeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized RecipeDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RecipeDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Recipe.class);
        }
        catch (final SQLException e) {
            Log.e(LOG_TAG, "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Recipe.class, true);

            TableUtils.createTable(connectionSource, Recipe.class);
        }
        catch (final SQLException e) {
            Log.e(LOG_TAG, "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        super.close();
        recipeDao = null;
    }

    public Dao<Recipe, String> getDao() throws SQLException {
        if (recipeDao == null) {
            recipeDao = getDao(Recipe.class);
        }
        return recipeDao;
    }
}
