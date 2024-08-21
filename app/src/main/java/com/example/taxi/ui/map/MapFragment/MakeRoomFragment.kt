package com.example.taxi.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.taxi.databinding.FragmentMakeRoomBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MakeRoomFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMakeRoomBinding? = null
    private val binding get() = _binding!!
    private var googleMap: GoogleMap? = null
    private val makeRoomViewModel: RoomMakeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMakeRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // MapView 초기화
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        // 사용자 위치 데이터 변경을 관찰하고, 변경 시 지도에 마커를 업데이트
        makeRoomViewModel.userLocations.observe(viewLifecycleOwner, { locations ->
            googleMap?.let {
                updateMap(locations)
            }
        })
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.uiSettings?.isScrollGesturesEnabled = true
        googleMap?.uiSettings?.isZoomGesturesEnabled = true
        googleMap?.uiSettings?.isZoomControlsEnabled = true

        // 초기 사용자 위치를 지도에 반영
        makeRoomViewModel.userLocations.value?.let { updateMap(it) }

        // 주기적으로 사용자 위치를 가져오기 시작
        makeRoomViewModel.fetchUserLocations()
    }

    private fun updateMap(userLocations: List<LatLng>) {
        googleMap?.clear() // 기존 마커 제거

        val boundsBuilder = LatLngBounds.builder()

        userLocations.forEachIndexed { index, userLocation ->
            val markerOptions = MarkerOptions().position(userLocation)
            when (index) {
                0 -> markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                1 -> markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                else -> markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            }
            googleMap?.addMarker(markerOptions.title("User ${index + 1}"))
            boundsBuilder.include(userLocation)
        }

        val bounds = boundsBuilder.build()
        val padding = 100 // px
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        googleMap?.animateCamera(cameraUpdate)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
        _binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}
