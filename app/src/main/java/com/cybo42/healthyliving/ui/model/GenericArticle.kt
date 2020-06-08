package com.cybo42.healthyliving.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenericArticle (
    val uri: String,
    val url: String,
    val title: String,
    val summary: String,
    val thumbnailUrl: String
): Parcelable
