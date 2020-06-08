package com.cybo42.healthyliving.ui.topstories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cybo42.healthyliving.network.api.NYTArticleApi
import com.cybo42.healthyliving.network.dto.Article
import com.cybo42.healthyliving.network.dto.TopStoriesResponse
import com.cybo42.healthyliving.test.TestCoroutineContextProvider
import com.cybo42.healthyliving.test.TestCoroutineRule
import com.cybo42.healthyliving.test.getOrAwaitValue
import com.cybo42.healthyliving.ui.model.PendingData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TopHealthStoriesViewModelTest  {

    private lateinit var viewModel: TopHealthStoriesViewModel
    private lateinit var nytArticleApi: NYTArticleApi

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test successful article call`() = testCoroutineRule.testCoroutineDispatcher.runBlockingTest {
        nytArticleApi = object: NYTArticleApi{
            override suspend fun fetchHealthTopStories(): TopStoriesResponse {
                return TopStoriesResponse("copyright", "now", 0, emptyList())
            }

        }

        viewModel = TopHealthStoriesViewModel(
            TestCoroutineContextProvider(
                testCoroutineRule.testCoroutineDispatcher
            ), nytArticleApi)

        viewModel.fetchArticles()
        val response = viewModel.articles.getOrAwaitValue()

        assertThat(response is PendingData.Success<List<Article>>).isTrue()


    }

    @Test
    fun `test error article call`() = testCoroutineRule.testCoroutineDispatcher.runBlockingTest {
        nytArticleApi = object: NYTArticleApi{
            override suspend fun fetchHealthTopStories(): TopStoriesResponse {
                throw RuntimeException("BOOM")
            }

        }

        viewModel = TopHealthStoriesViewModel(
            TestCoroutineContextProvider(
                testCoroutineRule.testCoroutineDispatcher
            ), nytArticleApi)

        viewModel.fetchArticles()
        val response = viewModel.articles.getOrAwaitValue()

        assertThat(response is PendingData.Error<List<Article>>).isTrue()


    }
}
