package com.example.taxi.ui.map

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.taxi.databinding.ActivityMakeRoomBinding
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.example.taxi.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MakeRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeRoomBinding
    private val makeRoomViewModel: RoomMakeViewModel by viewModels()
    private lateinit var googleMap: GoogleMap
    private lateinit var createRoomButton: Button
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMakeRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // 최대 사용자 수 설정
        makeRoomViewModel.maxUserCount = 2 // 예시로 최대 사용자 수를 2명으로 설정

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.map_fragment_container, MakeRoomFragment())
            }
        }

        makeRoomViewModel.userLocations.observe(this, { locations ->
            // 지도 업데이트 등의 작업은 Fragment에서 처리하므로 이곳에서는 필요하지 않음
        })
    }
}