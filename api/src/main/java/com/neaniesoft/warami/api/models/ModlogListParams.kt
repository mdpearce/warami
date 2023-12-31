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
 * @param hideModlogNames 
 * @param communityId 
 * @param modPersonId 
 * @param otherPersonId 
 * @param page 
 * @param limit 
 */
@JsonClass(generateAdapter = true)

data class ModlogListParams (

    @Json(name = "hide_modlog_names")
    val hideModlogNames: kotlin.Boolean,

    @Json(name = "community_id")
    val communityId: java.math.BigDecimal? = null,

    @Json(name = "mod_person_id")
    val modPersonId: java.math.BigDecimal? = null,

    @Json(name = "other_person_id")
    val otherPersonId: java.math.BigDecimal? = null,

    @Json(name = "page")
    val page: java.math.BigDecimal? = null,

    @Json(name = "limit")
    val limit: java.math.BigDecimal? = null

)

