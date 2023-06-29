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
 * @param registrationCreated 
 * @param verifyEmailSent 
 * @param jwt 
 */
@JsonClass(generateAdapter = true)

data class LoginResponse (

    @Json(name = "registration_created")
    val registrationCreated: kotlin.Boolean,

    @Json(name = "verify_email_sent")
    val verifyEmailSent: kotlin.Boolean,

    @Json(name = "jwt")
    val jwt: kotlin.String? = null

)

