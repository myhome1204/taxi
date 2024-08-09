package com.example.taxi.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.http.SslError
import android.os.Bundle
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.JsResult
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taxi.R
import com.example.taxi.databinding.ActivityGenerateRoomBinding
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

private lateinit var binding: ActivityGenerateRoomBinding

class GenerateRoomActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }


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

        // 초기 버튼 상태 확인
        checkFieldsForEmptyValues()

        binding.webView.visibility = View.INVISIBLE

        val fullAddress = intent.getStringExtra("full address")
        val fullAddress2 = intent.getStringExtra("full address2")
        val jibun = intent.getStringExtra("jibun")

        //출발지
        binding.startPointEdit.setOnClickListener {
            Log.d("this", "눌림")
            val webView: WebView = binding.webView
            webView.visibility = View.VISIBLE

            webView.clearCache(true)
            webView.settings.javaScriptEnabled = true

            webView.addJavascriptInterface(BridgeInterface(), "Android")
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    webView.loadUrl("javascript:sample2_execDaumPostcode();")
                }
            }
            webView.loadUrl("https://seulseul-35d52.web.app")

            val rmStartPoint = fullAddress.toString()
            binding.startPointEdit.setText(rmStartPoint)
        }
        // 목적지
        binding.endPointEdit.setOnClickListener {
            Log.d("this", "눌림")
            val webView: WebView = binding.webView
            webView.visibility = View.VISIBLE

            webView.clearCache(true)
            webView.settings.javaScriptEnabled = true

            webView.addJavascriptInterface(BridgeInterface2(), "Android")
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    webView.loadUrl("javascript:sample2_execDaumPostcode();")
                }
            }
            webView.loadUrl("https://seulseul-35d52.web.app")

            val rmEndPoint =  fullAddress2.toString()
            binding.endPointEdit.setText(rmEndPoint)
        }

        //버튼
        binding.generateRoomButton.setOnClickListener {

            val rmStart = binding.startPointEdit.text.toString()
            val rmEnd = binding.endPointEdit.text.toString()
            val hour = binding.timePicker.hour
            val min = binding.timePicker.minute

            //출발지
            Log.d("rmStart", rmStart)
            //목적지
            Log.d("rmEnd", rmEnd)
            //시간 (am/pm 에 따라 13시 1시 이런식으로 나옴)
            Log.d("hour", hour.toString())
            //분
            Log.d("min", min.toString())

        }
    }
    //출발지
    inner class BridgeInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        fun processDATA(fullRoadAddr: String, jibunAddr: String) {
            val intent = Intent().apply {
                putExtra("full address", fullRoadAddr)
                putExtra("jibun", jibunAddr)
            }
            setResult(RESULT_OK, intent)
            runOnUiThread {
                binding.startPointEdit.setText(fullRoadAddr)
                binding.webView.visibility = View.INVISIBLE
            }
        }
    }
    //목적지
    inner class BridgeInterface2 {
        @JavascriptInterface
        @SuppressWarnings("unused")
        fun processDATA(fullRoadAddr: String, jibunAddr: String) {
            val intent = Intent().apply {
                putExtra("full address2", fullRoadAddr)
                putExtra("jibun", jibunAddr)
            }
            setResult(RESULT_OK, intent)
            runOnUiThread {
                binding.endPointEdit.setText(fullRoadAddr)
                binding.webView.visibility = View.INVISIBLE
            }
        }
    }

    // EditText 빈값 확인하여 버튼 상태 업데이트
    private fun checkFieldsForEmptyValues() {
        // EditText 텍스트 변경 감지
        binding.startPointEdit.addTextChangedListener(textWatcher)
        binding.endPointEdit.addTextChangedListener(textWatcher)

        val startPointEdit = binding.startPointEdit.text.toString()
        val endpointEdit = binding.endPointEdit.text.toString()

        binding.generateRoomButton.apply {
            isEnabled = startPointEdit.isNotEmpty() && endpointEdit.isNotEmpty()
            setBackgroundColor(if (isEnabled) Color.BLACK else Color.GRAY)
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
