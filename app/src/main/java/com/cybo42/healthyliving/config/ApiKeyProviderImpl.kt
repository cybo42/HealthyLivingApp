package com.cybo42.healthyliving.config

import com.cybo42.healthyliving.BuildConfig

class ApiKeyProviderImpl : ApiKeyProvider {
    override fun fetchNYTApiKey(): String {
        return BuildConfig.APIKeyNYT
    }
}
