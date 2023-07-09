package com.neaniesoft.warami.data.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.neaniesoft.warami.data.di.DatabaseScope
import me.tatarka.inject.annotations.Inject

@Inject
@DatabaseScope
class AuthRepository(
    private val context: Context,
) {
    companion object {
        private const val AUTH_PREFS_FILENAME = "auth_repository"
        private const val KEY_JWT = "jwt"
    }

    private val key = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        AUTH_PREFS_FILENAME,
        key,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    var jwt: String?
        get() {
            return prefs.getString(KEY_JWT, null)
        }
        set(value) {
            prefs.edit().putString(KEY_JWT, value).apply()
        }
}
