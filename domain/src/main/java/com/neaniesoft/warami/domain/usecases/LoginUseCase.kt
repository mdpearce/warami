package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.RemoteResult
import com.neaniesoft.warami.data.repositories.AccountRepository
import com.neaniesoft.warami.domain.di.DomainScope
import me.tatarka.inject.annotations.Inject
import java.net.URLEncoder

@Inject
@DomainScope
class LoginUseCase(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(username: String, password: String): RemoteResult<Unit> {
        return accountRepository.login(usernameOrEmail = URLEncoder.encode(username, Charsets.UTF_8.name()), password = password)
    }
}
