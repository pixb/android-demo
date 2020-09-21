package com.openpix.testresouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_selector.setOnClickListener {
            Toast.makeText(this,"btn_selector click...",Toast.LENGTH_SHORT).show()
        }
    }
}