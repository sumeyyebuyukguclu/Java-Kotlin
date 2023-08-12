package com.sumeyyebuyukguclu.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val TextViewID = findViewById<TextView>(R.id.TextViewID)


        TextViewID.text = """
            That is something that I can not deny
            You put my soul from worst to best
            That’s why I value my dear lover.

            You just don’t know what you have done for me
            You even pushed me to the best that I can be
            You really are an angel sent from above
            To take care of me and shower with love
        """.trimIndent()
    }
}