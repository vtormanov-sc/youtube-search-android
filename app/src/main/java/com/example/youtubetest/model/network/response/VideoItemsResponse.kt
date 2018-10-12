package com.example.youtubetest.model.network.response

import com.google.gson.annotations.SerializedName


data class VideoItemsResponse(
		@SerializedName("kind") var kind: String,
		@SerializedName("etag") var etag: String,
		@SerializedName("pageInfo") var pageInfo: PageInfo,
		@SerializedName("items") var items: List<Item>
) {

	data class Item(
			@SerializedName("kind") var kind: String,
			@SerializedName("etag") var etag: String,
			@SerializedName("id") var id: String,
			@SerializedName("contentDetails") var contentDetails: ContentDetails
	) {

		data class ContentDetails(
				@SerializedName("duration") var duration: String,
				@SerializedName("dimension") var dimension: String,
				@SerializedName("definition") var definition: String,
				@SerializedName("caption") var caption: String,
				@SerializedName("licensedContent") var licensedContent: Boolean,
				@SerializedName("projection") var projection: String
		)
	}


	data class PageInfo(
			@SerializedName("totalResults") var totalResults: Int,
			@SerializedName("resultsPerPage") var resultsPerPage: Int
	)
}