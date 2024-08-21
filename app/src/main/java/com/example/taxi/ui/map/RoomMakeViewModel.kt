package com.example.taxi.ui.map
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RoomMakeViewModel(application: Application) : AndroidViewModel(application) {

    private val _userLocations = MutableLiveData<List<LatLng>>()
    val userLocations: LiveData<List<LatLng>> get() = _userLocations

    private val _maxUserCount = MutableLiveData<Int>()
    val maxUserCount: LiveData<Int> get() = _maxUserCount

    init {
        _maxUserCount.value = 2 // 기본 값으로 2명 설정
    }

    fun setMaxUserCount(count: Int) {
        _maxUserCount.value = count
        fetchUserLocations()
    }

    fun fetchUserLocations() {
        viewModelScope.launch {
            while (true) {
                val locations = generateDummyLocations(_maxUserCount.value ?: 2)
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
        // 필요 시 더 많은 사용자 위치를 추가할 수 있음
        return dummyLocations
    }
}
