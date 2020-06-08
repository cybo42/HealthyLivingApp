package com.cybo42.healthyliving.datastore.db

import androidx.room.*

@Dao
interface SavedArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: SavedArticle)

    @Delete
    suspend fun delete(article: SavedArticle)

    @Query("DELETE FROM saved_articles WHERE uri = :uri")
    suspend fun deleteById(uri: String): Int

    @Query("SELECT * FROM saved_articles")
    suspend fun getAll(): List<SavedArticle>
}
