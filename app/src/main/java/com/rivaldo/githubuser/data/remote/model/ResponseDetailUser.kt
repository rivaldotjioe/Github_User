package com.rivaldo.githubuser.data.remote.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseDetailUser(
	@SerializedName("gists_url")
	val gistsUrl: String? = null,
	@SerializedName("repos_url")
	val reposUrl: String? = null,
	@SerializedName("following_url")
	val followingUrl: String? = null,
	@SerializedName("twitter_username")
	val twitterUsername: String? = null,
	val bio: String? = null,
	@SerializedName("created_at")
	val createdAt: String? = null,
	val login: String? = null,
	val type: String? = null,
	val blog: String? = null,
	@SerializedName("subscriptions_url")
	val subscriptionsUrl: String? = null,
	@SerializedName("updated_at")
	val updatedAt: String? = null,
	@SerializedName("site_admin")
	val siteAdmin: Boolean? = null,
	val company: String? = null,
	val id: Int? = null,
	@SerializedName("public_repos")
	val publicRepos: Int? = null,
	@SerializedName("gravatar_id")
	val gravatarId: String? = null,
	val email: String? = null,
	@SerializedName("organizations_url")
	val organizationsUrl: String? = null,
	val hireable: String? = null,
	@SerializedName("starred_url")
	val starredUrl: String? = null,
	@SerializedName("followers_url")
	val followersUrl: String? = null,
	@SerializedName("public_gists")
	val publicGists: Int? = null,
	val url: String? = null,
	@SerializedName("received_events_url")
	val receivedEventsUrl: String? = null,
	val followers: Int? = null,
	@SerializedName("avatar_url")
	val avatarUrl: String? = null,
	@SerializedName("events_url")
	val eventsUrl: String? = null,
	@SerializedName("html_url")
	val htmlUrl: String? = null,
	val following: Int? = null,
	val name: String? = null,
	val location: String? = null,
	@SerializedName("node_id")
	val nodeId: String? = null
) : Parcelable
