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
import com.neaniesoft.warami.api.models.GetBannedPersons
import com.neaniesoft.warami.api.models.GetCaptcha
import com.neaniesoft.warami.api.models.GetCaptchaResponse
import com.neaniesoft.warami.api.models.GetComment
import com.neaniesoft.warami.api.models.GetComments
import com.neaniesoft.warami.api.models.GetCommentsResponse
import com.neaniesoft.warami.api.models.GetCommunityResponse
import com.neaniesoft.warami.api.models.GetFederatedInstances
import com.neaniesoft.warami.api.models.GetFederatedInstancesResponse
import com.neaniesoft.warami.api.models.GetModlog
import com.neaniesoft.warami.api.models.GetModlogResponse
import com.neaniesoft.warami.api.models.GetPersonDetails
import com.neaniesoft.warami.api.models.GetPersonDetailsResponse
import com.neaniesoft.warami.api.models.GetPersonMentions
import com.neaniesoft.warami.api.models.GetPersonMentionsResponse
import com.neaniesoft.warami.api.models.GetPosts
import com.neaniesoft.warami.api.models.GetPostsResponse
import com.neaniesoft.warami.api.models.GetPrivateMessages
import com.neaniesoft.warami.api.models.GetReplies
import com.neaniesoft.warami.api.models.GetRepliesResponse
import com.neaniesoft.warami.api.models.GetReportCount
import com.neaniesoft.warami.api.models.GetReportCountResponse
import com.neaniesoft.warami.api.models.GetSiteMetadata
import com.neaniesoft.warami.api.models.GetSiteMetadataResponse
import com.neaniesoft.warami.api.models.GetSiteResponse
import com.neaniesoft.warami.api.models.GetUnreadCount
import com.neaniesoft.warami.api.models.GetUnreadCountResponse
import com.neaniesoft.warami.api.models.GetUnreadRegistrationApplicationCount
import com.neaniesoft.warami.api.models.GetUnreadRegistrationApplicationCountResponse
import com.neaniesoft.warami.api.models.LeaveAdmin
import com.neaniesoft.warami.api.models.ListCommentReports
import com.neaniesoft.warami.api.models.ListCommentReportsResponse
import com.neaniesoft.warami.api.models.ListCommunities
import com.neaniesoft.warami.api.models.ListCommunitiesResponse
import com.neaniesoft.warami.api.models.ListPostReports
import com.neaniesoft.warami.api.models.ListPostReportsResponse
import com.neaniesoft.warami.api.models.ListPrivateMessageReports
import com.neaniesoft.warami.api.models.ListPrivateMessageReportsResponse
import com.neaniesoft.warami.api.models.ListRegistrationApplications
import com.neaniesoft.warami.api.models.ListRegistrationApplicationsResponse
import com.neaniesoft.warami.api.models.LockPost
import com.neaniesoft.warami.api.models.Login
import com.neaniesoft.warami.api.models.LoginResponse
import com.neaniesoft.warami.api.models.MarkAllAsRead
import com.neaniesoft.warami.api.models.MarkCommentReplyAsRead
import com.neaniesoft.warami.api.models.MarkPersonMentionAsRead
import com.neaniesoft.warami.api.models.MarkPostAsRead
import com.neaniesoft.warami.api.models.MarkPrivateMessageAsRead
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
import com.neaniesoft.warami.api.models.PurgePerson
import com.neaniesoft.warami.api.models.PurgePost
import com.neaniesoft.warami.api.models.Register
import com.neaniesoft.warami.api.models.RegistrationApplicationResponse
import com.neaniesoft.warami.api.models.RemoveComment
import com.neaniesoft.warami.api.models.RemoveCommunity
import com.neaniesoft.warami.api.models.RemovePost
import com.neaniesoft.warami.api.models.ResolveCommentReport
import com.neaniesoft.warami.api.models.ResolveObject
import com.neaniesoft.warami.api.models.ResolveObjectResponse
import com.neaniesoft.warami.api.models.ResolvePostReport
import com.neaniesoft.warami.api.models.ResolvePrivateMessageReport
import com.neaniesoft.warami.api.models.SaveComment
import com.neaniesoft.warami.api.models.SavePost
import com.neaniesoft.warami.api.models.SaveUserSettings
import com.neaniesoft.warami.api.models.Search
import com.neaniesoft.warami.api.models.SearchResponse
import com.neaniesoft.warami.api.models.SiteResponse
import com.neaniesoft.warami.api.models.TransferCommunity
import com.neaniesoft.warami.api.models.VerifyEmail

interface DefaultApi {
    /**
     * Add an admin to your site.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param addAdmin 
     * @return [AddAdminResponse]
     */
    @POST("admin/add")
    suspend fun addAdmin(@Body addAdmin: AddAdmin): Response<AddAdminResponse>

    /**
     * Add a moderator to your community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param addModToCommunity 
     * @return [AddModToCommunityResponse]
     */
    @POST("community/mod")
    suspend fun addModToCommunity(@Body addModToCommunity: AddModToCommunity): Response<AddModToCommunityResponse>

    /**
     * Approve a registration application
     * 
     * Responses:
     *  - 200: OK
     *
     * @param approveRegistrationApplication 
     * @return [RegistrationApplicationResponse]
     */
    @PUT("admin/registration_application/approve")
    suspend fun approveRegistrationApplication(@Body approveRegistrationApplication: ApproveRegistrationApplication): Response<RegistrationApplicationResponse>

    /**
     * Ban a user from a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param banFromCommunity 
     * @return [BanFromCommunityResponse]
     */
    @POST("community/ban_user")
    suspend fun banFromCommunity(@Body banFromCommunity: BanFromCommunity): Response<BanFromCommunityResponse>

    /**
     * Ban a person from your site.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param banPerson 
     * @return [BanPersonResponse]
     */
    @POST("user/ban")
    suspend fun banPerson(@Body banPerson: BanPerson): Response<BanPersonResponse>

    /**
     * Block a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param blockCommunity 
     * @return [BlockCommunityResponse]
     */
    @POST("community/block")
    suspend fun blockCommunity(@Body blockCommunity: BlockCommunity): Response<BlockCommunityResponse>

    /**
     * Block a person.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param blockPerson 
     * @return [BlockPersonResponse]
     */
    @POST("user/block")
    suspend fun blockPerson(@Body blockPerson: BlockPerson): Response<BlockPersonResponse>

    /**
     * Change your user password.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param changePassword 
     * @return [LoginResponse]
     */
    @PUT("user/change_password")
    suspend fun changePassword(@Body changePassword: ChangePassword): Response<LoginResponse>

    /**
     * Report a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createCommentReport 
     * @return [CommentReportResponse]
     */
    @POST("comment/report")
    suspend fun createCommentReport(@Body createCommentReport: CreateCommentReport): Response<CommentReportResponse>

    /**
     * Report a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createPostReport 
     * @return [PostReportResponse]
     */
    @POST("post/report")
    suspend fun createPostReport(@Body createPostReport: CreatePostReport): Response<PostReportResponse>

    /**
     * Create a report for a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createPrivateMessageReport 
     * @return [PrivateMessageReportResponse]
     */
    @POST("private_message/report")
    suspend fun createPrivateMessageReport(@Body createPrivateMessageReport: CreatePrivateMessageReport): Response<PrivateMessageReportResponse>

    /**
     * Delete your account.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param deleteAccount 
     * @return [kotlin.Any]
     */
    @POST("user/delete_account")
    suspend fun deleteAccount(@Body deleteAccount: DeleteAccount): Response<kotlin.Any>

    /**
     * Delete a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param deleteComment 
     * @return [CommentResponse]
     */
    @POST("comment/delete")
    suspend fun deleteComment(@Body deleteComment: DeleteComment): Response<CommentResponse>

    /**
     * Delete a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param deleteCommunity 
     * @return [CommunityResponse]
     */
    @POST("community/delete")
    suspend fun deleteCommunity(@Body deleteCommunity: DeleteCommunity): Response<CommunityResponse>

    /**
     * Delete a custom emoji
     * 
     * Responses:
     *  - 200: OK
     *
     * @param deleteCustomEmoji 
     * @return [DeleteCustomEmojiResponse]
     */
    @POST("custom_emoji/delete")
    suspend fun deleteCustomEmoji(@Body deleteCustomEmoji: DeleteCustomEmoji): Response<DeleteCustomEmojiResponse>

    /**
     * Delete a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param deletePost 
     * @return [PostResponse]
     */
    @POST("post/delete")
    suspend fun deletePost(@Body deletePost: DeletePost): Response<PostResponse>

    /**
     * Delete a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param deletePrivateMessage 
     * @return [PrivateMessageResponse]
     */
    @POST("private_message/delete")
    suspend fun deletePrivateMessage(@Body deletePrivateMessage: DeletePrivateMessage): Response<PrivateMessageResponse>

    /**
     * Distinguishes a comment (speak as moderator)
     * 
     * Responses:
     *  - 200: OK
     *
     * @param distinguishComment 
     * @return [CommentResponse]
     */
    @POST("comment/distinguish")
    suspend fun distinguishComment(@Body distinguishComment: DistinguishComment): Response<CommentResponse>

    /**
     * Edit a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param editCommunity 
     * @return [CommunityResponse]
     */
    @PUT("community")
    suspend fun editCommunity(@Body editCommunity: EditCommunity): Response<CommunityResponse>

    /**
     * Edit an existing custom emoji
     * 
     * Responses:
     *  - 200: OK
     *
     * @param editCustomEmoji 
     * @return [CustomEmojiResponse]
     */
    @PUT("custom_emoji")
    suspend fun editCustomEmoji(@Body editCustomEmoji: EditCustomEmoji): Response<CustomEmojiResponse>

    /**
     * Edit a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param editPost 
     * @return [PostResponse]
     */
    @PUT("post")
    suspend fun editPost(@Body editPost: EditPost): Response<PostResponse>

    /**
     * Edit a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param editPrivateMessage 
     * @return [PrivateMessageResponse]
     */
    @PUT("private_message")
    suspend fun editPrivateMessage(@Body editPrivateMessage: EditPrivateMessage): Response<PrivateMessageResponse>

    /**
     * Edit your site.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param editSite 
     * @return [SiteResponse]
     */
    @PUT("site")
    suspend fun editSite(@Body editSite: EditSite): Response<SiteResponse>

    /**
     * A moderator can feature a community post ( IE stick it to the top of a community ).
     * 
     * Responses:
     *  - 200: OK
     *
     * @param featurePost 
     * @return [PostResponse]
     */
    @POST("post/feature")
    suspend fun featurePost(@Body featurePost: FeaturePost): Response<PostResponse>

    /**
     * Follow / subscribe to a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param followCommunity 
     * @return [CommunityResponse]
     */
    @POST("community/follow")
    suspend fun followCommunity(@Body followCommunity: FollowCommunity): Response<CommunityResponse>

    /**
     * Get a list of banned users
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getBannedPersons 
     * @return [BannedPersonsResponse]
     */
    @GET("user/banned")
    suspend fun getBannedPersons(@Body getBannedPersons: GetBannedPersons): Response<BannedPersonsResponse>

    /**
     * Fetch a Captcha.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getCaptcha 
     * @return [GetCaptchaResponse]
     */
    @GET("user/get_captcha")
    suspend fun getCaptcha(@Body getCaptcha: GetCaptcha): Response<GetCaptchaResponse>

    /**
     * Get / fetch comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getComment 
     * @return [CommentResponse]
     */
    @GET("comment")
    suspend fun getComment(@Body getComment: GetComment): Response<CommentResponse>

    /**
     * Get / fetch comments.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getComments 
     * @return [GetCommentsResponse]
     */
    @GET("comment/list")
    suspend fun getComments(@Body getComments: GetComments): Response<GetCommentsResponse>

    /**
     * Fetch federated instances.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getFederatedInstances 
     * @return [GetFederatedInstancesResponse]
     */
    @GET("federated_instances")
    suspend fun getFederatedInstances(@Body getFederatedInstances: GetFederatedInstances): Response<GetFederatedInstancesResponse>

    /**
     * Get the modlog.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getModlog 
     * @return [GetModlogResponse]
     */
    @GET("modlog")
    suspend fun getModlog(@Body getModlog: GetModlog): Response<GetModlogResponse>

    /**
     * Get the details for a person.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getPersonDetails 
     * @return [GetPersonDetailsResponse]
     */
    @GET("user")
    suspend fun getPersonDetails(@Body getPersonDetails: GetPersonDetails): Response<GetPersonDetailsResponse>

    /**
     * Get mentions for your user.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getPersonMentions 
     * @return [GetPersonMentionsResponse]
     */
    @GET("user/mention")
    suspend fun getPersonMentions(@Body getPersonMentions: GetPersonMentions): Response<GetPersonMentionsResponse>

    /**
     * Get / fetch posts, with various filters.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getPosts 
     * @return [GetPostsResponse]
     */
    @GET("post/list")
    suspend fun getPosts(@Body getPosts: GetPosts): Response<GetPostsResponse>

    /**
     * Get / fetch private messages.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getPrivateMessages 
     * @return [PrivateMessagesResponse]
     */
    @GET("private_message/list")
    suspend fun getPrivateMessages(@Body getPrivateMessages: GetPrivateMessages): Response<PrivateMessagesResponse>

    /**
     * Get comment replies.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getReplies 
     * @return [GetRepliesResponse]
     */
    @GET("user/replies")
    suspend fun getReplies(@Body getReplies: GetReplies): Response<GetRepliesResponse>

    /**
     * Get counts for your reports
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getReportCount 
     * @return [GetReportCountResponse]
     */
    @GET("user/report_count")
    suspend fun getReportCount(@Body getReportCount: GetReportCount): Response<GetReportCountResponse>

    /**
     * Fetch metadata for any given site.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getSiteMetadata 
     * @return [GetSiteMetadataResponse]
     */
    @GET("post/site_metadata")
    suspend fun getSiteMetadata(@Body getSiteMetadata: GetSiteMetadata): Response<GetSiteMetadataResponse>

    /**
     * Get your unread counts
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getUnreadCount 
     * @return [GetUnreadCountResponse]
     */
    @GET("user/unread_count")
    suspend fun getUnreadCount(@Body getUnreadCount: GetUnreadCount): Response<GetUnreadCountResponse>

    /**
     * Get the unread registration applications count.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param getUnreadRegistrationApplicationCount 
     * @return [GetUnreadRegistrationApplicationCountResponse]
     */
    @GET("admin/registration_application/count")
    suspend fun getUnreadRegistrationApplicationCount(@Body getUnreadRegistrationApplicationCount: GetUnreadRegistrationApplicationCount): Response<GetUnreadRegistrationApplicationCountResponse>

    /**
     * Leave the Site admins.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param leaveAdmin 
     * @return [GetSiteResponse]
     */
    @POST("user/leave_admin")
    suspend fun leaveAdmin(@Body leaveAdmin: LeaveAdmin): Response<GetSiteResponse>

    /**
     * Like / vote on a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createCommentLike 
     * @return [CommentResponse]
     */
    @POST("comment/like")
    suspend fun likeComment(@Body createCommentLike: CreateCommentLike): Response<CommentResponse>

    /**
     * Like / vote on a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createPostLike 
     * @return [PostResponse]
     */
    @POST("post/like")
    suspend fun likePost(@Body createPostLike: CreatePostLike): Response<PostResponse>

    /**
     * List comment reports.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param listCommentReports 
     * @return [ListCommentReportsResponse]
     */
    @GET("comment/report/list")
    suspend fun listCommentReports(@Body listCommentReports: ListCommentReports): Response<ListCommentReportsResponse>

    /**
     * List communities, with various filters.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param listCommunities 
     * @return [ListCommunitiesResponse]
     */
    @GET("community/list")
    suspend fun listCommunities(@Body listCommunities: ListCommunities): Response<ListCommunitiesResponse>

    /**
     * List post reports.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param listPostReports 
     * @return [ListPostReportsResponse]
     */
    @GET("post/report/list")
    suspend fun listPostReports(@Body listPostReports: ListPostReports): Response<ListPostReportsResponse>

    /**
     * List private message reports.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param listPrivateMessageReports 
     * @return [ListPrivateMessageReportsResponse]
     */
    @GET("private_message/report/list")
    suspend fun listPrivateMessageReports(@Body listPrivateMessageReports: ListPrivateMessageReports): Response<ListPrivateMessageReportsResponse>

    /**
     * List the registration applications.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param listRegistrationApplications 
     * @return [ListRegistrationApplicationsResponse]
     */
    @GET("admin/registration_application/list")
    suspend fun listRegistrationApplications(@Body listRegistrationApplications: ListRegistrationApplications): Response<ListRegistrationApplicationsResponse>

    /**
     * A moderator can lock a post ( IE disable new comments ).
     * 
     * Responses:
     *  - 200: OK
     *
     * @param lockPost 
     * @return [PostResponse]
     */
    @POST("post/lock")
    suspend fun lockPost(@Body lockPost: LockPost): Response<PostResponse>

    /**
     * Log into lemmy.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param login 
     * @return [LoginResponse]
     */
    @POST("user/login")
    suspend fun login(@Body login: Login): Response<LoginResponse>

    /**
     * Mark all replies as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param markAllAsRead 
     * @return [GetRepliesResponse]
     */
    @POST("user/mark_all_as_read")
    suspend fun markAllAsRead(@Body markAllAsRead: MarkAllAsRead): Response<GetRepliesResponse>

    /**
     * Mark a comment as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param markCommentReplyAsRead 
     * @return [CommentReplyResponse]
     */
    @POST("comment/mark_as_read")
    suspend fun markCommentReplyAsRead(@Body markCommentReplyAsRead: MarkCommentReplyAsRead): Response<CommentReplyResponse>

    /**
     * Mark a person mention as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param markPersonMentionAsRead 
     * @return [PersonMentionResponse]
     */
    @POST("user/mention/mark_as_read")
    suspend fun markPersonMentionAsRead(@Body markPersonMentionAsRead: MarkPersonMentionAsRead): Response<PersonMentionResponse>

    /**
     * Mark a post as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param markPostAsRead 
     * @return [PostResponse]
     */
    @POST("post/mark_as_read")
    suspend fun markPostAsRead(@Body markPostAsRead: MarkPostAsRead): Response<PostResponse>

    /**
     * Mark a private message as read.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param markPrivateMessageAsRead 
     * @return [PrivateMessageResponse]
     */
    @POST("private_message/mark_as_read")
    suspend fun markPrivateMessageAsRead(@Body markPrivateMessageAsRead: MarkPrivateMessageAsRead): Response<PrivateMessageResponse>

    /**
     * Change your password from an email / token based reset.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param passwordChangeAfterReset 
     * @return [LoginResponse]
     */
    @POST("user/password_change")
    suspend fun passwordChangeAfterReset(@Body passwordChangeAfterReset: PasswordChangeAfterReset): Response<LoginResponse>

    /**
     * Reset your password.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param passwordReset 
     * @return [kotlin.Any]
     */
    @POST("user/password_reset")
    suspend fun passwordReset(@Body passwordReset: PasswordReset): Response<kotlin.Any>

    /**
     * Purge / Delete a comment from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param purgeComment 
     * @return [kotlin.Any]
     */
    @POST("admin/purge/comment")
    suspend fun purgeComment(@Body purgeComment: PurgeComment): Response<kotlin.Any>

    /**
     * Purge / Delete a community from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param purgeCommunity 
     * @return [kotlin.Any]
     */
    @POST("admin/purge/community")
    suspend fun purgeCommunity(@Body purgeCommunity: PurgeCommunity): Response<kotlin.Any>

    /**
     * Purge / Delete a person from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param purgePerson 
     * @return [kotlin.Any]
     */
    @POST("admin/purge/person")
    suspend fun purgePerson(@Body purgePerson: PurgePerson): Response<kotlin.Any>

    /**
     * Purge / Delete a post from the database.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param purgePost 
     * @return [kotlin.Any]
     */
    @POST("admin/purge/post")
    suspend fun purgePost(@Body purgePost: PurgePost): Response<kotlin.Any>

    /**
     * Register a new user.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param register 
     * @return [LoginResponse]
     */
    @POST("user/register")
    suspend fun register(@Body register: Register): Response<LoginResponse>

    /**
     * A moderator remove for a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param removeComment 
     * @return [CommentResponse]
     */
    @POST("comment/remove")
    suspend fun removeComment(@Body removeComment: RemoveComment): Response<CommentResponse>

    /**
     * A moderator remove for a community.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param removeCommunity 
     * @return [CommunityResponse]
     */
    @POST("community/remove")
    suspend fun removeCommunity(@Body removeCommunity: RemoveCommunity): Response<CommunityResponse>

    /**
     * A moderator remove for a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param removePost 
     * @return [PostResponse]
     */
    @POST("post/remove")
    suspend fun removePost(@Body removePost: RemovePost): Response<PostResponse>

    /**
     * Resolve a comment report. Only a mod can do this.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param resolveCommentReport 
     * @return [CommentReportResponse]
     */
    @PUT("comment/report/resolve")
    suspend fun resolveCommentReport(@Body resolveCommentReport: ResolveCommentReport): Response<CommentReportResponse>

    /**
     * Fetch a non-local / federated object.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param resolveObject 
     * @return [ResolveObjectResponse]
     */
    @GET("resolve_object")
    suspend fun resolveObject(@Body resolveObject: ResolveObject): Response<ResolveObjectResponse>

    /**
     * Resolve a post report. Only a mod can do this.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param resolvePostReport 
     * @return [PostReportResponse]
     */
    @PUT("post/report/resolve")
    suspend fun resolvePostReport(@Body resolvePostReport: ResolvePostReport): Response<PostReportResponse>

    /**
     * Resolve a report for a private message.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param resolvePrivateMessageReport 
     * @return [PrivateMessageReportResponse]
     */
    @PUT("private_message/report/resolve")
    suspend fun resolvePrivateMessageReport(@Body resolvePrivateMessageReport: ResolvePrivateMessageReport): Response<PrivateMessageReportResponse>

    /**
     * Save a comment.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param saveComment 
     * @return [CommentResponse]
     */
    @PUT("comment/save")
    suspend fun saveComment(@Body saveComment: SaveComment): Response<CommentResponse>

    /**
     * Save a post.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param savePost 
     * @return [PostResponse]
     */
    @PUT("post/save")
    suspend fun savePost(@Body savePost: SavePost): Response<PostResponse>

    /**
     * Save your user settings.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param saveUserSettings 
     * @return [LoginResponse]
     */
    @PUT("user/save_user_settings")
    suspend fun saveUserSettings(@Body saveUserSettings: SaveUserSettings): Response<LoginResponse>

    /**
     * Search lemmy.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param search 
     * @return [SearchResponse]
     */
    @GET("search")
    suspend fun search(@Body search: Search): Response<SearchResponse>

    /**
     * Transfer your community to an existing moderator.
     * 
     * Responses:
     *  - 200: OK
     *
     * @param transferCommunity 
     * @return [GetCommunityResponse]
     */
    @POST("community/transfer")
    suspend fun transferCommunity(@Body transferCommunity: TransferCommunity): Response<GetCommunityResponse>

    /**
     * Verify your email
     * 
     * Responses:
     *  - 200: OK
     *
     * @param verifyEmail 
     * @return [kotlin.Any]
     */
    @POST("user/verify_email")
    suspend fun verifyEmail(@Body verifyEmail: VerifyEmail): Response<kotlin.Any>

}
