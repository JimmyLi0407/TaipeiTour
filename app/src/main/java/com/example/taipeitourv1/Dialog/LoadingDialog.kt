package com.example.taipeitourv1.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import com.example.taipeitourv1.R

class LoadingDialog(context: Context) : Dialog(context) {
    private lateinit var dialog_progressBar: ProgressBar
    private lateinit var dialog_loadingText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_loading_layout)
        initView()
        setLoadingDialog()
    }

    private fun initView() {
        dialog_progressBar = findViewById(R.id.Dialog_CustomProgressBar)
        dialog_loadingText = findViewById(R.id.Dialog_CustomLoadingText)
    }

    private fun setLoadingDialog() {
        dialog_progressBar.isIndeterminate = true
        dialog_loadingText.text = "載入中..."
    }
}