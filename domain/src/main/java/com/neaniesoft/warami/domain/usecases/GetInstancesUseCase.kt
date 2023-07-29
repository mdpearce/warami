package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.data.repositories.RemoteConfigRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetInstancesUseCase
    @Inject
    constructor(private val remoteConfigRepository: RemoteConfigRepository) {

        companion object {
            private const val REMOTE_CONFIG_KEY = "lemmyverse_instances_download_url"
        }

        operator fun invoke(): UriString = UriString(remoteConfigRepository.getString(REMOTE_CONFIG_KEY))
    }
