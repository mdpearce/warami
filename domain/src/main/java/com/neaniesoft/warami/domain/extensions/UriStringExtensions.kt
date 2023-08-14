package com.neaniesoft.warami.domain.extensions

import android.webkit.MimeTypeMap
import com.neaniesoft.warami.common.models.UriString

fun UriString?.isImageUrl(): Boolean {
    return if (this == null) {
        false
    } else {
        val mimeType = MimeTypeMap.getFileExtensionFromUrl(this.value)?.let {
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(it)
        }
        mimeType?.startsWith("image/", ignoreCase = true) == true
    }
}
