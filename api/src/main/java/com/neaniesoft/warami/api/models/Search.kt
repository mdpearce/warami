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
 * @param q 
 * @param communityId 
 * @param communityName 
 * @param creatorId 
 * @param page 
 * @param limit 
 * @param auth 
 */
@JsonClass(generateAdapter = true)

data class Search (

    @Json(name = "q")
    val q: kotlin.String,

    @Json(name = "community_id")
    val communityId: java.math.BigDecimal? = null,

    @Json(name = "community_name")
    val communityName: kotlin.String? = null,

    @Json(name = "creator_id")
    val creatorId: java.math.BigDecimal? = null,

    @Json(name = "page")
    val page: java.math.BigDecimal? = null,

    @Json(name = "limit")
    val limit: java.math.BigDecimal? = null,

    @Json(name = "auth")
    val auth: kotlin.String? = null

)

