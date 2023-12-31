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
 * @param privateMessageId 
 * @param read 
 * @param auth 
 */
@JsonClass(generateAdapter = true)

data class MarkPrivateMessageAsRead (

    @Json(name = "private_message_id")
    val privateMessageId: java.math.BigDecimal,

    @Json(name = "read")
    val read: kotlin.Boolean,

    @Json(name = "auth")
    val auth: kotlin.String

)

