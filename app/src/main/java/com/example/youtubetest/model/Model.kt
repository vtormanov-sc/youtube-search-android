package com.example.youtubetest.model

class Model {

	private val handler = QueryHandler()

	fun loadYoutubeVideoItems(searchQuery: String = "") {
		handler.executeSearchQuery(searchQuery)
	}
}