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

import com.neaniesoft.warami.api.models.Community
import com.neaniesoft.warami.api.models.Person
import com.neaniesoft.warami.api.models.Post
import com.neaniesoft.warami.api.models.PostAggregates
import com.neaniesoft.warami.api.models.PostReport

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param postReport 
 * @param post 
 * @param community 
 * @param creator 
 * @param postCreator 
 * @param counts 
 * @param myVote 
 * @param resolver 
 */
@JsonClass(generateAdapter = true)

data class PostReportView (

    @Json(name = "post_report")
    val postReport: PostReport,

    @Json(name = "post")
    val post: Post,

    @Json(name = "community")
    val community: Community,

    @Json(name = "creator")
    val creator: Person,

    @Json(name = "post_creator")
    val postCreator: Person,

    @Json(name = "counts")
    val counts: PostAggregates,

    @Json(name = "my_vote")
    val myVote: java.math.BigDecimal? = null,

    @Json(name = "resolver")
    val resolver: Person? = null

)

