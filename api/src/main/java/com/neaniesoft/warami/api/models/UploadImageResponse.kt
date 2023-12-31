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

import com.neaniesoft.warami.api.models.ImageFile

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param msg 
 * @param files 
 * @param url 
 * @param deleteUrl 
 */
@JsonClass(generateAdapter = true)

data class UploadImageResponse (

    @Json(name = "msg")
    val msg: kotlin.String,

    @Json(name = "files")
    val files: kotlin.collections.List<ImageFile>? = null,

    @Json(name = "url")
    val url: kotlin.String? = null,

    @Json(name = "delete_url")
    val deleteUrl: kotlin.String? = null

)

