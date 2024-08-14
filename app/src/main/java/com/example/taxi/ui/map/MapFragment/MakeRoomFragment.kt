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
import com.google.android.gms.maps.model.*

class MakeRoomFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMakeRoomBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap
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

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        makeRoomViewModel.userLocations.observe(viewLifecycleOwner, { locations ->
            updateMap(locations)
        })
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.isScrollGesturesEnabled = true
        googleMap.uiSettings.isZoomGesturesEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true

        makeRoomViewModel.fetchUserLocations()
    }

    private fun updateMap(userLocations: List<LatLng>) {
        googleMap.clear()

        val startLocation = LatLng(37.5665, 126.9780)
        val endLocation = LatLng(37.5765, 126.9880)

        googleMap.addMarker(MarkerOptions().position(startLocation).title("Start"))
        googleMap.addMarker(MarkerOptions().position(endLocation).title("End"))

        val boundsBuilder = LatLngBounds.builder()
        boundsBuilder.include(startLocation)
        boundsBuilder.include(endLocation)

        userLocations.forEachIndexed { index, userLocation ->
            val markerOptions = MarkerOptions().position(userLocation)
            when (index) {
                0 -> markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                1 -> markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                else -> markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            }
            googleMap.addMarker(markerOptions.title("User ${index + 1}"))
            boundsBuilder.include(userLocation)
        }

        val bounds = boundsBuilder.build()
        val padding = 100 // px
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        googleMap.animateCamera(cameraUpdate)
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
