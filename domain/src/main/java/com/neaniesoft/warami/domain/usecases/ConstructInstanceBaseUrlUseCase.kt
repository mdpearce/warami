package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.UriString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConstructInstanceBaseUrlUseCase
@Inject
constructor() {
    operator fun invoke(input: String): UriString {
        return if (input.startsWith("https://", ignoreCase = true) || input.startsWith("http://", ignoreCase = true)) {
            UriString(input)
        } else {
            UriString("https://$input")
        }
    }
}
