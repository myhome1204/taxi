package com.example.taxi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi.R
import com.example.taxi.data.rv.BankAdapter
import com.example.taxi.data.rv.BottomDialogItem

class BottomDialogFragment(
    private val adapter: BankAdapter,
    private val onItemSelected: (BottomDialogItem) -> Unit
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_bottom, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = adapter

        adapter.onItemClick = { item ->
            onItemSelected(item)
            dismiss()  // 아이템 선택 시 바텀시트 닫기
        }

        return view
    }
}
