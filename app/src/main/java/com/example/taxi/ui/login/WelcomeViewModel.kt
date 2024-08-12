package com.example.taxi.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WelcomeViewModel : ViewModel() {
    private val _notificationState = MutableLiveData<Boolean>()
    val notificationState: LiveData<Boolean> get() = _notificationState

    fun setNotificationState(enabled: Boolean) {
        _notificationState.value = enabled
    }

    fun startTaxiMate() {
        // 택시 메이트 시작 로직
        // 현재 notificationState 값을 확인
        val currentState = _notificationState.value ?: false

        // 백엔드로 API 요청을 보내는 로직
        //sendNotificationStateToBackend(currentState)
    }

    /*private fun sendNotificationStateToBackend(state: Boolean) {
// Retrofit 등을 사용하여 API 요청을 보냅니다
        val apiService = RetrofitInstance.apiService
        val call = apiService.sendNotificationState(state)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // 성공적으로 전송됨
                    // 필요한 후속 작업 수행
                } else {
                    // 실패 처리
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // 요청 실패 처리
            }
        })
    }*/
}