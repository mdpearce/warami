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
 * @param postId 
 * @param `when` 
 * @param reason 
 */
@JsonClass(generateAdapter = true)

data class ModRemovePost (

    @Json(name = "id")
    val id: java.math.BigDecimal,

    @Json(name = "mod_person_id")
    val modPersonId: java.math.BigDecimal,

    @Json(name = "post_id")
    val postId: java.math.BigDecimal,

    @Json(name = "when_")
    val `when`: kotlin.String,

    @Json(name = "reason")
    val reason: kotlin.String? = null

)

