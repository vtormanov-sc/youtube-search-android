package com.example.youtubetest.search.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.youtubetest.R

class YoutubeVideoViewHolder(
		itemView: View,
		private val clickListener: ItemClickListener?
): RecyclerView.ViewHolder(itemView), View.OnClickListener {

	var ivThumbnail: ImageView = itemView.findViewById(R.id.img_thumbnail)
	var tvVideoTitle: TextView = itemView.findViewById(R.id.tv_video_title)
	var tvVideoDuration: TextView = itemView.findViewById(R.id.tv_video_duration)

	init {
	    itemView.setOnClickListener(this)
	}

	override fun onClick(v: View?) {
		clickListener?.onClick(v!!, adapterPosition)
	}
}