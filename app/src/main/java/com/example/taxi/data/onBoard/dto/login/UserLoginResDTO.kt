package com.example.taxi.data.onBoard.dto.login

import com.example.taxi.data.BaseResponseDTO

data class UserLoginResDTO(
    override val message: String,
    val token: String

): BaseResponseDTO
