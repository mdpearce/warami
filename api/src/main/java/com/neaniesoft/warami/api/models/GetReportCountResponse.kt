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
 * @param commentReports 
 * @param postReports 
 * @param communityId 
 * @param privateMessageReports 
 */
@JsonClass(generateAdapter = true)

data class GetReportCountResponse (

    @Json(name = "comment_reports")
    val commentReports: java.math.BigDecimal,

    @Json(name = "post_reports")
    val postReports: java.math.BigDecimal,

    @Json(name = "community_id")
    val communityId: java.math.BigDecimal? = null,

    @Json(name = "private_message_reports")
    val privateMessageReports: java.math.BigDecimal? = null

)

