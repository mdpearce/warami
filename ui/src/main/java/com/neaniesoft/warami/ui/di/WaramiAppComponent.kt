package com.neaniesoft.warami.ui.di

import com.neaniesoft.warami.featurefeed.di.FeedComponent
import com.neaniesoft.warami.signin.di.SignInComponent
import com.neaniesoft.warami.ui.WaramiApp
import me.tatarka.inject.annotations.Component

@Component
abstract class WaramiAppComponent(
    @Component val feedComponent: FeedComponent,
    @Component val signInComponent: SignInComponent,
) {
    abstract val waramiApp: WaramiApp
}
