package com.neaniesoft.warami.data.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.neaniesoft.warami.data.di.DatabaseScope
import me.tatarka.inject.annotations.Inject
import retrofit2.HttpException
import java.io.IOException

@Inject
@DatabaseScope
class AuthRepository(
    private val context: Context,
    private val apiRepository: ApiRepository,
) {
    companion object {
        private const val AUTH_PREFS_FILENAME = "auth_repository"
        private const val KEY_JWT = "jwt"
    }

    private val api
        get() = apiRepository.api.value

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

    suspend fun login(usernameOrEmail: String, password: String) {
        val response = try {
            val body = api.login(usernameOrEmail, password).body()
            if (body == null) {
                RemoteResult.Err(IllegalStateException("Response body was null"))
            } else {
                if (body.jwt == null) {
                    RemoteResult.Err(IllegalStateException("JWT was null"))
                } else {
                    jwt = body.jwt
                    RemoteResult.Ok(Unit)
                }
            }
        } catch (e: IOException) {
            RemoteResult.Err(e)
        } catch (e: HttpException) {
            RemoteResult.Err(e)
        }
    }
}

sealed class RemoteResult<T> {
    data class Ok<T>(val value: T) : RemoteResult<T>()
    data class Err<T>(val e: Exception) : RemoteResult<T>()
}
