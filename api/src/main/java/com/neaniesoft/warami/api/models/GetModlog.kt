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

import com.neaniesoft.warami.api.models.ModlogActionType

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param modPersonId 
 * @param communityId 
 * @param page 
 * @param limit 
 * @param type 
 * @param otherPersonId 
 * @param auth 
 */
@JsonClass(generateAdapter = true)

data class GetModlog (

    @Json(name = "mod_person_id")
    val modPersonId: java.math.BigDecimal? = null,

    @Json(name = "community_id")
    val communityId: java.math.BigDecimal? = null,

    @Json(name = "page")
    val page: java.math.BigDecimal? = null,

    @Json(name = "limit")
    val limit: java.math.BigDecimal? = null,

    @Json(name = "type_")
    val type: ModlogActionType? = null,

    @Json(name = "other_person_id")
    val otherPersonId: java.math.BigDecimal? = null,

    @Json(name = "auth")
    val auth: kotlin.String? = null

)

