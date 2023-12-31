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

import com.neaniesoft.warami.api.models.PostOrCommentIdOneOf
import com.neaniesoft.warami.api.models.PostOrCommentIdOneOf1

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param post 
 * @param comment 
 */
@JsonClass(generateAdapter = true)

data class PostOrCommentId (

    @Json(name = "Post")
    val post: java.math.BigDecimal,

    @Json(name = "Comment")
    val comment: java.math.BigDecimal

)

