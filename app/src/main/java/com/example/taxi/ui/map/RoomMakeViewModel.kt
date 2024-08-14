package com.example.taxi.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taxi.data.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RoomMakeViewModel (application: Application) : AndroidViewModel(application) {

    private val _userLocations = MutableLiveData<List<LatLng>>()
    val userLocations: LiveData<List<LatLng>> get() = _userLocations

    var maxUserCount = 3 // 최대 사용자 수를 조절할 수 있는 변수

    fun fetchUserLocations() {
        viewModelScope.launch {
            while (true) {
                // 여기에 서버에서 위치 데이터를 받아오는 로직 추가
                val locations = generateDummyLocations(maxUserCount)
                _userLocations.postValue(locations)
                delay(5000L) // 5초마다 업데이트
            }
        }
    }

    private fun generateDummyLocations(userCount: Int): List<LatLng> {
        val dummyLocations = mutableListOf<LatLng>()
        if (userCount >= 1) {
            dummyLocations.add(LatLng(37.5665, 126.9780)) // User 1 위치
        }
        if (userCount >= 2) {
            dummyLocations.add(LatLng(37.5765, 126.9880)) // User 2 위치
        }
        if (userCount >= 3) {
            dummyLocations.add(LatLng(37.5610, 126.9982)) // User 3 위치
        }
        return dummyLocations
    }
}