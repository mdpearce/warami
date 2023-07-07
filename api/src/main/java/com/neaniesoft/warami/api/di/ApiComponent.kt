package com.neaniesoft.warami.api.di

import android.content.Context
import android.util.Log
import com.neaniesoft.warami.api.R
import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.api.auth.ApiKeyAuth
import com.neaniesoft.warami.api.infrastructure.ApiClient
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@ApiScope
@Component
abstract class ApiComponent(
    @get:Provides val context: Context
) {

    @Provides
    @ApiScope
    fun provideApi(apiClient: ApiClient): DefaultApi {
        return apiClient.createService(DefaultApi::class.java)
    }

    @Provides
    @ApiScope
    fun provideApiClient(context: Context): ApiClient {
        val baseUrl = context.getString(R.string.warami_base_url)
        return ApiClient(baseUrl = baseUrl)
            .setLogger { message -> Log.d("ApiClient", message) }
            .addAuthorization("AuthKey", ApiKeyAuth(location = "query", "auth", ""))
    }
}
