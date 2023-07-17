package com.neaniesoft.warami.ui.di

import com.neaniesoft.warami.common.navigation.SignInNavigator
import com.neaniesoft.warami.featurefeed.di.FeedComponent
import com.neaniesoft.warami.signin.di.SignInComponent
import com.neaniesoft.warami.ui.WaramiApp
import com.neaniesoft.warami.ui.nav.WaramiNavigator
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@Component
@UiScope
abstract class UiComponent(
    @Component val feedComponent: FeedComponent,
    @Component val signInComponent: SignInComponent,
) {
    abstract val waramiApp: WaramiApp
}

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class UiScope
