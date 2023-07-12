package com.neaniesoft.warami.domain.usecases

import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.neaniesoft.warami.domain.di.DomainScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@DomainScope
@Inject
class FetchAndActivateRemoteConfigAsyncUseCase(
    private val remoteConfig: FirebaseRemoteConfig,
    private val fetchAndActivateRemoteConfig: FetchAndActivateRemoteConfigUseCase,
) {
    operator fun invoke(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            val remoteConfigFetched = fetchAndActivateRemoteConfig()
            if (remoteConfigFetched) {
                remoteConfig.addOnConfigUpdateListener(
                    object : ConfigUpdateListener {
                        override fun onUpdate(configUpdate: ConfigUpdate) {
                            remoteConfig.activate()
                        }

                        override fun onError(error: FirebaseRemoteConfigException) {
                        }
                    },
                )
            }
        }
    }
}
