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

import com.neaniesoft.warami.api.models.PostFeatureType

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param postId 
 * @param featured 
 * @param featureType 
 * @param auth 
 */
@JsonClass(generateAdapter = true)

data class FeaturePost (

    @Json(name = "post_id")
    val postId: java.math.BigDecimal,

    @Json(name = "featured")
    val featured: kotlin.Boolean,

    @Json(name = "feature_type")
    val featureType: PostFeatureType,

    @Json(name = "auth")
    val auth: kotlin.String

)

