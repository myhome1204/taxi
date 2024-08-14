package com.example.taxi.ui.map

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi.R
import com.google.android.gms.maps.model.LatLng
import com.example.taxi.databinding.ActivityMapBinding


class MapActivity : AppCompatActivity(), MapAdapter.OnItemClickListener {
    private lateinit var binding : ActivityMapBinding
    private lateinit var mapAdapter: MapAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locations = listOf(
            Pair(LatLng(37.5665, 126.9780), LatLng(37.5765, 126.9880)),
            Pair(LatLng(37.5510, 126.9882), LatLng(37.5610, 126.9982))
        )

        mapAdapter = MapAdapter(this, locations, this)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mapAdapter
        binding.bottomSheet.setOnClickListener {
            val intent = Intent(this, MakeRoomActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()

        val holder = recyclerView.findViewHolderForAdapterPosition(position) as? MapAdapter.MapViewHolder
        holder?.let {
            val binding = it.getBinding() // 접근 가능한 binding 객체
            when (view.id) {
                R.id.create_room_button -> {
                    val button = binding.createRoomButton
                    if (button.text == "합승하기") {
                        button.text = "취소하기"
                        button.setBackgroundResource(R.drawable.round_box_transparent)
                        this.binding.bottomSheet.visibility = View.VISIBLE

                    } else {
                        //startActivity(Intent(this, MakeRoomActivity::class.java))
                        button.text = "합승하기"
                        button.setBackgroundResource(R.drawable.round_box_transparent)
                        this.binding.bottomSheet.visibility = View.GONE
                    }
                }
                else -> {
                    // Handle other clicks if needed
                }
            }
        }
    }
}
