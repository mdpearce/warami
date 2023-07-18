package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.data.repositories.AccountRepository
import com.neaniesoft.warami.domain.di.DomainScope
import me.tatarka.inject.annotations.Inject

@Inject
@DomainScope
class IsLoggedInUseCase(
    private val accountRepository: AccountRepository,
) {
    operator fun invoke(): Boolean = accountRepository.isLoggedIn()
}
