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
 * @param auth 
 */
@JsonClass(generateAdapter = true)

data class ResolveObject (

    @Json(name = "q")
    val q: kotlin.String,

    @Json(name = "auth")
    val auth: kotlin.String

)

