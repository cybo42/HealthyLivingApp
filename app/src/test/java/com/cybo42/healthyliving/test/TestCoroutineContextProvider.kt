package com.cybo42.healthyliving.test

import com.cybo42.healthyliving.coroutine.CoroutineContextProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

class TestCoroutineContextProvider(private val dispatcher: CoroutineDispatcher) : CoroutineContextProvider(){
    override val Main: CoroutineContext
        get() = dispatcher

    override val IO: CoroutineContext
        get() = dispatcher
}
