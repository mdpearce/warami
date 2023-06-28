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

import com.neaniesoft.warami.api.models.ModAdd
import com.neaniesoft.warami.api.models.Person

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param modAdd 
 * @param moddedPerson 
 * @param moderator 
 */
@JsonClass(generateAdapter = true)

data class ModAddView (

    @Json(name = "mod_add")
    val modAdd: ModAdd,

    @Json(name = "modded_person")
    val moddedPerson: Person,

    @Json(name = "moderator")
    val moderator: Person? = null

)

