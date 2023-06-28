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
 * @param recipientId 
 * @param commentId 
 * @param published 
 */
@JsonClass(generateAdapter = true)

data class CommentReply (

    @Json(name = "id")
    val id: java.math.BigDecimal,

    @Json(name = "recipient_id")
    val recipientId: java.math.BigDecimal,

    @Json(name = "comment_id")
    val commentId: java.math.BigDecimal,

    @Json(name = "published")
    val published: kotlin.String

)

