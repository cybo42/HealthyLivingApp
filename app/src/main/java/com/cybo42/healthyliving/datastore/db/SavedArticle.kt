package com.cybo42.healthyliving.datastore.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_articles")
data class SavedArticle (
    @PrimaryKey
    val uri: String,
    val url: String,
    val title: String,
    val summary: String,
    val thumbnailUrl: String
)

