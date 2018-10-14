package com.sakura.footballscore.service

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.sakura.footballscore.model.Favorite
import org.jetbrains.anko.db.*

class DatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db", null, 1) {
    companion object {
        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as DatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) =// Here you create tables
            db.createTable(Favorite.TABLE_NAME, true,
                    Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                    Favorite.MATCH_ID to TEXT + UNIQUE,
                    Favorite.STR_DATE to TEXT ,
                    Favorite.STR_HOME_TEAM to TEXT  ,
                    Favorite.STR_AWAY_TEAM to TEXT  ,
                    Favorite.STR_HOME_SCORE to TEXT ,
                    Favorite.STR_AWAY_SCORE to TEXT
            )

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(Favorite.TABLE_NAME, true)
    }
}



// Access property for Context
val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(applicationContext)