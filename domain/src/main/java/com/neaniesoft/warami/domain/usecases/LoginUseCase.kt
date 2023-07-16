package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.RemoteResult
import com.neaniesoft.warami.data.repositories.SignInRepository
import com.neaniesoft.warami.domain.di.DomainScope
import me.tatarka.inject.annotations.Inject

@Inject
@DomainScope
class LoginUseCase(private val signInRepository: SignInRepository) {
    suspend operator fun invoke(username: String, password: String): RemoteResult<Unit> {
        return signInRepository.login(usernameOrEmail = username, password = password)
    }
}
