package com.neaniesoft.warami.data.repositories.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.data.FeedListingType
import com.neaniesoft.warami.data.UserSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSettingsRepository
@Inject
constructor(
    @ApplicationContext context: Context,
) {
    private val dataStore = context.userSettingsDataStore

    fun feedListingType(): Flow<ListingType> = dataStore.data.map { settings ->
        settings.feedListingType
    }.catch { emit(FeedListingType.ALL) }
        .map { listingType ->
            when (listingType) {
                FeedListingType.UNSPECIFIED -> ListingType.ALL
                FeedListingType.ALL -> ListingType.ALL
                FeedListingType.SUBSCRIBED -> ListingType.SUBSCRIBED
                FeedListingType.LOCAL -> ListingType.LOCAL
                FeedListingType.UNRECOGNIZED, null -> {
                    Timber.w("Unrecognized value for FeedListingType: $listingType")
                    ListingType.ALL
                }
            }
        }

    suspend fun setFeedListingType(listingType: ListingType) {
        val feedListingType = when (listingType) {
            ListingType.ALL -> FeedListingType.ALL
            ListingType.SUBSCRIBED -> FeedListingType.SUBSCRIBED
            ListingType.LOCAL -> FeedListingType.LOCAL
        }
        dataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setFeedListingType(feedListingType)
                .build()
        }
    }
}

private val Context.userSettingsDataStore: DataStore<UserSettings> by dataStore(
    fileName = "user_settings.proto",
    serializer = UserSettingsSerializer,
)
