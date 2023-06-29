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
 * @param removed 
 * @param auth 
 * @param reason 
 */
@JsonClass(generateAdapter = true)

data class RemoveComment (

    @Json(name = "comment_id")
    val commentId: java.math.BigDecimal,

    @Json(name = "removed")
    val removed: kotlin.Boolean,

    @Json(name = "auth")
    val auth: kotlin.String,

    @Json(name = "reason")
    val reason: kotlin.String? = null

)

