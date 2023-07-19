package com.neaniesoft.warami.api.di

import android.content.Context
import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.api.infrastructure.ApiClient
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@ApiScope
@Component
abstract class ApiComponent(
    @get:Provides val context: Context,
) {

    @Provides
    @ApiScope
    fun provideApi(apiClient: ApiClient): DefaultApi {
        return apiClient.createService(DefaultApi::class.java)
    }

    @Provides
    @ApiScope
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
                level = HttpLoggingInterceptor.Level.BODY
            },
        )
    }

    @Provides
    @ApiScope
    fun provideOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient {
        return builder.build()
    }

    @Provides
    @ApiScope
    fun provideApiClientFn(clientBuilder: OkHttpClient.Builder): (baseUrl: String) -> ApiClient =
        { baseUrl ->
            ApiClient(baseUrl = "${baseUrl.trimEnd('/')}/api/v3", okHttpClientBuilder = clientBuilder)
        }

}

@JvmInline
value class AuthToken(val value: String)
