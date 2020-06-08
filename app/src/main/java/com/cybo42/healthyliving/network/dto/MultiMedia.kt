package com.cybo42.healthyliving.network.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MultiMedia(
    val caption: String,
    val copyright: String,
    val format: String,
    val height: Int,
    val width: Int,
    val subtype: String,
    val type: String,
    val url: String
) : Parcelable
