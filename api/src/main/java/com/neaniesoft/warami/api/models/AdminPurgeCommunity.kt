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
 * @param adminPersonId 
 * @param `when` 
 * @param reason 
 */
@JsonClass(generateAdapter = true)

data class AdminPurgeCommunity (

    @Json(name = "id")
    val id: java.math.BigDecimal,

    @Json(name = "admin_person_id")
    val adminPersonId: java.math.BigDecimal,

    @Json(name = "when_")
    val `when`: kotlin.String,

    @Json(name = "reason")
    val reason: kotlin.String? = null

)

