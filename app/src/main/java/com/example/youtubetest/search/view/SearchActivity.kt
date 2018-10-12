package com.example.youtubetest.search.view

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import com.example.youtubetest.BaseActivity
import com.example.youtubetest.R
import com.example.youtubetest.search.YoutubeItemData
import com.example.youtubetest.search.presenter.SearchPresenter
import com.example.youtubetest.search.view.adapter.ItemClickListener
import com.example.youtubetest.search.view.adapter.YoutubeVideoItemAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity: BaseActivity(),
		ISearchView,
		SearchView.OnQueryTextListener,
		ItemClickListener
{

	private val TAG = SearchActivity::class.java.simpleName

	private lateinit var searchView: SearchView
	private lateinit var itemsList: RecyclerView
	private lateinit var pbLoading: ProgressBar
	private var presenter: SearchPresenter? = null
	private var itemsListAdapter: YoutubeVideoItemAdapter? = null
	private var layoutListManager: LinearLayoutManager? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search)
		findView()
		setListeners()
		presenter = SearchPresenter(this)
		presenter?.onCreate(applicationContext)
	}

	override fun findView() {
		searchView = view_search_videos
		itemsList = rv_items_list
		pbLoading = pb_loading
	}

	override fun setListeners() {
		searchView.setOnQueryTextListener(this)
	}

	override fun onQueryTextSubmit(str: String?): Boolean {
		presenter?.onSearchClicked(str.toString())
		return true
	}

	override fun onQueryTextChange(str: String?): Boolean = false

	override fun initItemsList() {
		itemsListAdapter = YoutubeVideoItemAdapter(this, this)
		layoutListManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
		itemsList.setHasFixedSize(true)
		itemsList.layoutManager = layoutListManager
		itemsList.itemAnimator = DefaultItemAnimator()
		itemsList.adapter = itemsListAdapter
	}

	override fun setItemsList(items: List<YoutubeItemData>) {
		itemsListAdapter?.setItemsList(items)
	}

	override fun removeAllItems() {
		itemsListAdapter?.clearAll()
	}

	override fun showLoadingProgress() {
		pbLoading.visibility = View.VISIBLE
	}

	override fun hideLoadingProgress() {
		pbLoading.visibility = View.GONE
	}

	override fun onClick(view: View, position: Int) {
		val itemVideoId = itemsListAdapter?.getItemByPosition(position)?.videoId
		presenter?.onItemSelected(itemVideoId)
	}

	override fun showToast(message: String, duration: Int) {
		Toast.makeText(this, message, duration).show()
	}
}
