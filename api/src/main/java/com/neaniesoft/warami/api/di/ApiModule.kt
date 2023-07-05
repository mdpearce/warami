package com.neaniesoft.warami.api.di

import android.content.Context
import android.util.Log
import com.neaniesoft.warami.api.R
import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.api.auth.ApiKeyAuth
import com.neaniesoft.warami.api.infrastructure.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideApiClient(@ApplicationContext context: Context): ApiClient {
        val baseUrl = context.getString(R.string.warami_base_url)
        return ApiClient(baseUrl = baseUrl)
            .setLogger { message -> Log.d("ApiClient", message) }
            .addAuthorization("AuthKey", ApiKeyAuth(location = "query", "auth", ""))
    }
}
