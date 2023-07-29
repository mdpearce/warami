package com.neaniesoft.warami.data.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.neaniesoft.warami.api.di.AuthToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository
    @Inject
    constructor(
        @ApplicationContext context: Context,
    ) {
        companion object {
            private const val AUTH_PREFS_FILENAME = "auth_repository"
            private const val KEY_JWT = "jwt"
        }

        private val scope = CoroutineScope(Dispatchers.IO)

        private val key = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
            context,
            AUTH_PREFS_FILENAME,
            key,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )

        init {
            prefs.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == KEY_JWT) {
                    scope.launch {
                        _jwt.emit(sharedPreferences.getString(KEY_JWT, null))
                    }
                }
            }
        }

        private val _jwt: MutableStateFlow<String?> = MutableStateFlow(prefs.getString(KEY_JWT, null))
        val jwt = _jwt.asStateFlow()

        fun onUpdateAuthToken(authToken: AuthToken) {
            Timber.d("onUpdateAuthToken: $authToken")
            prefs.edit().putString(KEY_JWT, authToken.value).apply()
        }
    }
