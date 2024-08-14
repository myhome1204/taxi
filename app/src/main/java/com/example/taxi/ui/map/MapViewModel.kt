package com.example.taxi.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taxi.data.ServiceConnector
import com.example.taxi.data.onBoard.dto.chat.UnreadCount
import com.google.android.gms.maps.model.LatLng

class MapViewModel : ViewModel() {

    private val _startLatLng = MutableLiveData<LatLng>()
    val startLatLng: LiveData<LatLng> = _startLatLng

    private val _endLatLng = MutableLiveData<LatLng>()
    val endLatLng: LiveData<LatLng> = _endLatLng

    fun setStartLatLng(latLng: LatLng) {
        _startLatLng.value = latLng
    }

    fun setEndLatLng(latLng: LatLng) {
        _endLatLng.value = latLng
    }

    fun getLocations(): List<Pair<LatLng, LatLng>> {
        // 실제 데이터나 더미 데이터 반환
        return listOf(
            Pair(LatLng(37.5665, 126.9780), LatLng(37.5765, 126.9880)), // 서울역 -> 명동
            Pair(LatLng(37.5510, 126.9882), LatLng(37.5610, 126.9982))  // 남대문 -> 동대문
        )
    }

    val unreadCount = liveData {
        try {
            val response = ServiceConnector.onBoardService.chatUnreadCountGet()
            emit(response)
        } catch (e: Exception) {
            // 에러 처리
            emit(null)
        }
    }
}
