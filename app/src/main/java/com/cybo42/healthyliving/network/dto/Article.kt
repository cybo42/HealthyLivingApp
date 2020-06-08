package com.cybo42.healthyliving.network.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article (
    val abstract: String,
    val byline: String,
    @field:Json(name = "created_date")
    val createdDate: String,
    @field:Json(name = "item_type")
    val itemType: String,
    val kicker: String,
    val multimedia: List<MultiMedia>,
    @field:Json(name = "published_date")
    val publishedDate: String,
    val section: String,
    @field:Json(name = "short_url")
    val shortUrl: String,
    val subsection: String,
    val title: String,
    @field:Json(name = "updated_date")
    val updatedDate: String,
    val uri: String,
    val url: String



): Parcelable {
    companion object {
        const val IMAGE_TYPE_THUMBNAIL = "thumbLarge"
    }

    val thumbnail: String
    get() = fixRelativeUrl(multimedia.first {
        (IMAGE_TYPE_THUMBNAIL  == it.format) || (IMAGE_TYPE_THUMBNAIL == it.subtype)
    }.url)

    private fun fixRelativeUrl(url: String): String = if (url.startsWith("http")) {
        url
    } else {
        "https://www.nytimes.com/${url}"
    }

}

