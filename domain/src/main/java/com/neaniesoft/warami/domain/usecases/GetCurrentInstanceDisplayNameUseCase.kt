package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.data.repositories.instance.InstanceSettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCurrentInstanceDisplayNameUseCase @Inject constructor(private val instanceSettingsRepository: InstanceSettingsRepository) {
    operator fun invoke(): Flow<String> = instanceSettingsRepository.currentInstanceName()
}
