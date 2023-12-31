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
 * @param creatorId 
 * @param postId 
 * @param originalPostName 
 * @param reason 
 * @param resolved 
 * @param published 
 * @param originalPostUrl 
 * @param originalPostBody 
 * @param resolverId 
 * @param updated 
 */
@JsonClass(generateAdapter = true)

data class PostReport (

    @Json(name = "id")
    val id: java.math.BigDecimal,

    @Json(name = "creator_id")
    val creatorId: java.math.BigDecimal,

    @Json(name = "post_id")
    val postId: java.math.BigDecimal,

    @Json(name = "original_post_name")
    val originalPostName: kotlin.String,

    @Json(name = "reason")
    val reason: kotlin.String,

    @Json(name = "resolved")
    val resolved: kotlin.Boolean,

    @Json(name = "published")
    val published: kotlin.String,

    @Json(name = "original_post_url")
    val originalPostUrl: kotlin.String? = null,

    @Json(name = "original_post_body")
    val originalPostBody: kotlin.String? = null,

    @Json(name = "resolver_id")
    val resolverId: java.math.BigDecimal? = null,

    @Json(name = "updated")
    val updated: kotlin.String? = null

)

