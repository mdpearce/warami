package com.neaniesoft.warami.data.repositories

import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.api.di.AuthToken
import com.neaniesoft.warami.api.infrastructure.ApiClient
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.data.repositories.instance.InstanceSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository
    @Inject
    constructor(
        private val apiClientFn: Function1<@JvmSuppressWildcards String, @JvmSuppressWildcards ApiClient>,
        private val instanceSettingsRepository: InstanceSettingsRepository,
        private val authRepository: AuthRepository,
    ) {

        private val scope = CoroutineScope(Dispatchers.Default)

        private val _api: MutableStateFlow<DefaultApi> =
            MutableStateFlow(apiClientFn("http://localhost/").createService(DefaultApi::class.java))
        val api = _api.asStateFlow()

        init {
            scope.launch {
                instanceSettingsRepository.currentInstanceBaseUrl().distinctUntilChanged().collect { baseUriString ->
                    Timber.d("baseUrl changed. collecting $baseUriString")
                    updateApi(baseUriString, authRepository.jwt.value?.let { AuthToken(it) })
                }
            }
            scope.launch {
                authRepository.jwt.collect { authToken ->
                    Timber.d("jwt changed, collecting $authToken")
                    updateApi(
                        instanceSettingsRepository.currentInstanceBaseUrl().firstOrNull() ?: UriString(""),
                        authToken?.let { AuthToken(it) },
                    )
                }
            }
        }

        private suspend fun updateApi(baseUrl: UriString, authToken: AuthToken?) {
            Timber.d("Updating API baseUrl ${baseUrl.value} with auth token $authToken")
            _api.emit(
                apiClientFn(
                    baseUrl.value,
                ).createService(DefaultApi::class.java),
            )
        }
    }
