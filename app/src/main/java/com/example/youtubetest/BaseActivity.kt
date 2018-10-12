package com.example.youtubetest

import android.support.v7.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {
	abstract fun findView()
	abstract fun setListeners()
}