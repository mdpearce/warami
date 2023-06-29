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

import com.neaniesoft.warami.api.models.CommentView
import com.neaniesoft.warami.api.models.CommunityView
import com.neaniesoft.warami.api.models.PersonView
import com.neaniesoft.warami.api.models.PostView
import com.neaniesoft.warami.api.models.SearchType

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param type 
 * @param comments 
 * @param posts 
 * @param communities 
 * @param users 
 */
@JsonClass(generateAdapter = true)

data class SearchResponse (

    @Json(name = "type_")
    val type: SearchType,

    @Json(name = "comments")
    val comments: kotlin.collections.List<CommentView>,

    @Json(name = "posts")
    val posts: kotlin.collections.List<PostView>,

    @Json(name = "communities")
    val communities: kotlin.collections.List<CommunityView>,

    @Json(name = "users")
    val users: kotlin.collections.List<PersonView>

)

