package com.neaniesoft.warami.data.repositories

import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.api.infrastructure.ApiClient
import com.neaniesoft.warami.data.di.DatabaseScope
import com.neaniesoft.warami.data.repositories.instance.InstanceSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@DatabaseScope
@Inject
class ApiRepository(
    private val apiClientFn: (baseUrl: String) -> ApiClient,
    private val instanceSettingsRepository: InstanceSettingsRepository,
) {

    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        scope.launch {
            instanceSettingsRepository.currentInstanceBaseUrl().collect {
                _api.emit(apiClientFn(it.value).createService(DefaultApi::class.java))
            }
        }
    }


    private val _api: MutableStateFlow<DefaultApi> =
        MutableStateFlow(apiClientFn("http://localhost/").createService(DefaultApi::class.java))

    val api = _api.asStateFlow()
}
