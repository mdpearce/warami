package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.domain.di.DomainScope
import me.tatarka.inject.annotations.Inject

@DomainScope
@Inject
class ConstructInstanceBaseUrlUseCase {
    operator fun invoke(input: String): UriString {
        return if (input.startsWith("https://", ignoreCase = true) || input.startsWith("http://", ignoreCase = true)) {
            UriString(input)
        } else {
            UriString("https://$input")
        }
    }
}
