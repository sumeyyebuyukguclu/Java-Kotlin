package com.sumeyyebuyukguclu.engelsizsehr

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sumeyyebuyukguclu.engelsizsehr.databinding.ActivityFeedBinding

class FeedActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFeedBinding
    private lateinit var barrierButtonFeed: Button
    private lateinit var dbHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbHelper = DBHelper(this)

        barrierButtonFeed = findViewById(R.id.bariyerbutonu)
        barrierButtonFeed.setOnClickListener {
            toggleBarrier()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.yan_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.add)
        {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }else if(item.itemId == R.id.sign_out){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggleBarrier() {
        if (barrierButtonFeed.text == "Bariyer Kapat") {
            barrierButtonFeed.text = "Bariyer Aç"
            showToast("Bariyer kapatıldı")
        } else {
            barrierButtonFeed.text = "Bariyer Kapat"
            showToast("Bariyer açıldı")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}