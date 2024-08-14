package com.example.taxi.ui.map

import android.content.Context
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi.databinding.ItemMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import java.util.*

class MapAdapter(
    private val context: Context,
    private val locations: List<Pair<LatLng, LatLng>>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MapAdapter.MapViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapViewHolder {
        val binding = ItemMapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MapViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MapViewHolder, position: Int) {
        val (startLocation, endLocation) = locations[position]
        holder.bind(startLocation, endLocation)
    }

    override fun getItemCount(): Int = locations.size

    inner class MapViewHolder(private val binding: ItemMapBinding) :
        RecyclerView.ViewHolder(binding.root), OnMapReadyCallback, View.OnClickListener {

        private lateinit var googleMap: GoogleMap
        private lateinit var startLocation: LatLng
        private lateinit var endLocation: LatLng

        init {
            binding.mapView.onCreate(null)
            binding.mapView.getMapAsync(this)
            binding.createRoomButton.setOnClickListener(this)
            binding.root.setOnClickListener(this)
        }

        fun bind(startLocation: LatLng, endLocation: LatLng) {
            this.startLocation = startLocation
            this.endLocation = endLocation
        }

        fun getBinding(): ItemMapBinding {
            return binding
        }

        private fun getAddress(latLng: LatLng): String {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            return addresses?.get(0)?.getAddressLine(0) ?: "Unknown location"
        }

        override fun onMapReady(map: GoogleMap) {
            googleMap = map
            googleMap.uiSettings.isScrollGesturesEnabled = false
            googleMap.uiSettings.isZoomGesturesEnabled = false
            googleMap.uiSettings.isZoomControlsEnabled = false

            googleMap.addMarker(MarkerOptions().position(startLocation).title("Start"))
            googleMap.addMarker(MarkerOptions().position(endLocation).title("End"))

            val boundsBuilder = LatLngBounds.builder()
            boundsBuilder.include(startLocation)
            boundsBuilder.include(endLocation)
            val bounds = boundsBuilder.build()

            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50))

            googleMap.addPolyline(
                PolylineOptions()
                    .add(startLocation, endLocation)
                    .width(5f)
                    .color(ContextCompat.getColor(context, android.R.color.holo_green_dark))
            )
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(v!!, position)
            }
        }
    }
}
