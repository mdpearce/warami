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

import com.neaniesoft.warami.api.models.CommunityView

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param communityView 
 * @param discussionLanguages 
 */
@JsonClass(generateAdapter = true)

data class CommunityResponse (

    @Json(name = "community_view")
    val communityView: CommunityView,

    @Json(name = "discussion_languages")
    val discussionLanguages: kotlin.collections.List<java.math.BigDecimal>

)

