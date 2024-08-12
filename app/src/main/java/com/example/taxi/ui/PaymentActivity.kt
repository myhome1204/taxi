package com.example.taxi.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taxi.R
import com.example.taxi.data.rv.BankAdapter
import com.example.taxi.data.rv.BottomDialogItem
import com.example.taxi.databinding.ActivityGenerateRoomBinding
import com.example.taxi.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 버튼 상태확인
        checkFieldsForEmptyValues()

        binding.bankNameEditText.setOnClickListener {
            val list = mutableListOf(
                BottomDialogItem(R.drawable.hana, "하나"),
                BottomDialogItem(R.drawable.post, "우체국"),
                BottomDialogItem(R.drawable.toss, "토스"),
                BottomDialogItem(R.drawable.industry, "산업"),
                BottomDialogItem(R.drawable.citi, "시티"),
                BottomDialogItem(R.drawable.shinhan, "신한"),
                BottomDialogItem(R.drawable.kakao, "카카오"),
                BottomDialogItem(R.drawable.kookmin, "국민"),
                BottomDialogItem(R.drawable.shinhyup, "신엽"),
                BottomDialogItem(R.drawable.newtown, "새마을"),
                BottomDialogItem(R.drawable.kiyup, "기업"),
                BottomDialogItem(R.drawable.nongyup, "농협"),

            )

            val adapter = BankAdapter()
            val bottomDialogFragment = BottomDialogFragment(adapter) { selectedItem: BottomDialogItem ->
                // 선택된 아이템의 이름을 EditText에 설정
                binding.bankNameEditText.setText(selectedItem.title)
            }

            adapter.setItems(list)
            bottomDialogFragment.show(supportFragmentManager, "TAG")
        }



        binding.pButton.setOnClickListener {
            finish()
        }


        binding.checkButton.setOnClickListener {
            val name = binding.bankNameEditText.text.toString()
            val number = binding.accountNumberEditText.text.toString()
            val price = binding.priceEditText.text.toString()

            Log.d("name", name)
            Log.d("number", number)
            Log.d("price", price)
        }

    }
    // EditText 빈값 확인하여 버튼 상태 업데이트
    private fun checkFieldsForEmptyValues() {
        // EditText 텍스트 변경 감지
        binding.accountNumberEditText.addTextChangedListener(textWatcher)
        binding.bankNameEditText.addTextChangedListener(textWatcher)
        binding.priceEditText.addTextChangedListener(textWatcher)

        val accountNumber = binding.accountNumberEditText.text.toString()
        val bankName = binding.bankNameEditText.text.toString()
        val price = binding.priceEditText.text.toString()

        binding.checkButton.apply {
            isEnabled = accountNumber.isNotEmpty() && bankName.isNotEmpty() && price.isNotEmpty()
            setBackgroundColor(if (isEnabled) Color.parseColor("#3D6840") else Color.GRAY)
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