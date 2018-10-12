package com.example.youtubetest.model.network.response

import com.google.gson.annotations.SerializedName


data class SearchResponse(
		@SerializedName("kind") var kind: String,
		@SerializedName("etag") var etag: String,
		@SerializedName("nextPageToken") var nextPageToken: String,
		@SerializedName("regionCode") var regionCode: String,
		@SerializedName("pageInfo") var pageInfo: PageInfo,
		@SerializedName("items") var items: List<Item>
) {

	data class PageInfo(
			@SerializedName("totalResults") var totalResults: Int,
			@SerializedName("resultsPerPage") var resultsPerPage: Int
	)


	data class Item(
			@SerializedName("kind") var kind: String,
			@SerializedName("etag") var etag: String,
			@SerializedName("id") var id: Id,
			@SerializedName("snippet") var snippet: Snippet
	) {

		data class Snippet(
				@SerializedName("publishedAt") var publishedAt: String,
				@SerializedName("channelId") var channelId: String,
				@SerializedName("title") var title: String,
				@SerializedName("description") var description: String,
				@SerializedName("thumbnails") var thumbnails: Thumbnails,
				@SerializedName("channelTitle") var channelTitle: String,
				@SerializedName("liveBroadcastContent") var liveBroadcastContent: String
		) {

			data class Thumbnails(
					@SerializedName("default") var default: Default,
					@SerializedName("medium") var medium: Medium,
					@SerializedName("high") var high: High
			) {

				data class Medium(
						@SerializedName("url") var url: String,
						@SerializedName("width") var width: Int,
						@SerializedName("height") var height: Int
				)


				data class Default(
						@SerializedName("url") var url: String,
						@SerializedName("width") var width: Int,
						@SerializedName("height") var height: Int
				)


				data class High(
						@SerializedName("url") var url: String,
						@SerializedName("width") var width: Int,
						@SerializedName("height") var height: Int
				)
			}
		}


		data class Id(
				@SerializedName("kind") var kind: String,
				@SerializedName("videoId") var videoId: String
		)
	}
}