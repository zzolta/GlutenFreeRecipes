package com.zzolta.android.glutenfreerecipes.persistence.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zzolta.android.glutenfreerecipes.content.Contract;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;

import java.sql.SQLException;

/**
 * Created by Zolta.Szekely on 2015-03-01.
 */
public class RecipeDBHelper extends OrmLiteSqliteOpenHelper {

    private static final String LOG_TAG = RecipeDBHelper.class.getName();

    private Dao<Recipe, String> recipeDao;

    public RecipeDBHelper(Context context) {
        super(context, Contract.DATABASE_NAME, null, Contract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            Log.i(LOG_TAG, "onCreate");
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
