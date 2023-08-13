package com.neaniesoft.warami.featurefeed.di

import com.neaniesoft.warami.data.repositories.settings.UserSettingsRepository
import com.neaniesoft.warami.domain.usecases.BuildFullListOfSortTypesUseCase
import com.neaniesoft.warami.domain.usecases.IsLoggedInUseCase
import com.neaniesoft.warami.featurefeed.ListingTypeSelectable
import com.neaniesoft.warami.featurefeed.ListingTypeSelectableImpl
import com.neaniesoft.warami.featurefeed.SortTypeSelectable
import com.neaniesoft.warami.featurefeed.SortTypeSelectableImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
open class FeedModule {
    @ViewModelScoped
    @Provides
    fun provideListingTypeSelectable(userSettingsRepository: UserSettingsRepository, isLoggedIn: IsLoggedInUseCase): ListingTypeSelectable =
        ListingTypeSelectableImpl(userSettingsRepository, isLoggedIn)

    @ViewModelScoped
    @Provides
    fun provideSortTypeSelectable(buildFullListOfSortTypes: BuildFullListOfSortTypesUseCase): SortTypeSelectable =
        SortTypeSelectableImpl(buildFullListOfSortTypes)
}
