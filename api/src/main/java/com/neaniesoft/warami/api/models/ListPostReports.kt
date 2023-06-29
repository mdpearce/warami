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
 * @param auth 
 * @param page 
 * @param limit 
 * @param unresolvedOnly 
 * @param communityId 
 */
@JsonClass(generateAdapter = true)

data class ListPostReports (

    @Json(name = "auth")
    val auth: kotlin.String,

    @Json(name = "page")
    val page: java.math.BigDecimal? = null,

    @Json(name = "limit")
    val limit: java.math.BigDecimal? = null,

    @Json(name = "unresolved_only")
    val unresolvedOnly: kotlin.Boolean? = null,

    @Json(name = "community_id")
    val communityId: java.math.BigDecimal? = null

)

