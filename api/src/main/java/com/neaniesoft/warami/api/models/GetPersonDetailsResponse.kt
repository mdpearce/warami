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
import com.neaniesoft.warami.api.models.CommunityModeratorView
import com.neaniesoft.warami.api.models.PersonView
import com.neaniesoft.warami.api.models.PostView

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param personView 
 * @param comments 
 * @param posts 
 * @param moderates 
 */
@JsonClass(generateAdapter = true)

data class GetPersonDetailsResponse (

    @Json(name = "person_view")
    val personView: PersonView,

    @Json(name = "comments")
    val comments: kotlin.collections.List<CommentView>,

    @Json(name = "posts")
    val posts: kotlin.collections.List<PostView>,

    @Json(name = "moderates")
    val moderates: kotlin.collections.List<CommunityModeratorView>

)

