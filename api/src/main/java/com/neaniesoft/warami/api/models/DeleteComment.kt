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
 * @param commentId 
 * @param auth 
 */
@JsonClass(generateAdapter = true)

data class DeleteComment (

    @Json(name = "comment_id")
    val commentId: java.math.BigDecimal,

    @Json(name = "auth")
    val auth: kotlin.String

)

