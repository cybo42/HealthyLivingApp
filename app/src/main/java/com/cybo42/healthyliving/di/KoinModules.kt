package com.cybo42.healthyliving.di

import android.app.Application
import com.cybo42.healthyliving.config.ApiKeyProvider
import com.cybo42.healthyliving.config.ApiKeyProviderImpl

import com.cybo42.healthyliving.config.Flipper
import com.cybo42.healthyliving.config.FlipperConfig
import com.cybo42.healthyliving.coroutine.CoroutineContextProvider
import com.cybo42.healthyliving.datastore.SavedArticleRepository
import com.cybo42.healthyliving.datastore.db.SavedArticleDatabase
import com.cybo42.healthyliving.network.api.NYTArticleApi
import com.cybo42.healthyliving.network.NYTAuthInterceptor
import com.cybo42.healthyliving.ui.article.ArticleViewModel
import com.cybo42.healthyliving.ui.saved.SavedArticlesViewModel
import com.cybo42.healthyliving.ui.topstories.TopHealthStoriesViewModel

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*


val viewModelModule = module {
    single{
        TopHealthStoriesViewModel(get(), get())
    }

    single {
        ArticleViewModel(get(), get())
    }

    single{
        SavedArticlesViewModel(get(), get())
    }
}

val apiModule = module {
    fun provideNYTArticleApi(retrofit: Retrofit): NYTArticleApi {
        return retrofit.create(NYTArticleApi::class.java)
    }

    single {
        provideNYTArticleApi(get())
    }
}

val appModule = module {

    fun provideFlipper(): Flipper = FlipperConfig()

    fun provideCoroutineContextProvider() = CoroutineContextProvider()
    single {
        provideFlipper()
    }
    single {
        provideCoroutineContextProvider()
    }
}

val databaseModule = module {
    single {
        SavedArticleDatabase.build(get())
    }

    single {
        get<SavedArticleDatabase>().savedArticleDao()
    }

    single {
        SavedArticleRepository(get())
    }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideApiKeyProvider():ApiKeyProvider  = ApiKeyProviderImpl()

    fun provideNytAuthInterceptor(apiKeyProvider: ApiKeyProvider): NYTAuthInterceptor {
        return NYTAuthInterceptor(apiKeyProvider)
    }


    fun provideHttpClient(cache: Cache, flipper: Flipper, nytAuthInterceptor: NYTAuthInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        }
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(nytAuthInterceptor)
            .cache(cache)

        flipper.addInterceptors(okHttpClientBuilder)
        return okHttpClientBuilder.build()
    }

    fun provideMoshi(): Moshi {
        val moshi: Moshi =
            Moshi.Builder()
                .add(Date::class.java, Rfc3339DateJsonAdapter())
//            .add(KotlinJsonAdapterFactory())
                .build()

        return moshi
    }
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideApiKeyProvider() }
    single { provideNytAuthInterceptor(get()) }
    single { provideCache(androidApplication()) }
    single { provideHttpClient(get(), get(), get()) }
    single { provideMoshi() }
    single { provideRetrofit(get(), get()) }
}
