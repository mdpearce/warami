package com.neaniesoft.warami.data.repositories

import android.util.Log
import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.api.di.AuthToken
import com.neaniesoft.warami.api.infrastructure.ApiClient
import com.neaniesoft.warami.data.di.DatabaseScope
import com.neaniesoft.warami.data.repositories.instance.InstanceSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@DatabaseScope
@Inject
class ApiRepository(
    private val apiClientFn: (baseUrl: String, authToken: AuthToken) -> ApiClient,
    private val instanceSettingsRepository: InstanceSettingsRepository,
    private val authRepository: AuthRepository,
) {

    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        scope.launch {
            val baseUrlFlow = instanceSettingsRepository.currentInstanceBaseUrl()
            val authTokenFlow = authRepository.jwt
            baseUrlFlow.combine(authTokenFlow) { baseUrl, authToken ->
                Log.d("ApiRepository", "Combining baseUrl ${baseUrl.value} with auth token $authToken")
                _api.emit(
                    apiClientFn(
                        baseUrl.value,
                        authToken?.let { AuthToken(it) } ?: AuthToken(""),
                    ).createService(DefaultApi::class.java),
                )
            }
        }
    }


    private val _api: MutableStateFlow<DefaultApi> =
        MutableStateFlow(apiClientFn("http://localhost/", AuthToken("")).createService(DefaultApi::class.java))
    val api = _api.asStateFlow()
}
