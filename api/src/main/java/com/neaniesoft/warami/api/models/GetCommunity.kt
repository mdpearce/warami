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
 * @param name 
 * @param auth 
 */
@JsonClass(generateAdapter = true)

data class GetCommunity (

    @Json(name = "id")
    val id: java.math.BigDecimal? = null,

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "auth")
    val auth: kotlin.String? = null

)

