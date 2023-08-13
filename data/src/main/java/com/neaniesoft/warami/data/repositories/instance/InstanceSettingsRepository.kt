package com.neaniesoft.warami.data.repositories.instance

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.data.InstanceSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InstanceSettingsRepository
@Inject
constructor(
    @ApplicationContext context: Context,
) {
    private val dataStore = context.instanceSettingsDataStore

    fun currentInstanceName(): Flow<String> = dataStore.data.map { it.instanceName }

    fun currentInstanceBaseUrl(): Flow<UriString> = dataStore.data.map { UriString(it.instanceBaseUrl) }.filterNot { it == UriString("") }

    suspend fun setInstance(name: String, baseUrl: UriString) {
        dataStore.updateData { currentInstanceSettings ->
            currentInstanceSettings.toBuilder()
                .setInstanceName(name)
                .setInstanceBaseUrl(baseUrl.value)
                .build()
        }
    }
}

val Context.instanceSettingsDataStore: DataStore<InstanceSettings> by dataStore(
    fileName = "instance_settings.proto",
    serializer = InstanceSettingsSerializer,
)
