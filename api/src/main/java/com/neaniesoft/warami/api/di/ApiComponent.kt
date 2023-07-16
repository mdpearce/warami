package com.neaniesoft.warami.api.di

import android.content.Context
import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.api.auth.ApiKeyAuth
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
    fun provideApiClientFn(context: Context, clientBuilder: OkHttpClient.Builder): (baseUrl: String) -> ApiClient = { baseUrl ->
        ApiClient(baseUrl = baseUrl, okHttpClientBuilder = clientBuilder)
            .addAuthorization("AuthKey", ApiKeyAuth(location = "query", "auth", ""))

    }

}
