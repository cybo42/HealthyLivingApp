package com.cybo42.healthyliving.ui.topstories

import android.util.Log
import androidx.lifecycle.*
import com.cybo42.healthyliving.coroutine.CoroutineContextProvider
import com.cybo42.healthyliving.datastore.SavedArticleRepository
import com.cybo42.healthyliving.network.api.NYTArticleApi
import com.cybo42.healthyliving.network.dto.Article
import com.cybo42.healthyliving.ui.model.PendingData
import kotlinx.coroutines.*

class TopHealthStoriesViewModel(private val coroutineContextProvider: CoroutineContextProvider,
                                private val nytArticleApi: NYTArticleApi) : ViewModel() {

    private val _articles = MutableLiveData<PendingData<List<Article>>>()
    val articles: LiveData<PendingData<List<Article>>>
        get() = _articles

    init {
        fetchArticles()
    }

    fun fetchArticles(){
        viewModelScope.launch{
            withContext(coroutineContextProvider.IO) {
                _articles.postValue(PendingData.Loading())
                try {
                    val response = nytArticleApi.fetchHealthTopStories()
                    _articles.postValue(PendingData.Success(response.results))
                } catch (e: Exception) {
                    _articles.postValue(PendingData.Error(e))
                }
            }
        }
    }


}
