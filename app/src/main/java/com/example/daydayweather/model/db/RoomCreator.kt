package com.example.daydayweather.model.db

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object RoomCreator {

    /**
     * this is example to adding version of DB
     *
     *    private val MIGRATION_1_2 = object : Migration(1, 2) {
     *        override fun migrate(database: SupportSQLiteDatabase) {
     *            database.execSQL("ALTER TABLE ToDoEntity ADD COLUMN priority INTEGER NOT NULL DEFAULT 0")
     *        }
     *    }
     */
//    private val MIGRATION_1_2 = object : Migration(1, 2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("ALTER TABLE ToDoEntity ADD COLUMN priority INTEGER NOT NULL DEFAULT 0")
//        }
//    }

    fun getDbPlaces(context: Context): PlacesDb {
        return Room.databaseBuilder(context, PlacesDb::class.java, " myPlaces")
            .build()
    }


}