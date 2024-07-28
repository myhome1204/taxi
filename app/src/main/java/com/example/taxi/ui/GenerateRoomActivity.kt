package com.example.taxi.ui

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taxi.R
import com.example.taxi.databinding.ActivityGenerateRoomBinding
import com.example.taxi.databinding.ActivityMainBinding

private lateinit var binding: ActivityGenerateRoomBinding


class GenerateRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerateRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 돌아가기 버튼
        binding.pButton.setOnClickListener {
            finish()
        }

        // EditText 텍스트 변경 감지
        binding.startPointEdit.addTextChangedListener(textWatcher)
        binding.endPointEdit.addTextChangedListener(textWatcher)
        binding.timeEdit.addTextChangedListener(textWatcher)

        // 초기 버튼 상태 확인
        checkFieldsForEmptyValues()

    }


    // EditText 빈값 확인하여 버튼 상태 업데이트
    private fun checkFieldsForEmptyValues() {
        val startPointEdit = binding.startPointEdit.text.toString()
        val endpointEdit = binding.endPointEdit.text.toString()
        val goalTime = binding.timeEdit.text.toString()

        if (startPointEdit.isEmpty() || endpointEdit.isEmpty() || goalTime.isEmpty()) {
            binding.generateRoomButton.isEnabled = false
            binding.generateRoomButton.setBackgroundColor(Color.GRAY)
        } else {
            binding.generateRoomButton.isEnabled = true
            binding.generateRoomButton.setBackgroundColor(Color.BLACK)
        }
    }

    // EditText 텍스트 변경 감지
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            checkFieldsForEmptyValues()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

}