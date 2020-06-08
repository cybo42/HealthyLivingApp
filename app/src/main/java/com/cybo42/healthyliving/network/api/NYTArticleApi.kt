package com.cybo42.healthyliving.network.api

import com.cybo42.healthyliving.network.dto.TopStoriesResponse
import retrofit2.http.GET

interface NYTArticleApi {

    @GET("svc/topstories/v2/health.json")
    suspend fun fetchHealthTopStories(): TopStoriesResponse
}

