package com.cybo42.healthyliving.config

import android.app.Application
import okhttp3.OkHttpClient

class FlipperConfig: Flipper {
    override fun addInterceptors(okHttpBuilder: OkHttpClient.Builder) {
        // NO-OP
    }

    override fun init(application: Application) {
        // NO-OP
    }

}
