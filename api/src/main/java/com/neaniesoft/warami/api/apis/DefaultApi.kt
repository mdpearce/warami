package com.neaniesoft.warami.api.apis

import com.neaniesoft.warami.api.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.squareup.moshi.Json

import com.neaniesoft.warami.api.models.AddAdmin
import com.neaniesoft.warami.api.models.AddAdminResponse
import com.neaniesoft.warami.api.models.AddModToCommunity
import com.neaniesoft.warami.api.models.AddModToCommunityResponse
import com.neaniesoft.warami.api.models.ApproveRegistrationApplication
import com.neaniesoft.warami.api.models.BanFromCommunity
import com.neaniesoft.warami.api.models.BanFromCommunityResponse
import com.neaniesoft.warami.api.models.BanPerson
import com.neaniesoft.warami.api.models.BanPersonResponse
import com.neaniesoft.warami.api.models.BannedPersonsResponse
import com.neaniesoft.warami.api.models.BlockCommunity
import com.neaniesoft.warami.api.models.BlockCommunityResponse
import com.neaniesoft.warami.api.models.BlockPerson
import com.neaniesoft.warami.api.models.BlockPersonResponse
import com.neaniesoft.warami.api.models.ChangePassword
import com.neaniesoft.warami.api.models.CommentReplyResponse
import com.neaniesoft.warami.api.models.CommentReportResponse
import com.neaniesoft.warami.api.models.CommentResponse
import com.neaniesoft.warami.api.models.CommentSortType
import com.neaniesoft.warami.api.models.CommunityResponse
import com.neaniesoft.warami.api.models.CreateCommentLike
import com.neaniesoft.warami.api.models.CreateCommentReport
import com.neaniesoft.warami.api.models.CreatePostLike
import com.neaniesoft.warami.api.models.CreatePostReport
import com.neaniesoft.warami.api.models.CreatePrivateMessageReport
import com.neaniesoft.warami.api.models.CustomEmojiResponse
import com.neaniesoft.warami.api.models.DeleteAccount
import com.neaniesoft.warami.api.models.DeleteComment
import com.neaniesoft.warami.api.models.DeleteCommunity
import com.neaniesoft.warami.api.models.DeleteCustomEmoji
import com.neaniesoft.warami.api.models.DeleteCustomEmojiResponse
import com.neaniesoft.warami.api.models.DeletePost
import com.neaniesoft.warami.api.models.DeletePrivateMessage
import com.neaniesoft.warami.api.models.DistinguishComment
import com.neaniesoft.warami.api.models.EditCommunity
import com.neaniesoft.warami.api.models.EditCustomEmoji
import com.neaniesoft.warami.api.models.EditPost
import com.neaniesoft.warami.api.models.EditPrivateMessage
import com.neaniesoft.warami.api.models.EditSite
import com.neaniesoft.warami.api.models.FeaturePost
import com.neaniesoft.warami.api.models.FollowCommunity
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
import com.neaniesoft.warami.api.models.LeaveAdmin
import com.neaniesoft.warami.api.models.ListCommentReportsResponse
import com.neaniesoft.warami.api.models.ListCommunitiesResponse
import com.neaniesoft.warami.api.models.ListPostReportsResponse
import com.neaniesoft.warami.api.models.ListPrivateMessageReportsResponse
import com.neaniesoft.warami.api.models.ListRegistrationApplicationsResponse
import com.neaniesoft.warami.api.models.ListingType
import com.neaniesoft.warami.api.models.LockPost
import com.neaniesoft.warami.api.models.Login
import com.neaniesoft.warami.api.models.LoginResponse
import com.neaniesoft.warami.api.models.MarkAllAsRead
import com.neaniesoft.warami.api.models.MarkCommentReplyAsRead
import com.neaniesoft.warami.api.models.MarkPersonMentionAsRead
import com.neaniesoft.warami.api.models.MarkPostAsRead
import com.neaniesoft.warami.api.models.MarkPrivateMessageAsRead
import com.neaniesoft.warami.api.models.ModlogActionType
import com.neaniesoft.warami.api.models.PasswordChangeAfterReset
import com.neaniesoft.warami.api.models.PasswordReset
import com.neaniesoft.warami.api.models.PersonMentionResponse
import com.neaniesoft.warami.api.models.PostReportResponse
import com.neaniesoft.warami.api.models.PostResponse
import com.neaniesoft.warami.api.models.PrivateMessageReportResponse
import com.neaniesoft.warami.api.models.PrivateMessageResponse
import com.neaniesoft.warami.api.models.PrivateMessagesResponse
import com.neaniesoft.warami.api.models.PurgeComment
import com.neaniesoft.warami.api.models.PurgeCommunity
import com.neaniesoft.warami.api.models.PurgeItemResponse
import com.neaniesoft.warami.api.models.PurgePerson
import com.neaniesoft.warami.api.models.PurgePost
import com.neaniesoft.warami.api.models.Register
import com.neaniesoft.warami.api.models.RegistrationApplicationResponse
import com.neaniesoft.warami.api.models.RemoveComment
import com.neaniesoft.warami.api.models.RemoveCommunity
import com.neaniesoft.warami.api.models.RemovePost
import com.neaniesoft.warami.api.models.ResolveCommentReport
import com.neaniesoft.warami.api.models.ResolveObjectResponse
import com.neaniesoft.warami.api.models.ResolvePostReport
import com.neaniesoft.warami.api.models.ResolvePrivateMessageReport
import com.neaniesoft.warami.api.models.SaveComment
import com.neaniesoft.warami.api.models.SavePost
import com.neaniesoft.warami.api.models.SaveUserSettings
import com.neaniesoft.warami.api.models.SearchResponse
import com.neaniesoft.warami.api.models.SearchType
import com.neaniesoft.warami.api.models.SiteResponse
import com.neaniesoft.warami.api.models.SortType
import com.neaniesoft.warami.api.models.TransferCommunity
import com.neaniesoft.warami.api.models.VerifyEmail

interface DefaultApi {
    /**
     * Add an admin to your site.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param addAdmin  (optional)
     * @return [AddAdminResponse]
     */
    @POST("admin/add")
    suspend fun addAdmin(@Body addAdmin: AddAdmin? = null): Response<AddAdminResponse>

    /**
     * Add a moderator to your community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param addModToCommunity  (optional)
     * @return [AddModToCommunityResponse]
     */
    @POST("community/mod")
    suspend fun addModToCommunity(@Body addModToCommunity: AddModToCommunity? = null): Response<AddModToCommunityResponse>

    /**
     * Approve a registration application
     * 
     * Responses:
     *  - 200: OK
     *
     * @param approveRegistrationApplication  (optional)
     * @return [RegistrationApplicationResponse]
     */
    @PUT("admin/registration_application/approve")
    suspend fun approveRegistrationApplication(@Body approveRegistrationApplication: ApproveRegistrationApplication? = null): Response<RegistrationApplicationResponse>

    /**
     * Ban a user from a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param banFromCommunity  (optional)
     * @return [BanFromCommunityResponse]
     */
    @POST("community/ban_user")
    suspend fun banFromCommunity(@Body banFromCommunity: BanFromCommunity? = null): Response<BanFromCommunityResponse>

    /**
     * Ban a person from your site.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param banPerson  (optional)
     * @return [BanPersonResponse]
     */
    @POST("user/ban")
    suspend fun banPerson(@Body banPerson: BanPerson? = null): Response<BanPersonResponse>

    /**
     * Block a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param blockCommunity  (optional)
     * @return [BlockCommunityResponse]
     */
    @POST("community/block")
    suspend fun blockCommunity(@Body blockCommunity: BlockCommunity? = null): Response<BlockCommunityResponse>

    /**
     * Block a person.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param blockPerson  (optional)
     * @return [BlockPersonResponse]
     */
    @POST("user/block")
    suspend fun blockPerson(@Body blockPerson: BlockPerson? = null): Response<BlockPersonResponse>

    /**
     * Change your user password.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param changePassword  (optional)
     * @return [LoginResponse]
     */
    @PUT("user/change_password")
    suspend fun changePassword(@Body changePassword: ChangePassword? = null): Response<LoginResponse>

    /**
     * Report a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createCommentReport  (optional)
     * @return [CommentReportResponse]
     */
    @POST("comment/report")
    suspend fun createCommentReport(@Body createCommentReport: CreateCommentReport? = null): Response<CommentReportResponse>

    /**
     * Report a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createPostReport  (optional)
     * @return [PostReportResponse]
     */
    @POST("post/report")
    suspend fun createPostReport(@Body createPostReport: CreatePostReport? = null): Response<PostReportResponse>

    /**
     * Create a report for a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createPrivateMessageReport  (optional)
     * @return [PrivateMessageReportResponse]
     */
    @POST("private_message/report")
    suspend fun createPrivateMessageReport(@Body createPrivateMessageReport: CreatePrivateMessageReport? = null): Response<PrivateMessageReportResponse>

    /**
     * Delete your account.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param deleteAccount  (optional)
     * @return [kotlin.Any]
     */
    @POST("user/delete_account")
    suspend fun deleteAccount(@Body deleteAccount: DeleteAccount? = null): Response<kotlin.Any>

    /**
     * Delete a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param deleteComment  (optional)
     * @return [CommentResponse]
     */
    @POST("comment/delete")
    suspend fun deleteComment(@Body deleteComment: DeleteComment? = null): Response<CommentResponse>

    /**
     * Delete a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param deleteCommunity  (optional)
     * @return [CommunityResponse]
     */
    @POST("community/delete")
    suspend fun deleteCommunity(@Body deleteCommunity: DeleteCommunity? = null): Response<CommunityResponse>

    /**
     * Delete a custom emoji
     * 
     * Responses:
     *  - 200: OK
     *
     * @param deleteCustomEmoji  (optional)
     * @return [DeleteCustomEmojiResponse]
     */
    @POST("custom_emoji/delete")
    suspend fun deleteCustomEmoji(@Body deleteCustomEmoji: DeleteCustomEmoji? = null): Response<DeleteCustomEmojiResponse>

    /**
     * Delete a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param deletePost  (optional)
     * @return [PostResponse]
     */
    @POST("post/delete")
    suspend fun deletePost(@Body deletePost: DeletePost? = null): Response<PostResponse>

    /**
     * Delete a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param deletePrivateMessage  (optional)
     * @return [PrivateMessageResponse]
     */
    @POST("private_message/delete")
    suspend fun deletePrivateMessage(@Body deletePrivateMessage: DeletePrivateMessage? = null): Response<PrivateMessageResponse>

    /**
     * Distinguishes a comment (speak as moderator)
     * 
     * Responses:
     *  - 200: OK
     *
     * @param distinguishComment  (optional)
     * @return [CommentResponse]
     */
    @POST("comment/distinguish")
    suspend fun distinguishComment(@Body distinguishComment: DistinguishComment? = null): Response<CommentResponse>

    /**
     * Edit a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param editCommunity  (optional)
     * @return [CommunityResponse]
     */
    @PUT("community")
    suspend fun editCommunity(@Body editCommunity: EditCommunity? = null): Response<CommunityResponse>

    /**
     * Edit an existing custom emoji
     * 
     * Responses:
     *  - 200: OK
     *
     * @param editCustomEmoji  (optional)
     * @return [CustomEmojiResponse]
     */
    @PUT("custom_emoji")
    suspend fun editCustomEmoji(@Body editCustomEmoji: EditCustomEmoji? = null): Response<CustomEmojiResponse>

    /**
     * Edit a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param editPost  (optional)
     * @return [PostResponse]
     */
    @PUT("post")
    suspend fun editPost(@Body editPost: EditPost? = null): Response<PostResponse>

    /**
     * Edit a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param editPrivateMessage  (optional)
     * @return [PrivateMessageResponse]
     */
    @PUT("private_message")
    suspend fun editPrivateMessage(@Body editPrivateMessage: EditPrivateMessage? = null): Response<PrivateMessageResponse>

    /**
     * Edit your site.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param editSite  (optional)
     * @return [SiteResponse]
     */
    @PUT("site")
    suspend fun editSite(@Body editSite: EditSite? = null): Response<SiteResponse>

    /**
     * A moderator can feature a community post ( IE stick it to the top of a community ).
     * 
     * Responses:
     *  - 200: OK
     *
     * @param featurePost  (optional)
     * @return [PostResponse]
     */
    @POST("post/feature")
    suspend fun featurePost(@Body featurePost: FeaturePost? = null): Response<PostResponse>

    /**
     * Follow / subscribe to a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param followCommunity  (optional)
     * @return [CommunityResponse]
     */
    @POST("community/follow")
    suspend fun followCommunity(@Body followCommunity: FollowCommunity? = null): Response<CommunityResponse>

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
     * @param leaveAdmin  (optional)
     * @return [GetSiteResponse]
     */
    @POST("user/leave_admin")
    suspend fun leaveAdmin(@Body leaveAdmin: LeaveAdmin? = null): Response<GetSiteResponse>

    /**
     * Like / vote on a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createCommentLike  (optional)
     * @return [CommentResponse]
     */
    @POST("comment/like")
    suspend fun likeComment(@Body createCommentLike: CreateCommentLike? = null): Response<CommentResponse>

    /**
     * Like / vote on a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createPostLike  (optional)
     * @return [PostResponse]
     */
    @POST("post/like")
    suspend fun likePost(@Body createPostLike: CreatePostLike? = null): Response<PostResponse>

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
     * @param lockPost  (optional)
     * @return [PostResponse]
     */
    @POST("post/lock")
    suspend fun lockPost(@Body lockPost: LockPost? = null): Response<PostResponse>

    /**
     * Log into lemmy.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param login  (optional)
     * @return [LoginResponse]
     */
    @POST("user/login")
    suspend fun login(@Body login: Login? = null): Response<LoginResponse>

    /**
     * Mark all replies as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param markAllAsRead  (optional)
     * @return [GetRepliesResponse]
     */
    @POST("user/mark_all_as_read")
    suspend fun markAllAsRead(@Body markAllAsRead: MarkAllAsRead? = null): Response<GetRepliesResponse>

    /**
     * Mark a comment as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param markCommentReplyAsRead  (optional)
     * @return [CommentReplyResponse]
     */
    @POST("comment/mark_as_read")
    suspend fun markCommentReplyAsRead(@Body markCommentReplyAsRead: MarkCommentReplyAsRead? = null): Response<CommentReplyResponse>

    /**
     * Mark a person mention as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param markPersonMentionAsRead  (optional)
     * @return [PersonMentionResponse]
     */
    @POST("user/mention/mark_as_read")
    suspend fun markPersonMentionAsRead(@Body markPersonMentionAsRead: MarkPersonMentionAsRead? = null): Response<PersonMentionResponse>

    /**
     * Mark a post as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param markPostAsRead  (optional)
     * @return [PostResponse]
     */
    @POST("post/mark_as_read")
    suspend fun markPostAsRead(@Body markPostAsRead: MarkPostAsRead? = null): Response<PostResponse>

    /**
     * Mark a private message as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param markPrivateMessageAsRead  (optional)
     * @return [PrivateMessageResponse]
     */
    @POST("private_message/mark_as_read")
    suspend fun markPrivateMessageAsRead(@Body markPrivateMessageAsRead: MarkPrivateMessageAsRead? = null): Response<PrivateMessageResponse>

    /**
     * Change your password from an email / token based reset.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param passwordChangeAfterReset  (optional)
     * @return [LoginResponse]
     */
    @POST("user/password_change")
    suspend fun passwordChangeAfterReset(@Body passwordChangeAfterReset: PasswordChangeAfterReset? = null): Response<LoginResponse>

    /**
     * Reset your password.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param passwordReset  (optional)
     * @return [kotlin.Any]
     */
    @POST("user/password_reset")
    suspend fun passwordReset(@Body passwordReset: PasswordReset? = null): Response<kotlin.Any>

    /**
     * Purge / Delete a comment from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param purgeComment  (optional)
     * @return [PurgeItemResponse]
     */
    @POST("admin/purge/comment")
    suspend fun purgeComment(@Body purgeComment: PurgeComment? = null): Response<PurgeItemResponse>

    /**
     * Purge / Delete a community from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param purgeCommunity  (optional)
     * @return [PurgeItemResponse]
     */
    @POST("admin/purge/community")
    suspend fun purgeCommunity(@Body purgeCommunity: PurgeCommunity? = null): Response<PurgeItemResponse>

    /**
     * Purge / Delete a person from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param purgePerson  (optional)
     * @return [PurgeItemResponse]
     */
    @POST("admin/purge/person")
    suspend fun purgePerson(@Body purgePerson: PurgePerson? = null): Response<PurgeItemResponse>

    /**
     * Purge / Delete a post from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param purgePost  (optional)
     * @return [PurgeItemResponse]
     */
    @POST("admin/purge/post")
    suspend fun purgePost(@Body purgePost: PurgePost? = null): Response<PurgeItemResponse>

    /**
     * Register a new user.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param register  (optional)
     * @return [LoginResponse]
     */
    @POST("user/register")
    suspend fun register(@Body register: Register? = null): Response<LoginResponse>

    /**
     * A moderator remove for a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param removeComment  (optional)
     * @return [CommentResponse]
     */
    @POST("comment/remove")
    suspend fun removeComment(@Body removeComment: RemoveComment? = null): Response<CommentResponse>

    /**
     * A moderator remove for a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param removeCommunity  (optional)
     * @return [CommunityResponse]
     */
    @POST("community/remove")
    suspend fun removeCommunity(@Body removeCommunity: RemoveCommunity? = null): Response<CommunityResponse>

    /**
     * A moderator remove for a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param removePost  (optional)
     * @return [PostResponse]
     */
    @POST("post/remove")
    suspend fun removePost(@Body removePost: RemovePost? = null): Response<PostResponse>

    /**
     * Resolve a comment report. Only a mod can do this.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param resolveCommentReport  (optional)
     * @return [CommentReportResponse]
     */
    @PUT("comment/report/resolve")
    suspend fun resolveCommentReport(@Body resolveCommentReport: ResolveCommentReport? = null): Response<CommentReportResponse>

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
     * @param resolvePostReport  (optional)
     * @return [PostReportResponse]
     */
    @PUT("post/report/resolve")
    suspend fun resolvePostReport(@Body resolvePostReport: ResolvePostReport? = null): Response<PostReportResponse>

    /**
     * Resolve a report for a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param resolvePrivateMessageReport  (optional)
     * @return [PrivateMessageReportResponse]
     */
    @PUT("private_message/report/resolve")
    suspend fun resolvePrivateMessageReport(@Body resolvePrivateMessageReport: ResolvePrivateMessageReport? = null): Response<PrivateMessageReportResponse>

    /**
     * Save a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param saveComment  (optional)
     * @return [CommentResponse]
     */
    @PUT("comment/save")
    suspend fun saveComment(@Body saveComment: SaveComment? = null): Response<CommentResponse>

    /**
     * Save a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param savePost  (optional)
     * @return [PostResponse]
     */
    @PUT("post/save")
    suspend fun savePost(@Body savePost: SavePost? = null): Response<PostResponse>

    /**
     * Save your user settings.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param saveUserSettings  (optional)
     * @return [LoginResponse]
     */
    @PUT("user/save_user_settings")
    suspend fun saveUserSettings(@Body saveUserSettings: SaveUserSettings? = null): Response<LoginResponse>

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
     * @param transferCommunity  (optional)
     * @return [GetCommunityResponse]
     */
    @POST("community/transfer")
    suspend fun transferCommunity(@Body transferCommunity: TransferCommunity? = null): Response<GetCommunityResponse>

    /**
     * Verify your email
     * 
     * Responses:
     *  - 200: OK
     *
     * @param verifyEmail  (optional)
     * @return [kotlin.Any]
     */
    @POST("user/verify_email")
    suspend fun verifyEmail(@Body verifyEmail: VerifyEmail? = null): Response<kotlin.Any>

}
