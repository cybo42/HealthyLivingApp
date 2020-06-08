package com.cybo42.healthyliving.config

interface ApiKeyProvider {
    fun fetchNYTApiKey(): String
}
