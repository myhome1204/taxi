package com.example.taxi.data

import com.example.taxi.data.onBoard.service.OnBoardService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceConnector {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("예시입니다")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val onBoardService: OnBoardService = retrofit.create(OnBoardService::class.java)
}