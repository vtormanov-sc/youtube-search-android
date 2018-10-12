package com.example.youtubetest.search.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.youtubetest.R
import com.example.youtubetest.model.YoutubeVideoModel
import com.example.youtubetest.search.YoutubeItemData

class YoutubeVideoItemAdapter(
		private val context: Context,
		private val itemClickListener: ItemClickListener
): RecyclerView.Adapter<YoutubeVideoViewHolder>() {

	private var youtubeVideoList: ArrayList<YoutubeItemData> = ArrayList()
	private var youtubeVideoItem: YoutubeItemData? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeVideoViewHolder {
		val itemView = LayoutInflater
				.from(parent.context)
				.inflate(R.layout.layout_video_info, parent, false)
		return YoutubeVideoViewHolder(itemView, itemClickListener)
	}

	override fun getItemCount(): Int = youtubeVideoList.size

	override fun onBindViewHolder(holder: YoutubeVideoViewHolder, position: Int) {
		youtubeVideoItem = youtubeVideoList[holder.adapterPosition]
		Glide.with(context).load(youtubeVideoItem?.videoThumbnailUrl).into(holder.ivThumbnail)
		holder.tvVideoTitle.text = youtubeVideoItem?.videoTitle
		holder.tvVideoDuration.text = youtubeVideoItem?.videoDuration
	}

	fun setItemsList(items: List<YoutubeItemData>) {
		youtubeVideoList.addAll(items)
		notifyDataSetChanged()
	}

    fun clearAll() {
        youtubeVideoList.clear()
        notifyDataSetChanged()
    }

	fun getItemByPosition(position: Int): YoutubeItemData = youtubeVideoList[position]
}