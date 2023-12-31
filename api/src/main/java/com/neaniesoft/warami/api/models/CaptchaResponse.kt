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
 * @param png 
 * @param wav 
 * @param uuid 
 */
@JsonClass(generateAdapter = true)

data class CaptchaResponse (

    @Json(name = "png")
    val png: kotlin.String,

    @Json(name = "wav")
    val wav: kotlin.String,

    @Json(name = "uuid")
    val uuid: kotlin.String

)

