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

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param community 
 * @param follower 
 */
@JsonClass(generateAdapter = true)

data class CommunityFollowerView (

    @Json(name = "community")
    val community: Community,

    @Json(name = "follower")
    val follower: Person

)

