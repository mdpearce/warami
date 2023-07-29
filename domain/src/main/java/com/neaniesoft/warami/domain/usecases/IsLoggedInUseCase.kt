package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.data.repositories.AccountRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IsLoggedInUseCase
    @Inject
    constructor(
        private val accountRepository: AccountRepository,
    ) {
        operator fun invoke(): Boolean = accountRepository.isLoggedIn()
    }
