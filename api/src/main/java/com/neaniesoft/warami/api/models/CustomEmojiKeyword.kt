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
 * @param customEmojiId 
 * @param keyword 
 */
@JsonClass(generateAdapter = true)

data class CustomEmojiKeyword (

    @Json(name = "id")
    val id: java.math.BigDecimal,

    @Json(name = "custom_emoji_id")
    val customEmojiId: java.math.BigDecimal,

    @Json(name = "keyword")
    val keyword: kotlin.String

)

