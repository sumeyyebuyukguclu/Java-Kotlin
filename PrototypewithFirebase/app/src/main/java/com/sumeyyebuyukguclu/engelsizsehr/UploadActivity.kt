package com.sumeyyebuyukguclu.engelsizsehr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sumeyyebuyukguclu.engelsizsehr.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUploadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_upload)

    }

    fun uploadImg(view : View)
    {

    }

    fun selectImg(view : View)
    {

    }

}