package com.neaniesoft.warami.featurefeed.di

import com.neaniesoft.warami.domain.di.DomainComponent
import com.neaniesoft.warami.featurefeed.FeedScreen
import com.neaniesoft.warami.featurefeed.FeedViewModel
import me.tatarka.inject.annotations.Component

@Component
@FeedScope
abstract class FeedComponent(
    @Component val domainComponent: DomainComponent,
) {
    abstract val feedScreen: FeedScreen

    @get:FeedScope
    abstract val feedViewModelProvider: () -> FeedViewModel
}
