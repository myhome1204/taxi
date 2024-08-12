package com.example.taxi.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener
import androidx.core.view.WindowInsetsCompat
import com.example.taxi.R
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.UserApiClient.Companion as UserApiClient1


class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LoginActivity"
    }

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        KakaoSdk.init(this, "9d7a61f2965ea1362680b09c6543b799")

        val kakaoLoginButton: ImageButton = findViewById(R.id.login_kakao_login_btn_ib)
        val kakaoDirectLoginButton: LinearLayout = findViewById(R.id.login_kakao_direct_login_btn)

        // 로그인 성공 시 처리
        viewModel.loginSuccess.observe(this) { token ->
            token?.let {
                Log.i(TAG, "로그인 성공 ${it.accessToken}")
                // 사용자 정보 요청을 ViewModel에 위임
                viewModel.fetchKakaoUserInfo(it)
            }
        }

        // 로그인 또는 사용자 정보 요청 실패 시 처리
        viewModel.loginError.observe(this) { error ->
            error?.let {
                Log.e(TAG, "로그인 또는 사용자 정보 요청 실패", it)
            }
        }



        // 사용자 정보 요청 성공 시 처리
        viewModel.userInfo.observe(this) { user ->
            user?.let {
                Log.i(TAG, "사용자 정보 요청 성공")
                // WelcomeFragment로 화면 전환
                val intent = Intent(this, WelcomeFragment::class.java)
                startActivity(intent)
                finish() // 현재 액티비티 종료
            }
        }

        // 로그인 또는 사용자 정보 요청 실패 시 처리
        viewModel.loginError.observe(this) { error ->
            error?.let {
                Log.e(TAG, "로그인 또는 사용자 정보 요청 실패", it)
            }
        }

        // 카카오톡으로 로그인 버튼 클릭 시 ViewModel의 카카오톡 로그인 메서드 호출
        kakaoLoginButton.setOnClickListener {
            viewModel.kakaoLoginWithApp(this)
        }

        // 카카오 계정으로 직접 로그인 버튼 클릭 시 ViewModel의 카카오 계정 로그인 메서드 호출
        kakaoDirectLoginButton.setOnClickListener {
            viewModel.kakaoLoginWithAccount(this)
        }

        // 윈도우 인셋 적용
        setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // WelcomeFragment를 표시하는 트랜잭션 처리 (필요 시)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.login_next_fragment_container, WelcomeFragment()) // fragment_container에 WelcomeFragment를 대체합니다.
                .commitNow() // 트랜잭션을 즉시 커밋하여 프래그먼트를 추가합니다.
        }


    }
}