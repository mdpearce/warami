package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.data.repositories.AuthRepository
import com.neaniesoft.warami.common.RemoteResult
import com.neaniesoft.warami.domain.di.DomainScope
import me.tatarka.inject.annotations.Inject

@Inject
@DomainScope
class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): RemoteResult<Unit> {
        return authRepository.login(usernameOrEmail = username, password = password)
    }
}
