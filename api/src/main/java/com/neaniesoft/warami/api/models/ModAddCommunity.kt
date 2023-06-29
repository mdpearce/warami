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
 * @param id 
 * @param modPersonId 
 * @param otherPersonId 
 * @param communityId 
 * @param removed 
 * @param `when` 
 */
@JsonClass(generateAdapter = true)

data class ModAddCommunity (

    @Json(name = "id")
    val id: java.math.BigDecimal,

    @Json(name = "mod_person_id")
    val modPersonId: java.math.BigDecimal,

    @Json(name = "other_person_id")
    val otherPersonId: java.math.BigDecimal,

    @Json(name = "community_id")
    val communityId: java.math.BigDecimal,

    @Json(name = "removed")
    val removed: kotlin.Boolean,

    @Json(name = "when_")
    val `when`: kotlin.String

)

