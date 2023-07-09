package com.neaniesoft.warami.di

import android.content.Context
import com.neaniesoft.warami.WaramiApplication
import com.neaniesoft.warami.data.di.DatabaseComponent
import com.neaniesoft.warami.featurefeed.di.FeedComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@AppScope
@Component
abstract class AppComponent(
    @Component val databaseComponent: DatabaseComponent,
    @Component val feedComponent: FeedComponent,
) {
    @AppScope
    @Provides
    fun provideContext(): Context = WaramiApplication.getInstance()
}
