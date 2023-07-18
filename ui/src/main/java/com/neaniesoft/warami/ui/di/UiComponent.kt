package com.neaniesoft.warami.ui.di

import com.neaniesoft.warami.common.navigation.HomeNavigator
import com.neaniesoft.warami.featurefeed.di.FeedComponent
import com.neaniesoft.warami.signin.di.SignInComponent
import com.neaniesoft.warami.ui.HomeViewModel
import com.neaniesoft.warami.ui.WaramiApp
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@Component
@UiScope
abstract class UiComponent(
    @Component val feedComponent: FeedComponent,
    @Component val signInComponent: SignInComponent,
    @get:UiScope @get:Provides val homeNavigator: HomeNavigator,
) {
    abstract val waramiApp: WaramiApp

    @get:UiScope
    abstract val homeViewModelProvider: () -> HomeViewModel
}

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class UiScope
