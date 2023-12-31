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
 * @param name 
 * @param title 
 * @param auth 
 * @param description 
 * @param icon 
 * @param banner 
 * @param nsfw 
 * @param postingRestrictedToMods 
 * @param discussionLanguages 
 */
@JsonClass(generateAdapter = true)

data class CreateCommunity (

    @Json(name = "name")
    val name: kotlin.String,

    @Json(name = "title")
    val title: kotlin.String,

    @Json(name = "auth")
    val auth: kotlin.String,

    @Json(name = "description")
    val description: kotlin.String? = null,

    @Json(name = "icon")
    val icon: kotlin.String? = null,

    @Json(name = "banner")
    val banner: kotlin.String? = null,

    @Json(name = "nsfw")
    val nsfw: kotlin.Boolean? = null,

    @Json(name = "posting_restricted_to_mods")
    val postingRestrictedToMods: kotlin.Boolean? = null,

    @Json(name = "discussion_languages")
    val discussionLanguages: kotlin.collections.List<java.math.BigDecimal>? = null

)

