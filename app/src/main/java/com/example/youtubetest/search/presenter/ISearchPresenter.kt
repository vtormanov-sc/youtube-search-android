package com.example.youtubetest.search.presenter

import android.content.Context

interface ISearchPresenter {
	fun onCreate(context: Context)
	fun onSearchClicked(searchQuery: String)
	fun onItemSelected(videoId: String?)
}