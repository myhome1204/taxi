package com.example.taxi.data.onBoard.dto.chat

import com.example.taxi.data.BaseResponseDTO

data class UnreadCount(
    override val message: String,
    val  data : Int

): BaseResponseDTO
