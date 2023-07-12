package com.neaniesoft.warami.domain.di

import android.content.Context
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.neaniesoft.warami.data.di.DatabaseComponent
import com.neaniesoft.warami.domain.usecases.BuildPostSearchParametersUseCase
import com.neaniesoft.warami.domain.usecases.FetchAndActivateRemoteConfigAsyncUseCase
import com.neaniesoft.warami.domain.usecases.FetchAndActivateRemoteConfigUseCase
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@Component
@DomainScope
abstract class DomainComponent(
    @Component val dataComponent: DatabaseComponent,
) {
    @Provides
    @DomainScope
    fun provideContext(): Context = dataComponent.provideContext()

    @DomainScope
    @Provides
    fun remoteConfigSettings(): FirebaseRemoteConfigSettings = com.google.firebase.remoteconfig.ktx.remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600
    }

    @DomainScope
    @Provides
    fun provideFirebaseRemoteConfig(settings: FirebaseRemoteConfigSettings): FirebaseRemoteConfig =
        Firebase.remoteConfig.apply { setConfigSettingsAsync(settings) }

    abstract val buildPostSearchParametersUseCase: BuildPostSearchParametersUseCase
    abstract val fetchAndActivateRemoteConfigUseCase: FetchAndActivateRemoteConfigUseCase
    abstract val fetchAndActivateRemoteConfigAsyncUseCase: FetchAndActivateRemoteConfigAsyncUseCase
}

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class DomainScope
