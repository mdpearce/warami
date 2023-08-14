package com.neaniesoft.warami.data.repositories

import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.data.di.IODispatcher
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class ExternalUrlRepository @Inject constructor(
    private val client: OkHttpClient,
    private val ioDispatcher: IODispatcher,
) {
    suspend fun isImageUrl(uriString: UriString?): Boolean = withContext(ioDispatcher) {
        suspendCancellableCoroutine { continuation ->
            if (uriString == null) {
                continuation.resume(false)
            } else {
                val request = Request.Builder()
                    .url(uriString.value)
                    .head() // Just get the headers, not the full content
                    .build()

                val call = client.newCall(request)

                call.enqueue(
                    object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            continuation.resumeWithException(e)
                        }

                        override fun onResponse(call: Call, response: Response) {
                            val contentType = response.header("Content-Type")
                            continuation.resume(contentType?.startsWith("image/", ignoreCase = true) == true)
                        }
                    },
                )

                continuation.invokeOnCancellation {
                    try {
                        call.cancel()
                    } catch (_: Exception) {
                        // Ignore cancellation exception
                    }
                }
            }
        }
    }
}
