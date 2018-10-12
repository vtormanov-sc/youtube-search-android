package com.example.youtubetest.model

import com.example.youtubetest.*
import com.example.youtubetest.model.event.YoutubeFailureEvent
import com.example.youtubetest.model.event.YoutubeItemLoadedEvent
import com.example.youtubetest.model.network.RestService
import com.example.youtubetest.model.network.ServiceGenerator
import com.example.youtubetest.model.network.response.SearchResponse
import com.example.youtubetest.model.network.response.VideoItemsResponse
import kotlinx.coroutines.experimental.*
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

class QueryHandler {

	private val TAG = QueryHandler::class.java.simpleName
	private val MAX_RESULTS = "10"
	private val TYPE_VIDEO = "video"
	private val PART_SNIPPET = "snippet"
	private val PART_CONTENT_DETAILS = "contentDetails"

	private val ERROR_MESSAGE_NO_INTERNET = "No internet connection"
	private val ERROR_MESSAGE_TIMEOUT = "Timeout"

	private var youtubeVideoModel: YoutubeVideoModel? = null
	private val youtubeVideoModelList = ArrayList<YoutubeVideoModel>()
	private lateinit var job: Job
	private val background = newFixedThreadPoolContext(1, "bg")

	private fun searchItems(queryMap: Map<String, String>): Call<SearchResponse> {
		return ServiceGenerator.createRetrofitService(RestService::class.java).getItemsLIst(queryMap)
	}

	private fun getVideoItemsInfo(queryMap: Map<String, String>): Call<VideoItemsResponse> {
		return ServiceGenerator.createRetrofitService(RestService::class.java).getContentDetails(queryMap)
	}

	fun executeSearchQuery(searchQuery: String) {
		val queryMap = mapOf(
				QUERY_MAP_KEY_KEY to API_KEY,
				QUERY_MAP_KEY_MAX_RESULTS to MAX_RESULTS,
				QUERY_MAP_KEY_QUERY to searchQuery,
				QUERY_MAP_KEY_TYPE to TYPE_VIDEO,
				QUERY_MAP_KEY_PART to PART_SNIPPET
		)
		val call = searchItems(queryMap)
		call.enqueue(object : Callback<SearchResponse> {
			override fun onFailure(call: Call<SearchResponse>, t: Throwable?) {
				if (t != null && t.localizedMessage != null) {
					var errorMessage = ""
					when (t) {
						is ConnectException -> errorMessage = ERROR_MESSAGE_NO_INTERNET
						is SocketTimeoutException -> errorMessage = ERROR_MESSAGE_TIMEOUT
						is UnknownHostException -> errorMessage = ERROR_MESSAGE_NO_INTERNET
						else -> t.stackTrace
					}
					sendFailure(errorMessage)
				}
			}

			override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
				if (response.isSuccessful) {
					executeVideoItemsQuery(response)
				} else {
					val errorMessage = response.errorBody()?.string().toString()
					sendFailure(errorMessage)
				}
			}
		})
	}

	private fun executeVideoItemsQuery(response: Response<SearchResponse>) {
		youtubeVideoModelList.clear()
		job = GlobalScope.launch(background, CoroutineStart.DEFAULT, {
			var errorMessage = ""
			response.body()?.items?.forEachIndexed { _, item ->
				val queryMap = mapOf(
						QUERY_MAP_KEY_KEY to API_KEY,
						QUERY_MAP_KEY_ID to item.id.videoId,
						QUERY_MAP_KEY_PART to PART_CONTENT_DETAILS
				)
				val call = getVideoItemsInfo(queryMap)
				val resp = call.execute()
				if (resp.isSuccessful) {
					resp.body()?.items?.forEach {
						youtubeVideoModel = YoutubeVideoModel(
								item.snippet.title,
								item.snippet.thumbnails.default.url,
								it.contentDetails.duration,
								it.id
						)
						youtubeVideoModelList.add(youtubeVideoModel!!)
					}
				} else {
					errorMessage = response.errorBody()?.string().toString()
				}
			}

			launch(Dispatchers.Main) {
				if (youtubeVideoModelList.isNotEmpty())
					EventBus.getDefault().post(YoutubeItemLoadedEvent(youtubeVideoModelList))
				else
					sendFailure(errorMessage)
			}
		})
	}

	private fun sendFailure(message: String) {
		EventBus.getDefault().post(YoutubeFailureEvent(message))
	}
}