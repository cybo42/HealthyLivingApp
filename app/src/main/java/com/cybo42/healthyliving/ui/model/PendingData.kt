package com.cybo42.healthyliving.ui.model

sealed class PendingData<out T : Any?> {
    class Success<out T : Any>(val data: T) : PendingData<T>()
    class Loading<Nothing> : PendingData<Nothing>()
    class Error<out T : Any>(val throwable: Throwable, val data: T? = null) : PendingData<T>()
}

