package com.example.youtubetest.model.network

import com.example.youtubetest.model.network.response.SearchResponse
import com.example.youtubetest.model.network.response.VideoItemsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RestService {

	@GET("search")
	fun getItemsLIst(@QueryMap queries: Map<String, String>): Call<SearchResponse>

	@GET("videos")
	fun getContentDetails(@QueryMap queries: Map<String, String>): Call<VideoItemsResponse>
}