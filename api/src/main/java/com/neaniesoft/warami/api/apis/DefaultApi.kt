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
import java.math.BigDecimal

interface DefaultApi {
    /**
     * Add an admin to your site.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param personId 
     * @param added 
     * @return [AddAdminResponse]
     */
    @POST("admin/add")
    suspend fun addAdmin(@Query("person_id") personId: java.math.BigDecimal, @Query("added") added: Boolean): Response<AddAdminResponse>

    /**
     * Add a moderator to your community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param personId 
     * @param added 
     * @return [AddModToCommunityResponse]
     */
    @POST("community/mod")
    suspend fun addModToCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("person_id") personId: java.math.BigDecimal, @Query("added") added: Boolean): Response<AddModToCommunityResponse>

    /**
     * Approve a registration application
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param approve 
     * @param denyReason  (optional)
     * @return [RegistrationApplicationResponse]
     */
    @PUT("admin/registration_application/approve")
    suspend fun approveRegistrationApplication(@Query("id") id: java.math.BigDecimal, @Query("approve") approve: Boolean, @Query("deny_reason") denyReason: String? = null): Response<RegistrationApplicationResponse>

    /**
     * Ban a user from a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param personId 
     * @param ban 
     * @param removeData  (optional)
     * @param reason  (optional)
     * @param expires  (optional)
     * @return [BanFromCommunityResponse]
     */
    @POST("community/ban_user")
    suspend fun banFromCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("person_id") personId: java.math.BigDecimal, @Query("ban") ban: Boolean, @Query("remove_data") removeData: Boolean? = null, @Query("reason") reason: String? = null, @Query("expires") expires: java.math.BigDecimal? = null): Response<BanFromCommunityResponse>

    /**
     * Ban a person from your site.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param personId 
     * @param ban 
     * @param removeData  (optional)
     * @param reason  (optional)
     * @param expires  (optional)
     * @return [BanPersonResponse]
     */
    @POST("user/ban")
    suspend fun banPerson(@Query("person_id") personId: java.math.BigDecimal, @Query("ban") ban: Boolean, @Query("remove_data") removeData: Boolean? = null, @Query("reason") reason: String? = null, @Query("expires") expires: java.math.BigDecimal? = null): Response<BanPersonResponse>

    /**
     * Block a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param block 
     * @return [BlockCommunityResponse]
     */
    @POST("community/block")
    suspend fun blockCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("block") block: Boolean): Response<BlockCommunityResponse>

    /**
     * Block a person.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param personId 
     * @param block 
     * @return [BlockPersonResponse]
     */
    @POST("user/block")
    suspend fun blockPerson(@Query("person_id") personId: java.math.BigDecimal, @Query("block") block: Boolean): Response<BlockPersonResponse>

    /**
     * Change your user password.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param newPassword 
     * @param newPasswordVerify 
     * @param oldPassword 
     * @return [LoginResponse]
     */
    @PUT("user/change_password")
    suspend fun changePassword(@Query("new_password") newPassword: String, @Query("new_password_verify") newPasswordVerify: String, @Query("old_password") oldPassword: String): Response<LoginResponse>

    /**
     * Report a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentId 
     * @param reason 
     * @return [CommentReportResponse]
     */
    @POST("comment/report")
    suspend fun createCommentReport(@Query("comment_id") commentId: java.math.BigDecimal, @Query("reason") reason: String): Response<CommentReportResponse>

    /**
     * Report a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param reason 
     * @return [PostReportResponse]
     */
    @POST("post/report")
    suspend fun createPostReport(@Query("post_id") postId: java.math.BigDecimal, @Query("reason") reason: String): Response<PostReportResponse>

    /**
     * Create a report for a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param privateMessageId 
     * @param reason 
     * @return [PrivateMessageReportResponse]
     */
    @POST("private_message/report")
    suspend fun createPrivateMessageReport(@Query("private_message_id") privateMessageId: java.math.BigDecimal, @Query("reason") reason: String): Response<PrivateMessageReportResponse>

    /**
     * Delete your account.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param password 
     * @return [kotlin.Any]
     */
    @POST("user/delete_account")
    suspend fun deleteAccount(@Query("password") password: String): Response<Any>

    /**
     * Delete a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentId 
     * @param deleted 
     * @return [CommentResponse]
     */
    @POST("comment/delete")
    suspend fun deleteComment(@Query("comment_id") commentId: java.math.BigDecimal, @Query("deleted") deleted: Boolean): Response<CommentResponse>

    /**
     * Delete a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param deleted 
     * @return [CommunityResponse]
     */
    @POST("community/delete")
    suspend fun deleteCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("deleted") deleted: Boolean): Response<CommunityResponse>

    /**
     * Delete a custom emoji
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteCustomEmojiResponse]
     */
    @POST("custom_emoji/delete")
    suspend fun deleteCustomEmoji(@Query("id") id: java.math.BigDecimal): Response<DeleteCustomEmojiResponse>

    /**
     * Delete a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param deleted 
     * @return [PostResponse]
     */
    @POST("post/delete")
    suspend fun deletePost(@Query("post_id") postId: java.math.BigDecimal, @Query("deleted") deleted: Boolean): Response<PostResponse>

    /**
     * Delete a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param privateMessageId 
     * @param deleted 
     * @return [PrivateMessageResponse]
     */
    @POST("private_message/delete")
    suspend fun deletePrivateMessage(@Query("private_message_id") privateMessageId: java.math.BigDecimal, @Query("deleted") deleted: Boolean): Response<PrivateMessageResponse>

    /**
     * Distinguishes a comment (speak as moderator)
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentId 
     * @param distinguished 
     * @return [CommentResponse]
     */
    @POST("comment/distinguish")
    suspend fun distinguishComment(@Query("comment_id") commentId: java.math.BigDecimal, @Query("distinguished") distinguished: Boolean): Response<CommentResponse>

    /**
     * Edit a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
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
    suspend fun editCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("title") title: String? = null, @Query("description") description: String? = null, @Query("icon") icon: String? = null, @Query("banner") banner: String? = null, @Query("nsfw") nsfw: Boolean? = null, @Query("posting_restricted_to_mods") postingRestrictedToMods: Boolean? = null, @Query("discussion_languages") discussionLanguages: List<BigDecimal>? = null): Response<CommunityResponse>

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
     * @return [CustomEmojiResponse]
     */
    @PUT("custom_emoji")
    suspend fun editCustomEmoji(@Query("id") id: java.math.BigDecimal, @Query("category") category: String, @Query("image_url") imageUrl: String, @Query("alt_text") altText: String, @Query("keywords") keywords: List<String>): Response<CustomEmojiResponse>

    /**
     * Edit a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param name  (optional)
     * @param url  (optional)
     * @param body  (optional)
     * @param nsfw  (optional)
     * @param languageId  (optional)
     * @return [PostResponse]
     */
    @PUT("post")
    suspend fun editPost(@Query("post_id") postId: java.math.BigDecimal, @Query("name") name: String? = null, @Query("url") url: String? = null, @Query("body") body: String? = null, @Query("nsfw") nsfw: Boolean? = null, @Query("language_id") languageId: java.math.BigDecimal? = null): Response<PostResponse>

    /**
     * Edit a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param privateMessageId 
     * @param content 
     * @return [PrivateMessageResponse]
     */
    @PUT("private_message")
    suspend fun editPrivateMessage(@Query("private_message_id") privateMessageId: java.math.BigDecimal, @Query("content") content: String): Response<PrivateMessageResponse>

    /**
     * Edit your site.
     * 
     * Responses:
     *  - 200: OK
     *
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
    suspend fun editSite(@Query("name") name: String? = null, @Query("sidebar") sidebar: String? = null, @Query("description") description: String? = null, @Query("icon") icon: String? = null, @Query("banner") banner: String? = null, @Query("enable_downvotes") enableDownvotes: Boolean? = null, @Query("enable_nsfw") enableNsfw: Boolean? = null, @Query("community_creation_admin_only") communityCreationAdminOnly: Boolean? = null, @Query("require_email_verification") requireEmailVerification: Boolean? = null, @Query("application_question") applicationQuestion: String? = null, @Query("private_instance") privateInstance: Boolean? = null, @Query("default_theme") defaultTheme: String? = null, @Query("default_post_listing_type") defaultPostListingType: ListingType? = null, @Query("legal_information") legalInformation: String? = null, @Query("application_email_admins") applicationEmailAdmins: Boolean? = null, @Query("hide_modlog_mod_names") hideModlogModNames: Boolean? = null, @Query("discussion_languages") discussionLanguages: List<BigDecimal>? = null, @Query("slur_filter_regex") slurFilterRegex: String? = null, @Query("actor_name_max_length") actorNameMaxLength: java.math.BigDecimal? = null, @Query("rate_limit_message") rateLimitMessage: java.math.BigDecimal? = null, @Query("rate_limit_message_per_second") rateLimitMessagePerSecond: java.math.BigDecimal? = null, @Query("rate_limit_post") rateLimitPost: java.math.BigDecimal? = null, @Query("rate_limit_post_per_second") rateLimitPostPerSecond: java.math.BigDecimal? = null, @Query("rate_limit_register") rateLimitRegister: java.math.BigDecimal? = null, @Query("rate_limit_register_per_second") rateLimitRegisterPerSecond: java.math.BigDecimal? = null, @Query("rate_limit_image") rateLimitImage: java.math.BigDecimal? = null, @Query("rate_limit_image_per_second") rateLimitImagePerSecond: java.math.BigDecimal? = null, @Query("rate_limit_comment") rateLimitComment: java.math.BigDecimal? = null, @Query("rate_limit_comment_per_second") rateLimitCommentPerSecond: java.math.BigDecimal? = null, @Query("rate_limit_search") rateLimitSearch: java.math.BigDecimal? = null, @Query("rate_limit_search_per_second") rateLimitSearchPerSecond: java.math.BigDecimal? = null, @Query("federation_enabled") federationEnabled: Boolean? = null, @Query("federation_debug") federationDebug: Boolean? = null, @Query("captcha_enabled") captchaEnabled: Boolean? = null, @Query("captcha_difficulty") captchaDifficulty: String? = null, @Query("allowed_instances") allowedInstances: List<String>? = null, @Query("blocked_instances") blockedInstances: List<String>? = null, @Query("taglines") taglines: List<String>? = null, @Query("registration_mode") registrationMode: RegistrationMode? = null, @Query("reports_email_admins") reportsEmailAdmins: Boolean? = null): Response<SiteResponse>

    /**
     * A moderator can feature a community post ( IE stick it to the top of a community ).
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param featured 
     * @param featureType 
     * @return [PostResponse]
     */
    @POST("post/feature")
    suspend fun featurePost(@Query("post_id") postId: java.math.BigDecimal, @Query("featured") featured: Boolean, @Query("feature_type") featureType: PostFeatureType): Response<PostResponse>

    /**
     * Follow / subscribe to a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param follow 
     * @return [CommunityResponse]
     */
    @POST("community/follow")
    suspend fun followCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("follow") follow: Boolean): Response<CommunityResponse>

    /**
     * Get a list of banned users
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [BannedPersonsResponse]
     */
    @GET("user/banned")
    suspend fun getBannedPersons(): Response<BannedPersonsResponse>

    /**
     * Fetch a Captcha.
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetCaptchaResponse]
     */
    @GET("user/get_captcha")
    suspend fun getCaptcha(): Response<GetCaptchaResponse>

    /**
     * Get / fetch comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [CommentResponse]
     */
    @GET("comment")
    suspend fun getComment(@Query("id") id: java.math.BigDecimal): Response<CommentResponse>

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
     * @return [GetCommentsResponse]
     */
    @GET("comment/list")
    suspend fun getComments(@Query("type_") type: ListingType? = null, @Query("sort") sort: CommentSortType? = null, @Query("max_depth") maxDepth: java.math.BigDecimal? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("community_id") communityId: java.math.BigDecimal? = null, @Query("community_name") communityName: String? = null, @Query("post_id") postId: java.math.BigDecimal? = null, @Query("parent_id") parentId: java.math.BigDecimal? = null, @Query("saved_only") savedOnly: Boolean? = null): Response<GetCommentsResponse>

    /**
     * Fetch federated instances.
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetFederatedInstancesResponse]
     */
    @GET("federated_instances")
    suspend fun getFederatedInstances(): Response<GetFederatedInstancesResponse>

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
     * @return [GetModlogResponse]
     */
    @GET("modlog")
    suspend fun getModlog(@Query("mod_person_id") modPersonId: java.math.BigDecimal? = null, @Query("community_id") communityId: java.math.BigDecimal? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("type_") type: ModlogActionType? = null, @Query("other_person_id") otherPersonId: java.math.BigDecimal? = null): Response<GetModlogResponse>

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
     * @return [GetPersonDetailsResponse]
     */
    @GET("user")
    suspend fun getPersonDetails(@Query("person_id") personId: java.math.BigDecimal? = null, @Query("username") username: String? = null, @Query("sort") sort: SortType? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("community_id") communityId: java.math.BigDecimal? = null, @Query("saved_only") savedOnly: Boolean? = null): Response<GetPersonDetailsResponse>

    /**
     * Get mentions for your user.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param sort  (optional)
     * @param page  (optional)
     * @param limit  (optional)
     * @param unreadOnly  (optional)
     * @return [GetPersonMentionsResponse]
     */
    @GET("user/mention")
    suspend fun getPersonMentions(@Query("sort") sort: CommentSortType? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("unread_only") unreadOnly: Boolean? = null): Response<GetPersonMentionsResponse>

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
     * @return [GetPostsResponse]
     */
    @GET("post/list")
    suspend fun getPosts(@Query("type_") type: ListingType? = null, @Query("sort") sort: SortType? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("community_id") communityId: java.math.BigDecimal? = null, @Query("community_name") communityName: String? = null, @Query("saved_only") savedOnly: Boolean? = null): Response<GetPostsResponse>

    /**
     * Get / fetch private messages.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param unreadOnly  (optional)
     * @param page  (optional)
     * @param limit  (optional)
     * @return [PrivateMessagesResponse]
     */
    @GET("private_message/list")
    suspend fun getPrivateMessages(@Query("unread_only") unreadOnly: Boolean? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null): Response<PrivateMessagesResponse>

    /**
     * Get comment replies.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param sort  (optional)
     * @param page  (optional)
     * @param limit  (optional)
     * @param unreadOnly  (optional)
     * @return [GetRepliesResponse]
     */
    @GET("user/replies")
    suspend fun getReplies(@Query("sort") sort: CommentSortType? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("unread_only") unreadOnly: Boolean? = null): Response<GetRepliesResponse>

    /**
     * Get counts for your reports
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId  (optional)
     * @return [GetReportCountResponse]
     */
    @GET("user/report_count")
    suspend fun getReportCount(@Query("community_id") communityId: java.math.BigDecimal? = null): Response<GetReportCountResponse>

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
    suspend fun getSiteMetadata(@Query("url") url: String): Response<GetSiteMetadataResponse>

    /**
     * Get your unread counts
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetUnreadCountResponse]
     */
    @GET("user/unread_count")
    suspend fun getUnreadCount(): Response<GetUnreadCountResponse>

    /**
     * Get the unread registration applications count.
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetUnreadRegistrationApplicationCountResponse]
     */
    @GET("admin/registration_application/count")
    suspend fun getUnreadRegistrationApplicationCount(): Response<GetUnreadRegistrationApplicationCountResponse>

    /**
     * Leave the Site admins.
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetSiteResponse]
     */
    @POST("user/leave_admin")
    suspend fun leaveAdmin(): Response<GetSiteResponse>

    /**
     * Like / vote on a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentId 
     * @param score 
     * @return [CommentResponse]
     */
    @POST("comment/like")
    suspend fun likeComment(@Query("comment_id") commentId: java.math.BigDecimal, @Query("score") score: java.math.BigDecimal): Response<CommentResponse>

    /**
     * Like / vote on a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param score 
     * @return [PostResponse]
     */
    @POST("post/like")
    suspend fun likePost(@Query("post_id") postId: java.math.BigDecimal, @Query("score") score: java.math.BigDecimal): Response<PostResponse>

    /**
     * List comment reports.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param page  (optional)
     * @param limit  (optional)
     * @param unresolvedOnly  (optional)
     * @param communityId  (optional)
     * @return [ListCommentReportsResponse]
     */
    @GET("comment/report/list")
    suspend fun listCommentReports(@Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("unresolved_only") unresolvedOnly: Boolean? = null, @Query("community_id") communityId: java.math.BigDecimal? = null): Response<ListCommentReportsResponse>

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
     * @return [ListCommunitiesResponse]
     */
    @GET("community/list")
    suspend fun listCommunities(@Query("type_") type: ListingType? = null, @Query("sort") sort: SortType? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null): Response<ListCommunitiesResponse>

    /**
     * List post reports.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param page  (optional)
     * @param limit  (optional)
     * @param unresolvedOnly  (optional)
     * @param communityId  (optional)
     * @return [ListPostReportsResponse]
     */
    @GET("post/report/list")
    suspend fun listPostReports(@Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("unresolved_only") unresolvedOnly: Boolean? = null, @Query("community_id") communityId: java.math.BigDecimal? = null): Response<ListPostReportsResponse>

    /**
     * List private message reports.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param page  (optional)
     * @param limit  (optional)
     * @param unresolvedOnly  (optional)
     * @return [ListPrivateMessageReportsResponse]
     */
    @GET("private_message/report/list")
    suspend fun listPrivateMessageReports(@Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null, @Query("unresolved_only") unresolvedOnly: Boolean? = null): Response<ListPrivateMessageReportsResponse>

    /**
     * List the registration applications.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param unreadOnly  (optional)
     * @param page  (optional)
     * @param limit  (optional)
     * @return [ListRegistrationApplicationsResponse]
     */
    @GET("admin/registration_application/list")
    suspend fun listRegistrationApplications(@Query("unread_only") unreadOnly: Boolean? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null): Response<ListRegistrationApplicationsResponse>

    /**
     * A moderator can lock a post ( IE disable new comments ).
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param locked 
     * @return [PostResponse]
     */
    @POST("post/lock")
    suspend fun lockPost(@Query("post_id") postId: java.math.BigDecimal, @Query("locked") locked: Boolean): Response<PostResponse>

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
    suspend fun login(@Query("username_or_email") usernameOrEmail: String, @Query("password") password: String, @Query("totp_2fa_token") totp2faToken: String? = null): Response<LoginResponse>

    /**
     * Mark all replies as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetRepliesResponse]
     */
    @POST("user/mark_all_as_read")
    suspend fun markAllAsRead(): Response<GetRepliesResponse>

    /**
     * Mark a comment as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentReplyId 
     * @param read 
     * @return [CommentReplyResponse]
     */
    @POST("comment/mark_as_read")
    suspend fun markCommentReplyAsRead(@Query("comment_reply_id") commentReplyId: java.math.BigDecimal, @Query("read") read: Boolean): Response<CommentReplyResponse>

    /**
     * Mark a person mention as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param personMentionId 
     * @param read 
     * @return [PersonMentionResponse]
     */
    @POST("user/mention/mark_as_read")
    suspend fun markPersonMentionAsRead(@Query("person_mention_id") personMentionId: java.math.BigDecimal, @Query("read") read: Boolean): Response<PersonMentionResponse>

    /**
     * Mark a post as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param read 
     * @return [PostResponse]
     */
    @POST("post/mark_as_read")
    suspend fun markPostAsRead(@Query("post_id") postId: java.math.BigDecimal, @Query("read") read: Boolean): Response<PostResponse>

    /**
     * Mark a private message as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param privateMessageId 
     * @param read 
     * @return [PrivateMessageResponse]
     */
    @POST("private_message/mark_as_read")
    suspend fun markPrivateMessageAsRead(@Query("private_message_id") privateMessageId: java.math.BigDecimal, @Query("read") read: Boolean): Response<PrivateMessageResponse>

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
    suspend fun passwordChangeAfterReset(@Query("token") token: String, @Query("password") password: String, @Query("password_verify") passwordVerify: String): Response<LoginResponse>

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
    suspend fun passwordReset(@Query("email") email: String): Response<Any>

    /**
     * Purge / Delete a comment from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentId 
     * @param reason  (optional)
     * @return [PurgeItemResponse]
     */
    @POST("admin/purge/comment")
    suspend fun purgeComment(@Query("comment_id") commentId: java.math.BigDecimal, @Query("reason") reason: String? = null): Response<PurgeItemResponse>

    /**
     * Purge / Delete a community from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param reason  (optional)
     * @return [PurgeItemResponse]
     */
    @POST("admin/purge/community")
    suspend fun purgeCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("reason") reason: String? = null): Response<PurgeItemResponse>

    /**
     * Purge / Delete a person from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param personId 
     * @param reason  (optional)
     * @return [PurgeItemResponse]
     */
    @POST("admin/purge/person")
    suspend fun purgePerson(@Query("person_id") personId: java.math.BigDecimal, @Query("reason") reason: String? = null): Response<PurgeItemResponse>

    /**
     * Purge / Delete a post from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param reason  (optional)
     * @return [PurgeItemResponse]
     */
    @POST("admin/purge/post")
    suspend fun purgePost(@Query("post_id") postId: java.math.BigDecimal, @Query("reason") reason: String? = null): Response<PurgeItemResponse>

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
    suspend fun register(@Query("username") username: String, @Query("password") password: String, @Query("password_verify") passwordVerify: String, @Query("show_nsfw") showNsfw: Boolean, @Query("email") email: String? = null, @Query("captcha_uuid") captchaUuid: String? = null, @Query("captcha_answer") captchaAnswer: String? = null, @Query("honeypot") honeypot: String? = null, @Query("answer") answer: String? = null): Response<LoginResponse>

    /**
     * A moderator remove for a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentId 
     * @param removed 
     * @param reason  (optional)
     * @return [CommentResponse]
     */
    @POST("comment/remove")
    suspend fun removeComment(@Query("comment_id") commentId: java.math.BigDecimal, @Query("removed") removed: Boolean, @Query("reason") reason: String? = null): Response<CommentResponse>

    /**
     * A moderator remove for a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param removed 
     * @param reason  (optional)
     * @param expires  (optional)
     * @return [CommunityResponse]
     */
    @POST("community/remove")
    suspend fun removeCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("removed") removed: Boolean, @Query("reason") reason: String? = null, @Query("expires") expires: java.math.BigDecimal? = null): Response<CommunityResponse>

    /**
     * A moderator remove for a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param removed 
     * @param reason  (optional)
     * @return [PostResponse]
     */
    @POST("post/remove")
    suspend fun removePost(@Query("post_id") postId: java.math.BigDecimal, @Query("removed") removed: Boolean, @Query("reason") reason: String? = null): Response<PostResponse>

    /**
     * Resolve a comment report. Only a mod can do this.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param reportId 
     * @param resolved 
     * @return [CommentReportResponse]
     */
    @PUT("comment/report/resolve")
    suspend fun resolveCommentReport(@Query("report_id") reportId: java.math.BigDecimal, @Query("resolved") resolved: Boolean): Response<CommentReportResponse>

    /**
     * Fetch a non-local / federated object.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param q 
     * @return [ResolveObjectResponse]
     */
    @GET("resolve_object")
    suspend fun resolveObject(@Query("q") q: String): Response<ResolveObjectResponse>

    /**
     * Resolve a post report. Only a mod can do this.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param reportId 
     * @param resolved 
     * @return [PostReportResponse]
     */
    @PUT("post/report/resolve")
    suspend fun resolvePostReport(@Query("report_id") reportId: java.math.BigDecimal, @Query("resolved") resolved: Boolean): Response<PostReportResponse>

    /**
     * Resolve a report for a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param reportId 
     * @param resolved 
     * @return [PrivateMessageReportResponse]
     */
    @PUT("private_message/report/resolve")
    suspend fun resolvePrivateMessageReport(@Query("report_id") reportId: java.math.BigDecimal, @Query("resolved") resolved: Boolean): Response<PrivateMessageReportResponse>

    /**
     * Save a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commentId 
     * @param save 
     * @return [CommentResponse]
     */
    @PUT("comment/save")
    suspend fun saveComment(@Query("comment_id") commentId: java.math.BigDecimal, @Query("save") save: Boolean): Response<CommentResponse>

    /**
     * Save a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param postId 
     * @param save 
     * @return [PostResponse]
     */
    @PUT("post/save")
    suspend fun savePost(@Query("post_id") postId: java.math.BigDecimal, @Query("save") save: Boolean): Response<PostResponse>

    /**
     * Save your user settings.
     * 
     * Responses:
     *  - 200: OK
     *
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
    suspend fun saveUserSettings(@Query("show_nsfw") showNsfw: Boolean? = null, @Query("show_scores") showScores: Boolean? = null, @Query("theme") theme: String? = null, @Query("default_sort_type") defaultSortType: SortType? = null, @Query("default_listing_type") defaultListingType: ListingType? = null, @Query("interface_language") interfaceLanguage: String? = null, @Query("avatar") avatar: String? = null, @Query("banner") banner: String? = null, @Query("display_name") displayName: String? = null, @Query("email") email: String? = null, @Query("bio") bio: String? = null, @Query("matrix_user_id") matrixUserId: String? = null, @Query("show_avatars") showAvatars: Boolean? = null, @Query("send_notifications_to_email") sendNotificationsToEmail: Boolean? = null, @Query("bot_account") botAccount: Boolean? = null, @Query("show_bot_accounts") showBotAccounts: Boolean? = null, @Query("show_read_posts") showReadPosts: Boolean? = null, @Query("show_new_post_notifs") showNewPostNotifs: Boolean? = null, @Query("discussion_languages") discussionLanguages: List<BigDecimal>? = null, @Query("generate_totp_2fa") generateTotp2fa: Boolean? = null): Response<LoginResponse>

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
     * @return [SearchResponse]
     */
    @GET("search")
    suspend fun search(@Query("q") q: String, @Query("community_id") communityId: java.math.BigDecimal? = null, @Query("community_name") communityName: String? = null, @Query("creator_id") creatorId: java.math.BigDecimal? = null, @Query("type_") type: SearchType? = null, @Query("sort") sort: SortType? = null, @Query("listing_type") listingType: ListingType? = null, @Query("page") page: java.math.BigDecimal? = null, @Query("limit") limit: java.math.BigDecimal? = null): Response<SearchResponse>

    /**
     * Transfer your community to an existing moderator.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param communityId 
     * @param personId 
     * @return [GetCommunityResponse]
     */
    @POST("community/transfer")
    suspend fun transferCommunity(@Query("community_id") communityId: java.math.BigDecimal, @Query("person_id") personId: java.math.BigDecimal): Response<GetCommunityResponse>

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
    suspend fun verifyEmail(@Query("token") token: String): Response<Any>

}
