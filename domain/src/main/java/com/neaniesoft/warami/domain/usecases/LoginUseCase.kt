package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.RemoteResult
import com.neaniesoft.warami.data.repositories.AccountRepository
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase
    @Inject
    constructor(private val accountRepository: AccountRepository) {
        suspend operator fun invoke(username: String, password: String): RemoteResult<Unit> {
            return accountRepository.login(usernameOrEmail = URLEncoder.encode(username, Charsets.UTF_8.name()), password = password)
        }
    }
