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

import com.neaniesoft.warami.api.models.Comment
import com.neaniesoft.warami.api.models.CommentAggregates
import com.neaniesoft.warami.api.models.CommentReport
import com.neaniesoft.warami.api.models.Community
import com.neaniesoft.warami.api.models.Person
import com.neaniesoft.warami.api.models.Post

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param commentReport 
 * @param comment 
 * @param post 
 * @param community 
 * @param creator 
 * @param commentCreator 
 * @param counts 
 * @param creatorBannedFromCommunity 
 * @param myVote 
 * @param resolver 
 */
@JsonClass(generateAdapter = true)

data class CommentReportView (

    @Json(name = "comment_report")
    val commentReport: CommentReport,

    @Json(name = "comment")
    val comment: Comment,

    @Json(name = "post")
    val post: Post,

    @Json(name = "community")
    val community: Community,

    @Json(name = "creator")
    val creator: Person,

    @Json(name = "comment_creator")
    val commentCreator: Person,

    @Json(name = "counts")
    val counts: CommentAggregates,

    @Json(name = "creator_banned_from_community")
    val creatorBannedFromCommunity: kotlin.Boolean,

    @Json(name = "my_vote")
    val myVote: java.math.BigDecimal? = null,

    @Json(name = "resolver")
    val resolver: Person? = null

)

