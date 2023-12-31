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

import com.neaniesoft.warami.api.models.ListingType
import com.neaniesoft.warami.api.models.SortType

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param type 
 * @param sort 
 * @param page 
 * @param limit 
 * @param auth 
 */
@JsonClass(generateAdapter = true)

data class ListCommunities (

    @Json(name = "type_")
    val type: ListingType? = null,

    @Json(name = "sort")
    val sort: SortType? = null,

    @Json(name = "page")
    val page: java.math.BigDecimal? = null,

    @Json(name = "limit")
    val limit: java.math.BigDecimal? = null,

    @Json(name = "auth")
    val auth: kotlin.String? = null

)

