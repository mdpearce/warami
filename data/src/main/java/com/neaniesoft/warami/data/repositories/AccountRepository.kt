package com.neaniesoft.warami.data.repositories

import com.neaniesoft.warami.api.di.AuthToken
import com.neaniesoft.warami.api.models.Login
import com.neaniesoft.warami.common.RemoteResult
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository
@Inject
constructor(private val apiRepository: ApiRepository, private val authRepository: AuthRepository) {
    suspend fun login(usernameOrEmail: String, password: String): RemoteResult<Unit> {
        val api = apiRepository.api.value

        return try {
            val body = api.login(Login(usernameOrEmail, password)).body()
            if (body == null) {
                RemoteResult.Err(IllegalStateException("Response body was null"))
            } else {
                val jwt = body.jwt
                if (jwt == null) {
                    RemoteResult.Err(IllegalStateException("JWT was null"))
                } else {
                    authRepository.onUpdateAuthToken(AuthToken(jwt))
                    RemoteResult.Ok(Unit)
                }
            }
        } catch (e: IOException) {
            RemoteResult.Err(e)
        } catch (e: HttpException) {
            RemoteResult.Err(e)
        }
    }

    fun isLoggedIn(): Boolean {
        val jwt = authRepository.jwt.value
        return !jwt.isNullOrEmpty()
    }

    fun authToken(): String? {
        return authRepository.jwt.value
    }
}
