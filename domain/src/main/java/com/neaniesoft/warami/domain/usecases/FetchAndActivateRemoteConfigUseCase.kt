package com.neaniesoft.warami.domain.usecases

import com.google.android.gms.tasks.CancellationTokenSource
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.neaniesoft.warami.domain.di.DomainScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.asDeferred
import me.tatarka.inject.annotations.Inject

@DomainScope
@Inject
class FetchAndActivateRemoteConfigUseCase(private val remoteConfig: FirebaseRemoteConfig) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(): Boolean {
        return remoteConfig.fetchAndActivate().asDeferred(CancellationTokenSource()).await()
    }
}
