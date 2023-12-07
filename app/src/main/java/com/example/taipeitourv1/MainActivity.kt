package com.example.taipeitourv1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taipeitour.TaipeiTourFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.Main_FrameLayout, TaipeiTourFragment()).commit()
    }
}