package com.example.youtubetest.search.view

import android.widget.Toast
import com.example.youtubetest.search.YoutubeItemData

interface ISearchView {
	fun initItemsList()
	fun setItemsList(items: List<YoutubeItemData>)
	fun removeAllItems()
	fun showLoadingProgress()
	fun hideLoadingProgress()
	fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT)
}