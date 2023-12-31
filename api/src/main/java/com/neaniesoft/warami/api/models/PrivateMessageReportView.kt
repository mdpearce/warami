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

import com.neaniesoft.warami.api.models.Person
import com.neaniesoft.warami.api.models.PrivateMessage
import com.neaniesoft.warami.api.models.PrivateMessageReport

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param privateMessageReport 
 * @param privateMessage 
 * @param privateMessageCreator 
 * @param creator 
 * @param resolver 
 */
@JsonClass(generateAdapter = true)

data class PrivateMessageReportView (

    @Json(name = "private_message_report")
    val privateMessageReport: PrivateMessageReport,

    @Json(name = "private_message")
    val privateMessage: PrivateMessage,

    @Json(name = "private_message_creator")
    val privateMessageCreator: Person,

    @Json(name = "creator")
    val creator: Person,

    @Json(name = "resolver")
    val resolver: Person? = null

)

