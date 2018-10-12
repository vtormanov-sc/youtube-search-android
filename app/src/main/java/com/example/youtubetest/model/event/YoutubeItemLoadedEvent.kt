package com.example.youtubetest.model.event

import com.example.youtubetest.model.YoutubeVideoModel

data class YoutubeItemLoadedEvent(var items: List<YoutubeVideoModel>)