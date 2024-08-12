package com.example.taxi.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.taxi.R
import com.example.taxi.ui.login.LoginActivity.Companion

class WelcomeFragment : Fragment() {

        companion object {
            private const val TAG = "WelcomeFragment"
        }

        private val viewModel: WelcomeViewModel by viewModels()

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_welcome, container, false)
        }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taxiMateNotificationButtonNo: ImageButton = view.findViewById(R.id.welcome_notification_check_btn_no_ib)
        val taxiMateNotificationButtonYes: ImageButton = view.findViewById(R.id.welcome_notification_check_btn_yes_ib)

            taxiMateNotificationButtonNo.setOnClickListener {
                taxiMateNotificationButtonYes.visibility = View.VISIBLE
                taxiMateNotificationButtonNo.visibility = View.INVISIBLE
                viewModel.setNotificationState(true) // 뷰모델로 상태 전달
            }

            taxiMateNotificationButtonYes.setOnClickListener {
                taxiMateNotificationButtonNo.visibility = View.VISIBLE
                taxiMateNotificationButtonYes.visibility = View.INVISIBLE
                viewModel.setNotificationState(false) // 뷰모델로 상태 전달
            }

            val startTaxiMateButton: ImageButton = view.findViewById(R.id.welcome_start_taximate_btn_ib)

            startTaxiMateButton.setOnClickListener {
                // 뷰모델의 액션 호출
                viewModel.startTaxiMate()
            }

            // 뷰모델의 상태를 관찰하여 UI를 업데이트할 수 있습니다.
            viewModel.notificationState.observe(viewLifecycleOwner) { state ->
                // state에 따라 UI 업데이트
                if (state) {
                    taxiMateNotificationButtonYes.visibility = View.VISIBLE
                    taxiMateNotificationButtonNo.visibility = View.INVISIBLE
                } else {
                    taxiMateNotificationButtonNo.visibility = View.VISIBLE
                    taxiMateNotificationButtonYes.visibility = View.INVISIBLE
                }
            }
        }
    }