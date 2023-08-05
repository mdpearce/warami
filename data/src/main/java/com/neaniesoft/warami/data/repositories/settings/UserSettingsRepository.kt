package com.neaniesoft.warami.data.repositories.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.data.FeedListingType
import com.neaniesoft.warami.data.UserSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSettingsRepository @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore = context.userSettingsDataStore

    fun feedListingType(): Flow<ListingType> = dataStore.data.map { settings ->
        when (settings.feedListingType) {
            FeedListingType.UNSPECIFIED -> ListingType.ALL
            FeedListingType.ALL -> ListingType.ALL
            FeedListingType.SUBSCRIBED -> ListingType.SUBSCRIBED
            FeedListingType.LOCAL -> ListingType.LOCAL
            FeedListingType.UNRECOGNIZED -> throw IllegalStateException("Unrecognized listing type: ${settings.feedListingType}")
        }
    }
}

private val Context.userSettingsDataStore: DataStore<UserSettings> by dataStore(
    fileName = "user_settings.proto",
    serializer = UserSettingsSerializer,
)
