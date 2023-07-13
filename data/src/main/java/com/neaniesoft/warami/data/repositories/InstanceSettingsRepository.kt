package com.neaniesoft.warami.data.repositories

import com.neaniesoft.warami.api.infrastructure.Serializer
import com.neaniesoft.warami.data.InstanceSettings
import com.neaniesoft.warami.data.di.DatabaseScope
import me.tatarka.inject.annotations.Inject
import java.io.InputStream
import java.io.OutputStream

@DatabaseScope
@Inject
class InstanceSettingsRepository {

}

object InstanceSettingsSerializer : androidx.datastore.core.Serializer<InstanceSettings> {
    override val defaultValue: InstanceSettings
        get() = TODO("Not yet implemented")

    override suspend fun readFrom(input: InputStream): InstanceSettings {
        TODO("Not yet implemented")
    }

    override suspend fun writeTo(t: InstanceSettings, output: OutputStream) {
        TODO("Not yet implemented")
    }

}
