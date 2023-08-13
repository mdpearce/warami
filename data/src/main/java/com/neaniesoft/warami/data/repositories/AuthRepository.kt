package com.neaniesoft.warami.data.repositories

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
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
@Inject constructor(
    @ApplicationContext context: Context,
) {
    companion object {
        private const val AUTH_PREFS_FILENAME = "auth_repository"
        private const val KEY_JWT = "jwt"
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    private val spec = KeyGenParameterSpec.Builder(
        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
    ).apply {
        setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
    }.build()

    private val masterKey = MasterKey.Builder(context).apply {
        setKeyGenParameterSpec(spec)
    }.build()

    private val prefs: SharedPreferences by lazy {
        EncryptedSharedPreferences(
            context,
            AUTH_PREFS_FILENAME,
            masterKey,
        )
    }

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
