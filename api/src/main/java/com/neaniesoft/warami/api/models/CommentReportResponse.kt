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

import com.neaniesoft.warami.api.models.CommentReportView

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param commentReportView 
 */
@JsonClass(generateAdapter = true)

data class CommentReportResponse (

    @Json(name = "comment_report_view")
    val commentReportView: CommentReportView

)

