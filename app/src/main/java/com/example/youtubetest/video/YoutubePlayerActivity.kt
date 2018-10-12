package com.example.youtubetest.video

import android.os.Bundle
import com.example.youtubetest.API_KEY
import com.example.youtubetest.R
import com.example.youtubetest.YOUTUBE_PLAYER_EXTRA_KEY
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_youtube_player.*

class YoutubePlayerActivity: YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

	private lateinit var youtubeVideoPlayerView: YouTubePlayerView
	private var videoId: String = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val bundle: Bundle? = intent.extras
		videoId = bundle?.getString(YOUTUBE_PLAYER_EXTRA_KEY) ?: ""
		setContentView(R.layout.activity_youtube_player)
		findView()
		initPlayerView()
	}

	private fun findView(){
		youtubeVideoPlayerView = view_youtube_player
	}

	private fun initPlayerView() {
		youtubeVideoPlayerView.initialize(API_KEY, this)
	}

	override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, p: Boolean) {
		player?.loadVideo(videoId)
	}

	override fun onInitializationFailure(provider: YouTubePlayer.Provider?, result: YouTubeInitializationResult?) {

	}
}
