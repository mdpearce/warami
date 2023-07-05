package com.neaniesoft.warami.api.apis

import com.neaniesoft.warami.api.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.squareup.moshi.Json

import com.neaniesoft.warami.api.models.AddAdminResponse
import com.neaniesoft.warami.api.models.AddModToCommunityResponse
import com.neaniesoft.warami.api.models.BanFromCommunityResponse
import com.neaniesoft.warami.api.models.BanPersonResponse
import com.neaniesoft.warami.api.models.BannedPersonsResponse
import com.neaniesoft.warami.api.models.BlockCommunityResponse
import com.neaniesoft.warami.api.models.BlockPersonResponse
import com.neaniesoft.warami.api.models.CommentReplyResponse
import com.neaniesoft.warami.api.models.CommentReportResponse
import com.neaniesoft.warami.api.models.CommentResponse
import com.neaniesoft.warami.api.models.CommentSortType
import com.neaniesoft.warami.api.models.CommunityResponse
import com.neaniesoft.warami.api.models.CustomEmojiResponse
import com.neaniesoft.warami.api.models.DeleteCustomEmojiResponse
import com.neaniesoft.warami.api.models.GetCaptchaResponse
import com.neaniesoft.warami.api.models.GetCommentsResponse
import com.neaniesoft.warami.api.models.GetCommunityResponse
import com.neaniesoft.warami.api.models.GetFederatedInstancesResponse
import com.neaniesoft.warami.api.models.GetModlogResponse
import com.neaniesoft.warami.api.models.GetPersonDetailsResponse
import com.neaniesoft.warami.api.models.GetPersonMentionsResponse
import com.neaniesoft.warami.api.models.GetPostsResponse
import com.neaniesoft.warami.api.models.GetRepliesResponse
import com.neaniesoft.warami.api.models.GetReportCountResponse
import com.neaniesoft.warami.api.models.GetSiteMetadataResponse
import com.neaniesoft.warami.api.models.GetSiteResponse
import com.neaniesoft.warami.api.models.GetUnreadCountResponse
import com.neaniesoft.warami.api.models.GetUnreadRegistrationApplicationCountResponse
import com.neaniesoft.warami.api.models.ListCommentReportsResponse
import com.neaniesoft.warami.api.models.ListCommunitiesResponse
import com.neaniesoft.warami.api.models.ListPostReportsResponse
import com.neaniesoft.warami.api.models.ListPrivateMessageReportsResponse
import com.neaniesoft.warami.api.models.ListRegistrationApplicationsResponse
import com.neaniesoft.warami.api.models.ListingType
import com.neaniesoft.warami.api.models.LoginResponse
import com.neaniesoft.warami.api.models.ModlogActionType
import com.neaniesoft.warami.api.models.PersonMentionResponse
import com.neaniesoft.warami.api.models.PostFeatureType
import com.neaniesoft.warami.api.models.PostReportResponse
import com.neaniesoft.warami.api.models.PostResponse
import com.neaniesoft.warami.api.models.PrivateMessageReportResponse
import com.neaniesoft.warami.api.models.PrivateMessageResponse
import com.neaniesoft.warami.api.models.PrivateMessagesResponse
import com.neaniesoft.warami.api.models.PurgeItemResponse
import com.neaniesoft.warami.api.models.RegistrationApplicationResponse
import com.neaniesoft.warami.api.models.RegistrationMode
import com.neaniesoft.warami.api.models.ResolveObjectResponse
import com.neaniesoft.warami.api.models.SearchResponse
import com.neaniesoft.warami.api.models.SearchType
import com.neaniesoft.warami.api.models.SiteResponse
import com.neaniesoft.warami.api.models.SortType

interface DefaultApi {
    /**
     * Add an admin to your site.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param personId 
     * @param added 
     * @param auth 
     * @return [AddAdminResponse]
     */
    @POST("admin/add")
    suspend fun addAdmin(@Query("person_id") personId: java.math.BigDecimal, @Query("added") added: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<AddAdminResponse>

    /**
     * Add a moderator to your community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param personId 
     * @param added 
     * @param auth 
     * @return [AddModToCommunityResponse]
     */
    @POST("community/mod")
    suspend fun addModToCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("person_id") personId: java.math.BigDecimal, @Query("added") added: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<AddModToCommunityResponse>

    /**
     * Approve a registration application
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param approve 
     * @param auth 
     * @param denyReason  (optional)
     * @return [RegistrationApplicationResponse]
     */
    @PUT("admin/registration_application/approve")
    suspend fun approveRegistrationApplication(@Query("id") id: java.math.BigDecimal, @Query("approve") approve: kotlin.Boolean, @Query("auth") auth: kotlin.String, @Query("deny_reason") denyReason: kotlin.String? = null): Response<RegistrationApplicationResponse>

    /**
     * Ban a user from a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param personId 
     * @param ban 
     * @param auth 
     * @param removeData  (optional)
     * @param reason  (optional)
     * @param expires  (optional)
     * @return [BanFromCommunityResponse]
     */
    @POST("community/ban_user")
    suspend fun banFromCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("person_id") personId: java.math.BigDecimal, @Query("ban") ban: kotlin.Boolean, @Query("auth") auth: kotlin.String, @Query("remove_data") removeData: kotlin.Boolean? = null, @Query("reason") reason: kotlin.String? = null, @Query("expires") expires: java.math.BigDecimal? = null): Response<BanFromCommunityResponse>

    /**
     * Ban a person from your site.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param personId 
     * @param ban 
     * @param auth 
     * @param removeData  (optional)
     * @param reason  (optional)
     * @param expires  (optional)
     * @return [BanPersonResponse]
     */
    @POST("user/ban")
    suspend fun banPerson(@Query("person_id") personId: java.math.BigDecimal, @Query("ban") ban: kotlin.Boolean, @Query("auth") auth: kotlin.String, @Query("remove_data") removeData: kotlin.Boolean? = null, @Query("reason") reason: kotlin.String? = null, @Query("expires") expires: java.math.BigDecimal? = null): Response<BanPersonResponse>

    /**
     * Block a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param block 
     * @param auth 
     * @return [BlockCommunityResponse]
     */
    @POST("community/block")
    suspend fun blockCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("block") block: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<BlockCommunityResponse>

    /**
     * Block a person.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param personId 
     * @param block 
     * @param auth 
     * @return [BlockPersonResponse]
     */
    @POST("user/block")
    suspend fun blockPerson(@Query("person_id") personId: java.math.BigDecimal, @Query("block") block: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<BlockPersonResponse>

    /**
     * Change your user password.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param newPassword 
     * @param newPasswordVerify 
     * @param oldPassword 
     * @param auth 
     * @return [LoginResponse]
     */
    @PUT("user/change_password")
    suspend fun changePassword(@Query("new_password") newPassword: kotlin.String, @Query("new_password_verify") newPasswordVerify: kotlin.String, @Query("old_password") oldPassword: kotlin.String, @Query("auth") auth: kotlin.String): Response<LoginResponse>

    /**
     * Report a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentId 
     * @param reason 
     * @param auth 
     * @return [CommentReportResponse]
     */
    @POST("comment/report")
    suspend fun createCommentReport(@Query("comment_id") commentId: java.math.BigDecimal, @Query("reason") reason: kotlin.String, @Query("auth") auth: kotlin.String): Response<CommentReportResponse>

    /**
     * Report a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param reason 
     * @param auth 
     * @return [PostReportResponse]
     */
    @POST("post/report")
    suspend fun createPostReport(@Query("post_id") postId: java.math.BigDecimal, @Query("reason") reason: kotlin.String, @Query("auth") auth: kotlin.String): Response<PostReportResponse>

    /**
     * Create a report for a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param privateMessageId 
     * @param reason 
     * @param auth 
     * @return [PrivateMessageReportResponse]
     */
    @POST("private_message/report")
    suspend fun createPrivateMessageReport(@Query("private_message_id") privateMessageId: java.math.BigDecimal, @Query("reason") reason: kotlin.String, @Query("auth") auth: kotlin.String): Response<PrivateMessageReportResponse>

    /**
     * Delete your account.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param password 
     * @param auth 
     * @return [kotlin.Any]
     */
    @POST("user/delete_account")
    suspend fun deleteAccount(@Query("password") password: kotlin.String, @Query("auth") auth: kotlin.String): Response<kotlin.Any>

    /**
     * Delete a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentId 
     * @param deleted 
     * @param auth 
     * @return [CommentResponse]
     */
    @POST("comment/delete")
    suspend fun deleteComment(@Query("comment_id") commentId: java.math.BigDecimal, @Query("deleted") deleted: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<CommentResponse>

    /**
     * Delete a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param deleted 
     * @param auth 
     * @return [CommunityResponse]
     */
    @POST("community/delete")
    suspend fun deleteCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("deleted") deleted: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<CommunityResponse>

    /**
     * Delete a custom emoji
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param auth 
     * @return [DeleteCustomEmojiResponse]
     */
    @POST("custom_emoji/delete")
    suspend fun deleteCustomEmoji(@Query("id") id: java.math.BigDecimal, @Query("auth") auth: kotlin.String): Response<DeleteCustomEmojiResponse>

    /**
     * Delete a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param deleted 
     * @param auth 
     * @return [PostResponse]
     */
    @POST("post/delete")
    suspend fun deletePost(@Query("post_id") postId: java.math.BigDecimal, @Query("deleted") deleted: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<PostResponse>

    /**
     * Delete a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param privateMessageId 
     * @param deleted 
     * @param auth 
     * @return [PrivateMessageResponse]
     */
    @POST("private_message/delete")
    suspend fun deletePrivateMessage(@Query("private_message_id") privateMessageId: java.math.BigDecimal, @Query("deleted") deleted: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<PrivateMessageResponse>

    /**
     * Distinguishes a comment (speak as moderator)
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentId 
     * @param distinguished 
     * @param auth 
     * @return [CommentResponse]
     */
    @POST("comment/distinguish")
    suspend fun distinguishComment(@Query("comment_id") commentId: java.math.BigDecimal, @Query("distinguished") distinguished: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<CommentResponse>

    /**
     * Edit a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param auth 
     * @param title  (optional)
     * @param description  (optional)
     * @param icon  (optional)
     * @param banner  (optional)
     * @param nsfw  (optional)
     * @param postingRestrictedToMods  (optional)
     * @param discussionLanguages  (optional)
     * @return [CommunityResponse]
     */
    @PUT("community")
    suspend fun editCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("auth") auth: kotlin.String, @Query("title") title: kotlin.String? = null, @Query("description") description: kotlin.String? = null, @Query("icon") icon: kotlin.String? = null, @Query("banner") banner: kotlin.String? = null, @Query("nsfw") nsfw: kotlin.Boolean? = null, @Query("posting_restricted_to_mods") postingRestrictedToMods: kotlin.Boolean? = null, @Query("discussion_languages") discussionLanguages: kotlin.collections.List<java.math.BigDecimal>? = null): Response<CommunityResponse>

    /**
     * Edit an existing custom emoji
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param category 
     * @param imageUrl 
     * @param altText 
     * @param keywords 
     * @param auth 
     * @return [CustomEmojiResponse]
     */
    @PUT("custom_emoji")
    suspend fun editCustomEmoji(@Query("id") id: java.math.BigDecimal, @Query("category") category: kotlin.String, @Query("image_url") imageUrl: kotlin.String, @Query("alt_text") altText: kotlin.String, @Query("keywords") keywords: kotlin.collections.List<kotlin.String>, @Query("auth") auth: kotlin.String): Response<CustomEmojiResponse>

    /**
     * Edit a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param auth 
     * @param name  (optional)
     * @param url  (optional)
     * @param body  (optional)
     * @param nsfw  (optional)
     * @param languageId  (optional)
     * @return [PostResponse]
     */
    @PUT("post")
    suspend fun editPost(@Query("post_id") postId: java.math.BigDecimal, @Query("auth") auth: kotlin.String, @Query("name") name: kotlin.String? = null, @Query("url") url: kotlin.String? = null, @Query("body") body: kotlin.String? = null, @Query("nsfw") nsfw: kotlin.Boolean? = null, @Query("language_id") languageId: java.math.BigDecimal? = null): Response<PostResponse>

    /**
     * Edit a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param privateMessageId 
     * @param content 
     * @param auth 
     * @return [PrivateMessageResponse]
     */
    @PUT("private_message")
    suspend fun editPrivateMessage(@Query("private_message_id") privateMessageId: java.math.BigDecimal, @Query("content") content: kotlin.String, @Query("auth") auth: kotlin.String): Response<PrivateMessageResponse>

    /**
     * Edit your site.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @param name  (optional)
     * @param sidebar  (optional)
     * @param description  (optional)
     * @param icon  (optional)
     * @param banner  (optional)
     * @param enableDownvotes  (optional)
     * @param enableNsfw  (optional)
     * @param communityCreationAdminOnly  (optional)
     * @param requireEmailVerification  (optional)
     * @param applicationQuestion  (optional)
     * @param privateInstance  (optional)
     * @param defaultTheme  (optional)
     * @param defaultPostListingType  (optional)
     * @param legalInformation  (optional)
     * @param applicationEmailAdmins  (optional)
     * @param hideModlogModNames  (optional)
     * @param discussionLanguages  (optional)
     * @param slurFilterRegex  (optional)
     * @param actorNameMaxLength  (optional)
     * @param rateLimitMessage  (optional)
     * @param rateLimitMessagePerSecond  (optional)
     * @param rateLimitPost  (optional)
     * @param rateLimitPostPerSecond  (optional)
     * @param rateLimitRegister  (optional)
     * @param rateLimitRegisterPerSecond  (optional)
     * @param rateLimitImage  (optional)
     * @param rateLimitImagePerSecond  (optional)
     * @param rateLimitComment  (optional)
     * @param rateLimitCommentPerSecond  (optional)
     * @param rateLimitSearch  (optional)
     * @param rateLimitSearchPerSecond  (optional)
     * @param federationEnabled  (optional)
     * @param federationDebug  (optional)
     * @param captchaEnabled  (optional)
     * @param captchaDifficulty  (optional)
     * @param allowedInstances  (optional)
     * @param blockedInstances  (optional)
     * @param taglines  (optional)
     * @param registrationMode  (optional)
     * @param reportsEmailAdmins  (optional)
     * @return [SiteResponse]
     */
    @PUT("site")
    suspend fun editSite(@Query("auth") auth: kotlin.String, @Query("name") name: kotlin.String? = null, @Query("sidebar") sidebar: kotlin.String? = null, @Query("description") description: kotlin.String? = null, @Query("icon") icon: kotlin.String? = null, @Query("banner") banner: kotlin.String? = null, @Query("enable_downvotes") enableDownvotes: kotlin.Boolean? = null, @Query("enable_nsfw") enableNsfw: kotlin.Boolean? = null, @Query("community_creation_admin_only") communityCreationAdminOnly: kotlin.Boolean? = null, @Query("require_email_verification") requireEmailVerification: kotlin.Boolean? = null, @Query("application_question") applicationQuestion: kotlin.String? = null, @Query("private_instance") privateInstance: kotlin.Boolean? = null, @Query("default_theme") defaultTheme: kotlin.String? = null, @Query("default_post_listing_type") defaultPostListingType: ListingType? = null, @Query("legal_information") legalInformation: kotlin.String? = null, @Query("application_email_admins") applicationEmailAdmins: kotlin.Boolean? = null, @Query("hide_modlog_mod_names") hideModlogModNames: kotlin.Boolean? = null, @Query("discussion_languages") discussionLanguages: kotlin.collections.List<java.math.BigDecimal>? = null, @Query("slur_filter_regex") slurFilterRegex: kotlin.String? = null, @Query("actor_name_max_length") actorNameMaxLength: java.math.BigDecimal? = null, @Query("rate_limit_message") rateLimitMessage: java.math.BigDecimal? = null, @Query("rate_limit_message_per_second") rateLimitMessagePerSecond: java.math.BigDecimal? = null, @Query("rate_limit_post") rateLimitPost: java.math.BigDecimal? = null, @Query("rate_limit_post_per_second") rateLimitPostPerSecond: java.math.BigDecimal? = null, @Query("rate_limit_register") rateLimitRegister: java.math.BigDecimal? = null, @Query("rate_limit_register_per_second") rateLimitRegisterPerSecond: java.math.BigDecimal? = null, @Query("rate_limit_image") rateLimitImage: java.math.BigDecimal? = null, @Query("rate_limit_image_per_second") rateLimitImagePerSecond: java.math.BigDecimal? = null, @Query("rate_limit_comment") rateLimitComment: java.math.BigDecimal? = null, @Query("rate_limit_comment_per_second") rateLimitCommentPerSecond: java.math.BigDecimal? = null, @Query("rate_limit_search") rateLimitSearch: java.math.BigDecimal? = null, @Query("rate_limit_search_per_second") rateLimitSearchPerSecond: java.math.BigDecimal? = null, @Query("federation_enabled") federationEnabled: kotlin.Boolean? = null, @Query("federation_debug") federationDebug: kotlin.Boolean? = null, @Query("captcha_enabled") captchaEnabled: kotlin.Boolean? = null, @Query("captcha_difficulty") captchaDifficulty: kotlin.String? = null, @Query("allowed_instances") allowedInstances: kotlin.collections.List<kotlin.String>? = null, @Query("blocked_instances") blockedInstances: kotlin.collections.List<kotlin.String>? = null, @Query("taglines") taglines: kotlin.collections.List<kotlin.String>? = null, @Query("registration_mode") registrationMode: RegistrationMode? = null, @Query("reports_email_admins") reportsEmailAdmins: kotlin.Boolean? = null): Response<SiteResponse>

    /**
     * A moderator can feature a community post ( IE stick it to the top of a community ).
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param featured 
     * @param featureType 
     * @param auth 
     * @return [PostResponse]
     */
    @POST("post/feature")
    suspend fun featurePost(@Query("post_id") postId: java.math.BigDecimal, @Query("featured") featured: kotlin.Boolean, @Query("feature_type") featureType: PostFeatureType, @Query("auth") auth: kotlin.String): Response<PostResponse>

    /**
     * Follow / subscribe to a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param follow 
     * @param auth 
     * @return [CommunityResponse]
     */
    @POST("community/follow")
    suspend fun followCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("follow") follow: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<CommunityResponse>

    /**
     * Get a list of banned users
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @return [BannedPersonsResponse]
     */
    @GET("user/banned")
    suspend fun getBannedPersons(@Query("auth") auth: kotlin.String): Response<BannedPersonsResponse>

    /**
     * Fetch a Captcha.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth  (optional)
     * @return [GetCaptchaResponse]
     */
    @GET("user/get_captcha")
    suspend fun getCaptcha(@Query("auth") auth: kotlin.String? = null): Response<GetCaptchaResponse>

    /**
     * Get / fetch comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param auth  (optional)
     * @return [CommentResponse]
     */
    @GET("comment")
    suspend fun getComment(@Query("id") id: java.math.BigDecimal, @Query("auth") auth: kotlin.String? = null): Response<CommentResponse>

    /**
     * Get / fetch comments.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param type  (optional)
     * @param sort  (optional)
     * @param maxDepth  (optional)
     * @param page  (optional)
     * @param limit  (optional)
     * @param communityId  (optional)
     * @param communityName  (optional)
     * @param postId  (optional)
     * @param parentId  (optional)
     * @param savedOnly  (optional)
     * @param auth  (optional)
     * @return [GetCommentsResponse]
     */
    @GET("comment/list")
    suspend fun getComments(@Query("type_") type: ListingType? = null, @Query("sort") sort: CommentSortType? = null, @Query("max_depth") maxDepth: java.math.BigDecimal? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("community_id") communityId: java.math.BigDecimal? = null, @Query("community_name") communityName: kotlin.String? = null, @Query("post_id") postId: java.math.BigDecimal? = null, @Query("parent_id") parentId: java.math.BigDecimal? = null, @Query("saved_only") savedOnly: kotlin.Boolean? = null, @Query("auth") auth: kotlin.String? = null): Response<GetCommentsResponse>

    /**
     * Fetch federated instances.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth  (optional)
     * @return [GetFederatedInstancesResponse]
     */
    @GET("federated_instances")
    suspend fun getFederatedInstances(@Query("auth") auth: kotlin.String? = null): Response<GetFederatedInstancesResponse>

    /**
     * Get the modlog.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param modPersonId  (optional)
     * @param communityId  (optional)
     * @param page  (optional)
     * @param limit  (optional)
     * @param type  (optional)
     * @param otherPersonId  (optional)
     * @param auth  (optional)
     * @return [GetModlogResponse]
     */
    @GET("modlog")
    suspend fun getModlog(@Query("mod_person_id") modPersonId: java.math.BigDecimal? = null, @Query("community_id") communityId: java.math.BigDecimal? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("type_") type: ModlogActionType? = null, @Query("other_person_id") otherPersonId: java.math.BigDecimal? = null, @Query("auth") auth: kotlin.String? = null): Response<GetModlogResponse>

    /**
     * Get the details for a person.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param personId  (optional)
     * @param username  (optional)
     * @param sort  (optional)
     * @param page  (optional)
     * @param limit  (optional)
     * @param communityId  (optional)
     * @param savedOnly  (optional)
     * @param auth  (optional)
     * @return [GetPersonDetailsResponse]
     */
    @GET("user")
    suspend fun getPersonDetails(@Query("person_id") personId: java.math.BigDecimal? = null, @Query("username") username: kotlin.String? = null, @Query("sort") sort: SortType? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("community_id") communityId: java.math.BigDecimal? = null, @Query("saved_only") savedOnly: kotlin.Boolean? = null, @Query("auth") auth: kotlin.String? = null): Response<GetPersonDetailsResponse>

    /**
     * Get mentions for your user.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @param sort  (optional)
     * @param page  (optional)
     * @param limit  (optional)
     * @param unreadOnly  (optional)
     * @return [GetPersonMentionsResponse]
     */
    @GET("user/mention")
    suspend fun getPersonMentions(@Query("auth") auth: kotlin.String, @Query("sort") sort: CommentSortType? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("unread_only") unreadOnly: kotlin.Boolean? = null): Response<GetPersonMentionsResponse>

    /**
     * Get / fetch posts, with various filters.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param type  (optional)
     * @param sort  (optional)
     * @param page  (optional)
     * @param limit  (optional)
     * @param communityId  (optional)
     * @param communityName  (optional)
     * @param savedOnly  (optional)
     * @param auth  (optional)
     * @return [GetPostsResponse]
     */
    @GET("post/list")
    suspend fun getPosts(@Query("type_") type: ListingType? = null, @Query("sort") sort: SortType? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("community_id") communityId: java.math.BigDecimal? = null, @Query("community_name") communityName: kotlin.String? = null, @Query("saved_only") savedOnly: kotlin.Boolean? = null, @Query("auth") auth: kotlin.String? = null): Response<GetPostsResponse>

    /**
     * Get / fetch private messages.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @param unreadOnly  (optional)
     * @param page  (optional)
     * @param limit  (optional)
     * @return [PrivateMessagesResponse]
     */
    @GET("private_message/list")
    suspend fun getPrivateMessages(@Query("auth") auth: kotlin.String, @Query("unread_only") unreadOnly: kotlin.Boolean? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null): Response<PrivateMessagesResponse>

    /**
     * Get comment replies.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @param sort  (optional)
     * @param page  (optional)
     * @param limit  (optional)
     * @param unreadOnly  (optional)
     * @return [GetRepliesResponse]
     */
    @GET("user/replies")
    suspend fun getReplies(@Query("auth") auth: kotlin.String, @Query("sort") sort: CommentSortType? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("unread_only") unreadOnly: kotlin.Boolean? = null): Response<GetRepliesResponse>

    /**
     * Get counts for your reports
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @param communityId  (optional)
     * @return [GetReportCountResponse]
     */
    @GET("user/report_count")
    suspend fun getReportCount(@Query("auth") auth: kotlin.String, @Query("community_id") communityId: java.math.BigDecimal? = null): Response<GetReportCountResponse>

    /**
     * Fetch metadata for any given site.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param url 
     * @return [GetSiteMetadataResponse]
     */
    @GET("post/site_metadata")
    suspend fun getSiteMetadata(@Query("url") url: kotlin.String): Response<GetSiteMetadataResponse>

    /**
     * Get your unread counts
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @return [GetUnreadCountResponse]
     */
    @GET("user/unread_count")
    suspend fun getUnreadCount(@Query("auth") auth: kotlin.String): Response<GetUnreadCountResponse>

    /**
     * Get the unread registration applications count.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @return [GetUnreadRegistrationApplicationCountResponse]
     */
    @GET("admin/registration_application/count")
    suspend fun getUnreadRegistrationApplicationCount(@Query("auth") auth: kotlin.String): Response<GetUnreadRegistrationApplicationCountResponse>

    /**
     * Leave the Site admins.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @return [GetSiteResponse]
     */
    @POST("user/leave_admin")
    suspend fun leaveAdmin(@Query("auth") auth: kotlin.String): Response<GetSiteResponse>

    /**
     * Like / vote on a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentId 
     * @param score 
     * @param auth 
     * @return [CommentResponse]
     */
    @POST("comment/like")
    suspend fun likeComment(@Query("comment_id") commentId: java.math.BigDecimal, @Query("score") score: java.math.BigDecimal, @Query("auth") auth: kotlin.String): Response<CommentResponse>

    /**
     * Like / vote on a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param score 
     * @param auth 
     * @return [PostResponse]
     */
    @POST("post/like")
    suspend fun likePost(@Query("post_id") postId: java.math.BigDecimal, @Query("score") score: java.math.BigDecimal, @Query("auth") auth: kotlin.String): Response<PostResponse>

    /**
     * List comment reports.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @param page  (optional)
     * @param limit  (optional)
     * @param unresolvedOnly  (optional)
     * @param communityId  (optional)
     * @return [ListCommentReportsResponse]
     */
    @GET("comment/report/list")
    suspend fun listCommentReports(@Query("auth") auth: kotlin.String, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("unresolved_only") unresolvedOnly: kotlin.Boolean? = null, @Query("community_id") communityId: java.math.BigDecimal? = null): Response<ListCommentReportsResponse>

    /**
     * List communities, with various filters.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param type  (optional)
     * @param sort  (optional)
     * @param page  (optional)
     * @param limit  (optional)
     * @param auth  (optional)
     * @return [ListCommunitiesResponse]
     */
    @GET("community/list")
    suspend fun listCommunities(@Query("type_") type: ListingType? = null, @Query("sort") sort: SortType? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("auth") auth: kotlin.String? = null): Response<ListCommunitiesResponse>

    /**
     * List post reports.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @param page  (optional)
     * @param limit  (optional)
     * @param unresolvedOnly  (optional)
     * @param communityId  (optional)
     * @return [ListPostReportsResponse]
     */
    @GET("post/report/list")
    suspend fun listPostReports(@Query("auth") auth: kotlin.String, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("unresolved_only") unresolvedOnly: kotlin.Boolean? = null, @Query("community_id") communityId: java.math.BigDecimal? = null): Response<ListPostReportsResponse>

    /**
     * List private message reports.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @param page  (optional)
     * @param limit  (optional)
     * @param unresolvedOnly  (optional)
     * @return [ListPrivateMessageReportsResponse]
     */
    @GET("private_message/report/list")
    suspend fun listPrivateMessageReports(@Query("auth") auth: kotlin.String, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("unresolved_only") unresolvedOnly: kotlin.Boolean? = null): Response<ListPrivateMessageReportsResponse>

    /**
     * List the registration applications.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @param unreadOnly  (optional)
     * @param page  (optional)
     * @param limit  (optional)
     * @return [ListRegistrationApplicationsResponse]
     */
    @GET("admin/registration_application/list")
    suspend fun listRegistrationApplications(@Query("auth") auth: kotlin.String, @Query("unread_only") unreadOnly: kotlin.Boolean? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null): Response<ListRegistrationApplicationsResponse>

    /**
     * A moderator can lock a post ( IE disable new comments ).
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param locked 
     * @param auth 
     * @return [PostResponse]
     */
    @POST("post/lock")
    suspend fun lockPost(@Query("post_id") postId: java.math.BigDecimal, @Query("locked") locked: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<PostResponse>

    /**
     * Log into lemmy.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param usernameOrEmail 
     * @param password 
     * @param totp2faToken  (optional)
     * @return [LoginResponse]
     */
    @POST("user/login")
    suspend fun login(@Query("username_or_email") usernameOrEmail: kotlin.String, @Query("password") password: kotlin.String, @Query("totp_2fa_token") totp2faToken: kotlin.String? = null): Response<LoginResponse>

    /**
     * Mark all replies as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @return [GetRepliesResponse]
     */
    @POST("user/mark_all_as_read")
    suspend fun markAllAsRead(@Query("auth") auth: kotlin.String): Response<GetRepliesResponse>

    /**
     * Mark a comment as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentReplyId 
     * @param read 
     * @param auth 
     * @return [CommentReplyResponse]
     */
    @POST("comment/mark_as_read")
    suspend fun markCommentReplyAsRead(@Query("comment_reply_id") commentReplyId: java.math.BigDecimal, @Query("read") read: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<CommentReplyResponse>

    /**
     * Mark a person mention as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param personMentionId 
     * @param read 
     * @param auth 
     * @return [PersonMentionResponse]
     */
    @POST("user/mention/mark_as_read")
    suspend fun markPersonMentionAsRead(@Query("person_mention_id") personMentionId: java.math.BigDecimal, @Query("read") read: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<PersonMentionResponse>

    /**
     * Mark a post as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param read 
     * @param auth 
     * @return [PostResponse]
     */
    @POST("post/mark_as_read")
    suspend fun markPostAsRead(@Query("post_id") postId: java.math.BigDecimal, @Query("read") read: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<PostResponse>

    /**
     * Mark a private message as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param privateMessageId 
     * @param read 
     * @param auth 
     * @return [PrivateMessageResponse]
     */
    @POST("private_message/mark_as_read")
    suspend fun markPrivateMessageAsRead(@Query("private_message_id") privateMessageId: java.math.BigDecimal, @Query("read") read: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<PrivateMessageResponse>

    /**
     * Change your password from an email / token based reset.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param token 
     * @param password 
     * @param passwordVerify 
     * @return [LoginResponse]
     */
    @POST("user/password_change")
    suspend fun passwordChangeAfterReset(@Query("token") token: kotlin.String, @Query("password") password: kotlin.String, @Query("password_verify") passwordVerify: kotlin.String): Response<LoginResponse>

    /**
     * Reset your password.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param email 
     * @return [kotlin.Any]
     */
    @POST("user/password_reset")
    suspend fun passwordReset(@Query("email") email: kotlin.String): Response<kotlin.Any>

    /**
     * Purge / Delete a comment from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentId 
     * @param auth 
     * @param reason  (optional)
     * @return [PurgeItemResponse]
     */
    @POST("admin/purge/comment")
    suspend fun purgeComment(@Query("comment_id") commentId: java.math.BigDecimal, @Query("auth") auth: kotlin.String, @Query("reason") reason: kotlin.String? = null): Response<PurgeItemResponse>

    /**
     * Purge / Delete a community from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param auth 
     * @param reason  (optional)
     * @return [PurgeItemResponse]
     */
    @POST("admin/purge/community")
    suspend fun purgeCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("auth") auth: kotlin.String, @Query("reason") reason: kotlin.String? = null): Response<PurgeItemResponse>

    /**
     * Purge / Delete a person from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param personId 
     * @param auth 
     * @param reason  (optional)
     * @return [PurgeItemResponse]
     */
    @POST("admin/purge/person")
    suspend fun purgePerson(@Query("person_id") personId: java.math.BigDecimal, @Query("auth") auth: kotlin.String, @Query("reason") reason: kotlin.String? = null): Response<PurgeItemResponse>

    /**
     * Purge / Delete a post from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param auth 
     * @param reason  (optional)
     * @return [PurgeItemResponse]
     */
    @POST("admin/purge/post")
    suspend fun purgePost(@Query("post_id") postId: java.math.BigDecimal, @Query("auth") auth: kotlin.String, @Query("reason") reason: kotlin.String? = null): Response<PurgeItemResponse>

    /**
     * Register a new user.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param username 
     * @param password 
     * @param passwordVerify 
     * @param showNsfw 
     * @param email  (optional)
     * @param captchaUuid  (optional)
     * @param captchaAnswer  (optional)
     * @param honeypot  (optional)
     * @param answer  (optional)
     * @return [LoginResponse]
     */
    @POST("user/register")
    suspend fun register(@Query("username") username: kotlin.String, @Query("password") password: kotlin.String, @Query("password_verify") passwordVerify: kotlin.String, @Query("show_nsfw") showNsfw: kotlin.Boolean, @Query("email") email: kotlin.String? = null, @Query("captcha_uuid") captchaUuid: kotlin.String? = null, @Query("captcha_answer") captchaAnswer: kotlin.String? = null, @Query("honeypot") honeypot: kotlin.String? = null, @Query("answer") answer: kotlin.String? = null): Response<LoginResponse>

    /**
     * A moderator remove for a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentId 
     * @param removed 
     * @param auth 
     * @param reason  (optional)
     * @return [CommentResponse]
     */
    @POST("comment/remove")
    suspend fun removeComment(@Query("comment_id") commentId: java.math.BigDecimal, @Query("removed") removed: kotlin.Boolean, @Query("auth") auth: kotlin.String, @Query("reason") reason: kotlin.String? = null): Response<CommentResponse>

    /**
     * A moderator remove for a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param removed 
     * @param auth 
     * @param reason  (optional)
     * @param expires  (optional)
     * @return [CommunityResponse]
     */
    @POST("community/remove")
    suspend fun removeCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("removed") removed: kotlin.Boolean, @Query("auth") auth: kotlin.String, @Query("reason") reason: kotlin.String? = null, @Query("expires") expires: java.math.BigDecimal? = null): Response<CommunityResponse>

    /**
     * A moderator remove for a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param removed 
     * @param auth 
     * @param reason  (optional)
     * @return [PostResponse]
     */
    @POST("post/remove")
    suspend fun removePost(@Query("post_id") postId: java.math.BigDecimal, @Query("removed") removed: kotlin.Boolean, @Query("auth") auth: kotlin.String, @Query("reason") reason: kotlin.String? = null): Response<PostResponse>

    /**
     * Resolve a comment report. Only a mod can do this.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param reportId 
     * @param resolved 
     * @param auth 
     * @return [CommentReportResponse]
     */
    @PUT("comment/report/resolve")
    suspend fun resolveCommentReport(@Query("report_id") reportId: java.math.BigDecimal, @Query("resolved") resolved: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<CommentReportResponse>

    /**
     * Fetch a non-local / federated object.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param q 
     * @param auth 
     * @return [ResolveObjectResponse]
     */
    @GET("resolve_object")
    suspend fun resolveObject(@Query("q") q: kotlin.String, @Query("auth") auth: kotlin.String): Response<ResolveObjectResponse>

    /**
     * Resolve a post report. Only a mod can do this.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param reportId 
     * @param resolved 
     * @param auth 
     * @return [PostReportResponse]
     */
    @PUT("post/report/resolve")
    suspend fun resolvePostReport(@Query("report_id") reportId: java.math.BigDecimal, @Query("resolved") resolved: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<PostReportResponse>

    /**
     * Resolve a report for a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param reportId 
     * @param resolved 
     * @param auth 
     * @return [PrivateMessageReportResponse]
     */
    @PUT("private_message/report/resolve")
    suspend fun resolvePrivateMessageReport(@Query("report_id") reportId: java.math.BigDecimal, @Query("resolved") resolved: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<PrivateMessageReportResponse>

    /**
     * Save a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentId 
     * @param save 
     * @param auth 
     * @return [CommentResponse]
     */
    @PUT("comment/save")
    suspend fun saveComment(@Query("comment_id") commentId: java.math.BigDecimal, @Query("save") save: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<CommentResponse>

    /**
     * Save a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param save 
     * @param auth 
     * @return [PostResponse]
     */
    @PUT("post/save")
    suspend fun savePost(@Query("post_id") postId: java.math.BigDecimal, @Query("save") save: kotlin.Boolean, @Query("auth") auth: kotlin.String): Response<PostResponse>

    /**
     * Save your user settings.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param auth 
     * @param showNsfw  (optional)
     * @param showScores  (optional)
     * @param theme  (optional)
     * @param defaultSortType  (optional)
     * @param defaultListingType  (optional)
     * @param interfaceLanguage  (optional)
     * @param avatar  (optional)
     * @param banner  (optional)
     * @param displayName  (optional)
     * @param email  (optional)
     * @param bio  (optional)
     * @param matrixUserId  (optional)
     * @param showAvatars  (optional)
     * @param sendNotificationsToEmail  (optional)
     * @param botAccount  (optional)
     * @param showBotAccounts  (optional)
     * @param showReadPosts  (optional)
     * @param showNewPostNotifs  (optional)
     * @param discussionLanguages  (optional)
     * @param generateTotp2fa  (optional)
     * @return [LoginResponse]
     */
    @PUT("user/save_user_settings")
    suspend fun saveUserSettings(@Query("auth") auth: kotlin.String, @Query("show_nsfw") showNsfw: kotlin.Boolean? = null, @Query("show_scores") showScores: kotlin.Boolean? = null, @Query("theme") theme: kotlin.String? = null, @Query("default_sort_type") defaultSortType: SortType? = null, @Query("default_listing_type") defaultListingType: ListingType? = null, @Query("interface_language") interfaceLanguage: kotlin.String? = null, @Query("avatar") avatar: kotlin.String? = null, @Query("banner") banner: kotlin.String? = null, @Query("display_name") displayName: kotlin.String? = null, @Query("email") email: kotlin.String? = null, @Query("bio") bio: kotlin.String? = null, @Query("matrix_user_id") matrixUserId: kotlin.String? = null, @Query("show_avatars") showAvatars: kotlin.Boolean? = null, @Query("send_notifications_to_email") sendNotificationsToEmail: kotlin.Boolean? = null, @Query("bot_account") botAccount: kotlin.Boolean? = null, @Query("show_bot_accounts") showBotAccounts: kotlin.Boolean? = null, @Query("show_read_posts") showReadPosts: kotlin.Boolean? = null, @Query("show_new_post_notifs") showNewPostNotifs: kotlin.Boolean? = null, @Query("discussion_languages") discussionLanguages: kotlin.collections.List<java.math.BigDecimal>? = null, @Query("generate_totp_2fa") generateTotp2fa: kotlin.Boolean? = null): Response<LoginResponse>

    /**
     * Search lemmy.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param q 
     * @param communityId  (optional)
     * @param communityName  (optional)
     * @param creatorId  (optional)
     * @param type  (optional)
     * @param sort  (optional)
     * @param listingType  (optional)
     * @param page  (optional)
     * @param limit  (optional)
     * @param auth  (optional)
     * @return [SearchResponse]
     */
    @GET("search")
    suspend fun search(@Query("q") q: kotlin.String, @Query("community_id") communityId: java.math.BigDecimal? = null, @Query("community_name") communityName: kotlin.String? = null, @Query("creator_id") creatorId: java.math.BigDecimal? = null, @Query("type_") type: SearchType? = null, @Query("sort") sort: SortType? = null, @Query("listing_type") listingType: ListingType? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("auth") auth: kotlin.String? = null): Response<SearchResponse>

    /**
     * Transfer your community to an existing moderator.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param personId 
     * @param auth 
     * @return [GetCommunityResponse]
     */
    @POST("community/transfer")
    suspend fun transferCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("person_id") personId: java.math.BigDecimal, @Query("auth") auth: kotlin.String): Response<GetCommunityResponse>

    /**
     * Verify your email
     * 
     * Responses:
     *  - 200: OK
     *
     * @param token 
     * @return [kotlin.Any]
     */
    @POST("user/verify_email")
    suspend fun verifyEmail(@Query("token") token: kotlin.String): Response<kotlin.Any>

}
