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
 * @param personId 
 * @param ban 
 * @param auth 
 * @param removeData 
 * @param reason 
 * @param expires 
 */
@JsonClass(generateAdapter = true)

data class BanFromCommunity (

    @Json(name = "community_id")
    val communityId: java.math.BigDecimal,

    @Json(name = "person_id")
    val personId: java.math.BigDecimal,

    @Json(name = "ban")
    val ban: kotlin.Boolean,

    @Json(name = "auth")
    val auth: kotlin.String,

    @Json(name = "remove_data")
    val removeData: kotlin.Boolean? = null,

    @Json(name = "reason")
    val reason: kotlin.String? = null,

    @Json(name = "expires")
    val expires: java.math.BigDecimal? = null

)

