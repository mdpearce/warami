package com.neaniesoft.warami.di

import android.content.Context
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.neaniesoft.warami.WaramiApplication
import com.neaniesoft.warami.data.di.DatabaseComponent
import com.neaniesoft.warami.domain.di.DomainComponent
import com.neaniesoft.warami.featurefeed.di.FeedComponent
import com.neaniesoft.warami.signin.di.SignInComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@AppScope
@Component
abstract class AppComponent(
    @Component val databaseComponent: DatabaseComponent,
    @Component val feedComponent: FeedComponent,
    @Component val signInComponent: SignInComponent,
) {
    @AppScope
    @Provides
    fun provideContext(): Context = WaramiApplication.getInstance()
}
