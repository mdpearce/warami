package com.neaniesoft.warami.featurefeed.di

import com.neaniesoft.warami.common.navigation.FeedNavigator
import com.neaniesoft.warami.common.navigation.SignInNavigator
import com.neaniesoft.warami.domain.di.DomainComponent
import com.neaniesoft.warami.featurefeed.CommentsViewModel
import com.neaniesoft.warami.featurefeed.FeedScreen
import com.neaniesoft.warami.featurefeed.FeedViewModel
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@FeedScope
abstract class FeedComponent(
    @Component val domainComponent: DomainComponent,
    @get:Provides @get:FeedScope val feedNavigator: FeedNavigator,
    ) {
    abstract val feedScreen: FeedScreen

    @get:FeedScope
    abstract val feedViewModelProvider: () -> FeedViewModel
    @get:FeedScope
    abstract val commentsViewModelProvider: () -> CommentsViewModel
}
