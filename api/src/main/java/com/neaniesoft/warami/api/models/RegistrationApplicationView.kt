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

import com.neaniesoft.warami.api.models.LocalUser
import com.neaniesoft.warami.api.models.Person
import com.neaniesoft.warami.api.models.RegistrationApplication

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param registrationApplication 
 * @param creatorLocalUser 
 * @param creator 
 * @param admin 
 */
@JsonClass(generateAdapter = true)

data class RegistrationApplicationView (

    @Json(name = "registration_application")
    val registrationApplication: RegistrationApplication,

    @Json(name = "creator_local_user")
    val creatorLocalUser: LocalUser,

    @Json(name = "creator")
    val creator: Person,

    @Json(name = "admin")
    val admin: Person? = null

)

