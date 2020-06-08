package com.cybo42.healthyliving.network

import com.cybo42.healthyliving.config.ApiKeyProvider
import okhttp3.Interceptor
import okhttp3.Response

class NYTAuthInterceptor(private val apiKeyProvider: ApiKeyProvider): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url
        val urlWithApiKey = url.newBuilder()
            .addQueryParameter("api-key", apiKeyProvider.fetchNYTApiKey())
            .build()

        val authenticatedRequest = request.newBuilder()
            .url(urlWithApiKey)
            .build()

        return chain.proceed(authenticatedRequest)
    }
}
