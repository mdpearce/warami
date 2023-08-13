package com.neaniesoft.warami.data.repositories

import com.google.android.gms.tasks.CancellationTokenSource
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.asDeferred
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigRepository
@Inject
constructor(private val remoteConfig: FirebaseRemoteConfig) {
    init {
        remoteConfig.fetch().addOnCompleteListener {
            remoteConfig.activate()
            remoteConfig.addOnConfigUpdateListener(
                object : ConfigUpdateListener {
                    override fun onUpdate(configUpdate: ConfigUpdate) {
                        remoteConfig.activate()
                    }

                    override fun onError(error: FirebaseRemoteConfigException) {
                        Timber.e(error, "Error updating remote config")
                    }
                },
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun fetchAndActivate(): Boolean {
        return remoteConfig.fetchAndActivate().asDeferred(CancellationTokenSource()).await()
    }

    fun getBoolean(key: String): Boolean = remoteConfig.getBoolean(key)

    fun getDouble(key: String): Double = remoteConfig.getDouble(key)

    fun getLong(key: String): Long = remoteConfig.getLong(key)

    fun getString(key: String): String = remoteConfig.getString(key)
}
