package com.rivaldo.githubuser.data.remote.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseFollowing(
	val responseFollowing: List<ResponseFollowingItem?>? = null
) : Parcelable

@Parcelize
data class ResponseFollowingItem(
	@SerializedName("gists_url")
	val gistsUrl: String? = null,
	@SerializedName("repos_url")
	val reposUrl: String? = null,
	@SerializedName("following_url")
	val followingUrl: String? = null,
	@SerializedName("starred_url")
	val starredUrl: String? = null,
	val login: String? = null,
	@SerializedName("followers_url")
	val followersUrl: String? = null,
	val type: String? = null,
	val url: String? = null,
	@SerializedName("subscriptions_url")
	val subscriptionsUrl: String? = null,
	@SerializedName("received_events_url")
	val receivedEventsUrl: String? = null,
	@SerializedName("avatar_url")
	val avatarUrl: String? = null,
	@SerializedName("events_url")
	val eventsUrl: String? = null,
	@SerializedName("html_url")
	val htmlUrl: String? = null,
	@SerializedName("site_admin")
	val siteAdmin: Boolean? = null,
	val id: Int? = null,
	@SerializedName("gravatar_id")
	val gravatarId: String? = null,
	@SerializedName("node_id")
	val nodeId: String? = null,
	@SerializedName("organizations_url")
	val organizationsUrl: String? = null
) : Parcelable
