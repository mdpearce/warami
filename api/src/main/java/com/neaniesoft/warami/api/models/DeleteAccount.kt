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
 * @param password 
 * @param auth 
 */
@JsonClass(generateAdapter = true)

data class DeleteAccount (

    @Json(name = "password")
    val password: kotlin.String,

    @Json(name = "auth")
    val auth: kotlin.String

)

