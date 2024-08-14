package com.example.taxi.data.onBoard.service

import com.example.taxi.data.onBoard.dto.chat.UnreadCount
import com.example.taxi.data.onBoard.dto.login.UserLoginReqDTO
import com.example.taxi.data.onBoard.dto.login.UserLoginResDTO
import com.example.taxi.data.onBoard.dto.map.UserLocationDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OnBoardService {
    @POST("user/login/")
    suspend fun postUserLogin(
        @Body requestBody: UserLoginReqDTO
    ): Response<UserLoginResDTO>
    @GET("locations")
    suspend fun getLocations(): Response<List<UserLocationDTO>>

    @POST("locations")
    suspend fun sendLocation(
        @Body location: UserLocationDTO
    ): Response<Unit>

    @GET("/chat/unread-count")
    suspend fun chatUnreadCountGet(): UnreadCount
}