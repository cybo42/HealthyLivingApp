package com.cybo42.healthyliving.network.dto

import com.cybo42.healthyliving.network.dto.Article
import com.squareup.moshi.Json

data class TopStoriesResponse (
    val copyright: String,
    @field:Json(name = "last_updated")
    val lastUpdated: String,
    @field:Json(name = "num_results")
    val numResults: Int,
    val results: List<Article>
)
