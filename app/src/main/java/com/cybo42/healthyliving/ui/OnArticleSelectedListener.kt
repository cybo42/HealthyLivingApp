package com.cybo42.healthyliving.ui

import com.cybo42.healthyliving.datastore.db.SavedArticle
import com.cybo42.healthyliving.network.dto.Article

interface OnArticleSelectedListener {
    fun onArticleSelected(article: Article)
}

interface OnSavedArticleSelectedListener {
    fun onArticleSelected(article: SavedArticle)
}

