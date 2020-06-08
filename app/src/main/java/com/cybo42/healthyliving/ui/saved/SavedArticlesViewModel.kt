package com.cybo42.healthyliving.ui.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cybo42.healthyliving.coroutine.CoroutineContextProvider
import com.cybo42.healthyliving.datastore.SavedArticleRepository
import com.cybo42.healthyliving.datastore.db.SavedArticle
import com.cybo42.healthyliving.ui.model.PendingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedArticlesViewModel(
    private val coroutineContextProvider: CoroutineContextProvider,
    private val savedArticleRepository: SavedArticleRepository
) : ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(coroutineContextProvider.IO + job)

    private val _savedArticles = MutableLiveData<PendingData<List<SavedArticle>>>()
    val savedArticles: LiveData<PendingData<List<SavedArticle>>>
        get() = _savedArticles

    init {
        fetchSavedArticles()
    }

    fun deleteSavedArticle(article: SavedArticle) {
        coroutineScope.launch {
            withContext(coroutineContextProvider.IO) {
                savedArticleRepository.deleteSaved(article)
                fetchSavedArticles()
            }
        }
    }

    fun fetchSavedArticles() {
        coroutineScope.launch {
            withContext(coroutineContextProvider.IO) {
                try {
                    val articles = savedArticleRepository.getAll()
                    _savedArticles.postValue(PendingData.Success(articles))

                } catch (e: Exception) {
                    _savedArticles.postValue(PendingData.Error(e))
                }
            }
        }
    }

}
