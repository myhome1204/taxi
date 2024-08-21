package com.example.taxi.ui.map
import android.os.Bundle
import com.example.taxi.R
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.taxi.databinding.ActivityMakeRoomBinding
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Space
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.commit
import com.example.taxi.ui.map.MakeRoomFragment

class MakeRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeRoomBinding
    private val makeRoomViewModel: RoomMakeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMakeRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fragment를 Activity에 붙임
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(binding.mapFragmentContainer.id, MakeRoomFragment())
            }
        }
        val layout = binding.yourLinearLayout
        layout.removeAllViews() // 기존에 추가된 뷰를 제거
        val count = 3 // 이미지의 개수를 지정

        for (i in 0 until count) {
            val imageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    48.dpToPx(), // width (이미지의 너비를 48dp로 설정)
                    48.dpToPx()  // height (이미지의 높이를 48dp로 설정)
                )
                setImageResource(R.drawable.avatar) // 사용할 이미지 리소스
            }
            layout.addView(imageView)

            // 마지막 이미지가 아니라면 Space 추가
            if (i < count - 1) {
                val space = Space(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        16.dpToPx(), // width (간격)
                        0 // height
                    )
                }
                layout.addView(space)
            }
        }





        // maxUserCount에 따라 UI 업데이트

    }





    // dp를 px로 변환하는 확장 함수
    private fun Int.dpToPx(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
}
