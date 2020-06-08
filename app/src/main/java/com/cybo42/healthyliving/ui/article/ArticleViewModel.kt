package com.cybo42.healthyliving.ui.article

import android.util.Log
import androidx.lifecycle.ViewModel
import com.cybo42.healthyliving.coroutine.CoroutineContextProvider
import com.cybo42.healthyliving.datastore.SavedArticleRepository
import com.cybo42.healthyliving.network.dto.Article
import com.cybo42.healthyliving.ui.model.GenericArticle
import kotlinx.coroutines.*

class ArticleViewModel (private val coroutineContextProvider: CoroutineContextProvider,
                        private val savedArticleRepository: SavedArticleRepository
) : ViewModel() {
    private val job = Job()
    private val coroutineScope = CoroutineScope(coroutineContextProvider.IO + job)

    fun saveArticle(article: GenericArticle) {
        coroutineScope.launch {
            withContext(coroutineContextProvider.IO) {
                Log.d("ArticleVM", "saving article ${article.uri}")
                savedArticleRepository.saveArticle(article)
            }
        }
    }
}
