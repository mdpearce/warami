package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.data.repositories.instance.InstanceSettingsRepository
import com.neaniesoft.warami.domain.di.DomainScope
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
@DomainScope
class GetCurrentInstanceDisplayNameUseCase(private val instanceSettingsRepository: InstanceSettingsRepository) {
    operator fun invoke(): Flow<String> = instanceSettingsRepository.currentInstanceName()
}
