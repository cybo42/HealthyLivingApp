package com.cybo42.healthyliving.datastore

import com.cybo42.healthyliving.datastore.db.SavedArticle
import com.cybo42.healthyliving.datastore.db.SavedArticleDao
import com.cybo42.healthyliving.network.dto.Article
import com.cybo42.healthyliving.ui.model.GenericArticle

class SavedArticleRepository (private val savedArticleDao: SavedArticleDao){

    suspend fun saveArticle(article: GenericArticle){
        val saved = SavedArticle(uri = article.uri,
            url = article.url,
            title = article.title,
            summary = article.summary,
            thumbnailUrl = article.thumbnailUrl)


        savedArticleDao.insert(saved)

    }

    suspend fun getAll() = savedArticleDao.getAll()
    suspend fun deleteSaved(article: SavedArticle) = savedArticleDao.deleteById(article.uri)

}
