package com.example.taxi.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User

class LoginViewModel : ViewModel() {

    private val _loginSuccess = MutableLiveData<OAuthToken?>()
    val loginSuccess: LiveData<OAuthToken?> = _loginSuccess

    private val _loginError = MutableLiveData<Throwable?>()
    val loginError: LiveData<Throwable?> = _loginError

    private val _userInfo = MutableLiveData<User?>()
    val userInfo: LiveData<User?> = _userInfo

    fun kakaoLoginWithApp(context: Context, serviceTerms: List<String>? = null) {
        // 카카오톡 앱으로 로그인 시도
        UserApiClient.instance.loginWithKakaoTalk(
            context = context,
            serviceTerms = serviceTerms
        ) { token, error ->
            if (error != null) {
                _loginError.value = error
            } else if (token != null) {
                _loginSuccess.value = token
            }
        }
    }

    fun kakaoLoginWithAccount(context: Context, serviceTerms: List<String>? = null) {
        // 카카오 계정으로 로그인 시도
        UserApiClient.instance.loginWithKakaoAccount(
            context = context,
            serviceTerms = serviceTerms
        ) { token, error ->
            if (error != null) {
                _loginError.value = error
            } else if (token != null) {
                _loginSuccess.value = token
            }
        }
    }

    fun fetchKakaoUserInfo(token: OAuthToken) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                _loginError.value = error
            } else if (user != null) {
                _userInfo.value = user
                handleKakaoLoginSuccess(user, token)
            }
        }
    }

    fun handleKakaoLoginSuccess(user: User, token: OAuthToken) {
        // 사용자의 정보를 백엔드로 전달하거나 다른 작업을 수행합니다.
        // 예를 들어, 서버로 로그인 요청을 보내는 API 호출 등을 여기에 작성합니다.
    }

}
