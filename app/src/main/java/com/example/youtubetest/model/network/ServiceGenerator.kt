package com.example.youtubetest.model.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {

	companion object {

		private val TAG = ServiceGenerator::class.java.simpleName
		private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
		private val gson = GsonBuilder().create()

		lateinit var retrofit: Retrofit
		private var baseUrl: String = "https://www.googleapis.com/youtube/v3/"

		private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()

		fun <S> createRetrofitService(serviceClass: Class<S>): S {
			retrofit = retrofitBuilder
					.baseUrl(baseUrl)
					.addConverterFactory(GsonConverterFactory.create(gson))
					.client(getOkHttpClient())
					.build()
			return retrofit.create(serviceClass)
		}

		private fun getOkHttpClient(): OkHttpClient {
				val loggingInterceptor = HttpLoggingInterceptor()
				loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
				httpClient.addInterceptor(loggingInterceptor)
			return httpClient.build()
		}
	}
}