package com.neaniesoft.warami.data.repositories.instance

import androidx.datastore.core.CorruptionException
import com.google.protobuf.InvalidProtocolBufferException
import com.neaniesoft.warami.data.InstanceSettings
import java.io.InputStream
import java.io.OutputStream

object InstanceSettingsSerializer : androidx.datastore.core.Serializer<InstanceSettings> {
    override val defaultValue: InstanceSettings = InstanceSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): InstanceSettings {
        return try {
            InstanceSettings.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: InstanceSettings, output: OutputStream) = t.writeTo(output)
}
