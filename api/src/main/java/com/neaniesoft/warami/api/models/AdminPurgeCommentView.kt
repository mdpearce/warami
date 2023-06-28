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

import com.neaniesoft.warami.api.models.AdminPurgeComment
import com.neaniesoft.warami.api.models.Person
import com.neaniesoft.warami.api.models.Post

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param adminPurgeComment 
 * @param post 
 * @param admin 
 */
@JsonClass(generateAdapter = true)

data class AdminPurgeCommentView (

    @Json(name = "admin_purge_comment")
    val adminPurgeComment: AdminPurgeComment,

    @Json(name = "post")
    val post: Post,

    @Json(name = "admin")
    val admin: Person? = null

)

