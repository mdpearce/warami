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
 * @param communityId 
 * @param hidden 
 * @param auth 
 * @param reason 
 */
@JsonClass(generateAdapter = true)

data class HideCommunity (

    @Json(name = "community_id")
    val communityId: java.math.BigDecimal,

    @Json(name = "hidden")
    val hidden: kotlin.Boolean,

    @Json(name = "auth")
    val auth: kotlin.String,

    @Json(name = "reason")
    val reason: kotlin.String? = null

)

