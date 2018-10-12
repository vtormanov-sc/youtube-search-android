package com.example.youtubetest.search.presenter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.youtubetest.YOUTUBE_PLAYER_EXTRA_KEY
import com.example.youtubetest.model.Model
import com.example.youtubetest.model.event.YoutubeFailureEvent
import com.example.youtubetest.model.event.YoutubeItemLoadedEvent
import com.example.youtubetest.search.YoutubeItemData
import com.example.youtubetest.search.view.ISearchView
import com.example.youtubetest.video.YoutubePlayerActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class SearchPresenter(private val view: ISearchView): ISearchPresenter {

	private val TAG = SearchPresenter::class.java.simpleName

	private lateinit var context: Context
	private lateinit var model: Model

	override fun onCreate(context: Context) {
		this.context = context
		view.initItemsList()
		model = Model()
		model.loadYoutubeVideoItems()
		EventBus.getDefault().register(this)
	}

	override fun onSearchClicked(searchQuery: String) {
		view.removeAllItems()
		view.showLoadingProgress()
		model.loadYoutubeVideoItems(searchQuery)
	}

	override fun onItemSelected(videoId: String?) {
		startYoutubeVideoPlayerActivity(videoId)
	}

	@Subscribe
	fun getYoutubeItemsList(event: YoutubeItemLoadedEvent) {
		val itemsList = ArrayList<YoutubeItemData>()
		event.items.forEach {
			val temp = it.videoDuration.substringAfter("PT")
			val duration = temp.toLowerCase(Locale.getDefault())
			val item = YoutubeItemData(it.videoTitle, it.videoThumbnailUrl, duration, it.videoId)
			itemsList.add(item)
		}
		view.hideLoadingProgress()
		view.setItemsList(itemsList)
	}

	@Subscribe
	fun getYoutubeFailure(event: YoutubeFailureEvent) {
		view.showToast(event.message)
		view.hideLoadingProgress()
	}

	private fun startYoutubeVideoPlayerActivity(videoId: String?){
		val intent = Intent(context, YoutubePlayerActivity::class.java)
		val bundle = Bundle().apply {
			putString(YOUTUBE_PLAYER_EXTRA_KEY, videoId)
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		intent.putExtras(bundle)
		context.startActivity(intent)
	}
}