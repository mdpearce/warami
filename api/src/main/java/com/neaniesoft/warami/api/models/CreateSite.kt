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

import com.neaniesoft.warami.api.models.ListingType
import com.neaniesoft.warami.api.models.RegistrationMode

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param name 
 * @param auth 
 * @param sidebar 
 * @param description 
 * @param icon 
 * @param banner 
 * @param enableDownvotes 
 * @param enableNsfw 
 * @param communityCreationAdminOnly 
 * @param requireEmailVerification 
 * @param applicationQuestion 
 * @param privateInstance 
 * @param defaultTheme 
 * @param defaultPostListingType 
 * @param legalInformation 
 * @param applicationEmailAdmins 
 * @param hideModlogModNames 
 * @param discussionLanguages 
 * @param slurFilterRegex 
 * @param actorNameMaxLength 
 * @param rateLimitMessage 
 * @param rateLimitMessagePerSecond 
 * @param rateLimitPost 
 * @param rateLimitPostPerSecond 
 * @param rateLimitRegister 
 * @param rateLimitRegisterPerSecond 
 * @param rateLimitImage 
 * @param rateLimitImagePerSecond 
 * @param rateLimitComment 
 * @param rateLimitCommentPerSecond 
 * @param rateLimitSearch 
 * @param rateLimitSearchPerSecond 
 * @param federationEnabled 
 * @param federationDebug 
 * @param captchaEnabled 
 * @param captchaDifficulty 
 * @param allowedInstances 
 * @param blockedInstances 
 * @param taglines 
 * @param registrationMode 
 */
@JsonClass(generateAdapter = true)

data class CreateSite (

    @Json(name = "name")
    val name: kotlin.String,

    @Json(name = "auth")
    val auth: kotlin.String,

    @Json(name = "sidebar")
    val sidebar: kotlin.String? = null,

    @Json(name = "description")
    val description: kotlin.String? = null,

    @Json(name = "icon")
    val icon: kotlin.String? = null,

    @Json(name = "banner")
    val banner: kotlin.String? = null,

    @Json(name = "enable_downvotes")
    val enableDownvotes: kotlin.Boolean? = null,

    @Json(name = "enable_nsfw")
    val enableNsfw: kotlin.Boolean? = null,

    @Json(name = "community_creation_admin_only")
    val communityCreationAdminOnly: kotlin.Boolean? = null,

    @Json(name = "require_email_verification")
    val requireEmailVerification: kotlin.Boolean? = null,

    @Json(name = "application_question")
    val applicationQuestion: kotlin.String? = null,

    @Json(name = "private_instance")
    val privateInstance: kotlin.Boolean? = null,

    @Json(name = "default_theme")
    val defaultTheme: kotlin.String? = null,

    @Json(name = "default_post_listing_type")
    val defaultPostListingType: ListingType? = null,

    @Json(name = "legal_information")
    val legalInformation: kotlin.String? = null,

    @Json(name = "application_email_admins")
    val applicationEmailAdmins: kotlin.Boolean? = null,

    @Json(name = "hide_modlog_mod_names")
    val hideModlogModNames: kotlin.Boolean? = null,

    @Json(name = "discussion_languages")
    val discussionLanguages: kotlin.collections.List<java.math.BigDecimal>? = null,

    @Json(name = "slur_filter_regex")
    val slurFilterRegex: kotlin.String? = null,

    @Json(name = "actor_name_max_length")
    val actorNameMaxLength: java.math.BigDecimal? = null,

    @Json(name = "rate_limit_message")
    val rateLimitMessage: java.math.BigDecimal? = null,

    @Json(name = "rate_limit_message_per_second")
    val rateLimitMessagePerSecond: java.math.BigDecimal? = null,

    @Json(name = "rate_limit_post")
    val rateLimitPost: java.math.BigDecimal? = null,

    @Json(name = "rate_limit_post_per_second")
    val rateLimitPostPerSecond: java.math.BigDecimal? = null,

    @Json(name = "rate_limit_register")
    val rateLimitRegister: java.math.BigDecimal? = null,

    @Json(name = "rate_limit_register_per_second")
    val rateLimitRegisterPerSecond: java.math.BigDecimal? = null,

    @Json(name = "rate_limit_image")
    val rateLimitImage: java.math.BigDecimal? = null,

    @Json(name = "rate_limit_image_per_second")
    val rateLimitImagePerSecond: java.math.BigDecimal? = null,

    @Json(name = "rate_limit_comment")
    val rateLimitComment: java.math.BigDecimal? = null,

    @Json(name = "rate_limit_comment_per_second")
    val rateLimitCommentPerSecond: java.math.BigDecimal? = null,

    @Json(name = "rate_limit_search")
    val rateLimitSearch: java.math.BigDecimal? = null,

    @Json(name = "rate_limit_search_per_second")
    val rateLimitSearchPerSecond: java.math.BigDecimal? = null,

    @Json(name = "federation_enabled")
    val federationEnabled: kotlin.Boolean? = null,

    @Json(name = "federation_debug")
    val federationDebug: kotlin.Boolean? = null,

    @Json(name = "captcha_enabled")
    val captchaEnabled: kotlin.Boolean? = null,

    @Json(name = "captcha_difficulty")
    val captchaDifficulty: kotlin.String? = null,

    @Json(name = "allowed_instances")
    val allowedInstances: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "blocked_instances")
    val blockedInstances: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "taglines")
    val taglines: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "registration_mode")
    val registrationMode: RegistrationMode? = null

)

