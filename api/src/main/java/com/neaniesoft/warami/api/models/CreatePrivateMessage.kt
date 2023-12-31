/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.neaniesoft.warami.api.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param content 
 * @param recipientId 
 * @param auth 
 */
@JsonClass(generateAdapter = true)

data class CreatePrivateMessage (

    @Json(name = "content")
    val content: kotlin.String,

    @Json(name = "recipient_id")
    val recipientId: java.math.BigDecimal,

    @Json(name = "auth")
    val auth: kotlin.String

)

