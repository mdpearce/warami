package com.neaniesoft.warami.ui.di

import com.neaniesoft.warami.common.navigation.HomeNavigator
import com.neaniesoft.warami.ui.nav.WaramiNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UiModule {
    @Provides
    @Singleton
    fun provideHomeNavigator(navigator: WaramiNavigator): HomeNavigator = navigator

    @Provides
    @Singleton
    fun provideWaramiNavigator(): WaramiNavigator = WaramiNavigator
}
