package com.sumeyyebuyukguclu.engelsizsehr

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sumeyyebuyukguclu.engelsizsehr.databinding.ActivityFeedBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import java.net.Socket
import java.util.concurrent.TimeUnit

class FeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var barrierButtonFeed: Button
    private var active: Boolean = false
    private var socket: Socket? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbHelper = DBHelper(this)

        barrierButtonFeed = findViewById(R.id.bariyerbutonu)

        // Bariyer komutunu gönderen fonksiyon
        fun sendBarrierCommand(open: Boolean, command: String) {
            val client = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS) // Bağlantı kurma zaman aşımı
                .writeTimeout(30, TimeUnit.SECONDS)   // Yazma zaman aşımı
                .readTimeout(30, TimeUnit.SECONDS)    // Okuma zaman aşımı
                .build()

            val url = "http://192.168.4.1$command" // Arduino'nun IP adresi ve komut yolu


            val request = Request.Builder()
                .url(url)
                .get()
                .build()


            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("MainActivity", "Request failed: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    // Arduino'dan dönen cevapla ilgili işlemler
                }
            })

            println("4")

        }

        // Bariyeri açma işlemi
        fun openBarrier() {
            sendBarrierCommand(true, "/ON")
        }

        // Bariyeri kapama işlemi
        fun closeBarrier() {
            sendBarrierCommand(false, "/OFF")
        }

        barrierButtonFeed.setOnClickListener {
            if (barrierButtonFeed.text == "Bariyer Aç") {
                barrierButtonFeed.text = "Bariyer Kapat"
                showToast("Bariyer açıldı")
                active = true
                CoroutineScope(Dispatchers.IO).launch {
                    openBarrier() // Bariyeri açma işlemini çağır
                }
            } else {
                active = false
                barrierButtonFeed.text = "Bariyer Aç"
                showToast("Bariyer kapatıldı")
                CoroutineScope(Dispatchers.IO).launch {
                    closeBarrier() // Bariyeri kapatma işlemini çağır
                }
            }
        }
    }




/*
    private suspend fun establishSocketConnection(address: String, port: Int) {
        try {
            socket = Socket(address, port)
            val writer = socket!!.getOutputStream()
            writer.write("/ON".toByteArray()) // Göndermek istediğiniz komut
        } catch (e: Exception) {
            Log.e("SocketError", "Socket connection error: ${e.message}")
        }
    }

 */
/*
    private fun closeSocketConnection() {
        try {
            socket?.close()
        } catch (e: IOException) {
            Log.e("SocketError", "Socket close error: ${e.message}")
        }
    }

 */


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}



/*
    private suspend fun client(address: String, port: Int) {
        val connection = Socket(address, port)
        val writer = connection.getOutputStream()
        writer.write(1)
        val reader = Scanner(connection.getInputStream())
        /*while (active) {
            //
        }
         */

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
     */
