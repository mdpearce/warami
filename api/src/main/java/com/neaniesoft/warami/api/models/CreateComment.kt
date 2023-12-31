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
 * @param content 
 * @param postId 
 * @param auth 
 * @param parentId 
 * @param languageId 
 * @param formId 
 */
@JsonClass(generateAdapter = true)

data class CreateComment (

    @Json(name = "content")
    val content: kotlin.String,

    @Json(name = "post_id")
    val postId: java.math.BigDecimal,

    @Json(name = "auth")
    val auth: kotlin.String,

    @Json(name = "parent_id")
    val parentId: java.math.BigDecimal? = null,

    @Json(name = "language_id")
    val languageId: java.math.BigDecimal? = null,

    @Json(name = "form_id")
    val formId: kotlin.String? = null

)

