package com.neaniesoft.warami.api.di

import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.api.infrastructure.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideApi(apiClient: ApiClient): DefaultApi {
        return apiClient.createService(DefaultApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
                level = HttpLoggingInterceptor.Level.BODY
            },
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient {
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideApiClientFn(clientBuilder: OkHttpClient.Builder): (baseUrl: String) -> ApiClient =
        { baseUrl ->
            ApiClient(baseUrl = "${baseUrl.trimEnd('/')}/api/v3", okHttpClientBuilder = clientBuilder)
        }

}

@JvmInline
value class AuthToken(val value: String)
