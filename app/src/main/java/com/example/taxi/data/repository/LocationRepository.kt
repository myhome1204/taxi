package com.example.taxi.data.repository

import com.example.taxi.data.onBoard.dto.map.UserLocationDTO
import com.example.taxi.data.onBoard.service.OnBoardService
import com.example.taxi.data.ServiceConnector
import com.google.android.gms.maps.model.LatLng

class LocationRepository {

    private val onBoardService: OnBoardService = ServiceConnector.onBoardService

    suspend fun getLocations(): List<LatLng> {
        val response = onBoardService.getLocations()
        if (response.isSuccessful) {
            return response.body()?.map { locationDto ->
                LatLng(locationDto.latitude, locationDto.longitude)
            } ?: emptyList()
        } else {
            throw Exception("Failed to fetch locations: ${response.errorBody()?.string()}")
        }
    }

    suspend fun sendLocation(location: LatLng) {
        val locationDto = UserLocationDTO(location.latitude, location.longitude)
        val response = onBoardService.sendLocation(locationDto)
        if (!response.isSuccessful) {
            throw Exception("Failed to send location: ${response.errorBody()?.string()}")
        }
    }
}
