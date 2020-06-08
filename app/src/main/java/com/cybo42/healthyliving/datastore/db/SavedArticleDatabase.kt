package com.cybo42.healthyliving.datastore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedArticle::class], version = 1, exportSchema = true)
abstract class SavedArticleDatabase : RoomDatabase(){

    abstract fun savedArticleDao(): SavedArticleDao

    companion object {
        fun build(context: Context) =
            Room.databaseBuilder(
                context,
                SavedArticleDatabase::class.java,
                "saved-articles.db"
            ).fallbackToDestructiveMigration().build()

    }
}
