package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.data.repositories.RemoteConfigRepository
import com.neaniesoft.warami.domain.di.DomainScope
import me.tatarka.inject.annotations.Inject

@DomainScope
@Inject
class GetInstancesDownloadUrlUseCase(private val remoteConfigRepository: RemoteConfigRepository) {

    companion object {
        private const val REMOTE_CONFIG_KEY = "lemmyverse_instances_download_url"
    }

    operator fun invoke(): UriString = UriString(remoteConfigRepository.getString(REMOTE_CONFIG_KEY))
}
