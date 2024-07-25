package com.example.taxi.data.onBoard.service

import com.example.taxi.data.onBoard.dto.login.UserLoginReqDTO
import com.example.taxi.data.onBoard.dto.login.UserLoginResDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OnBoardService {
    @POST("user/login/")
    suspend fun postUserLogin(
        @Body requestBody: UserLoginReqDTO
    ): Response<UserLoginResDTO>

}