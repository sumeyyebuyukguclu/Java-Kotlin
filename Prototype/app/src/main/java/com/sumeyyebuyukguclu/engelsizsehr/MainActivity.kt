@file:Suppress("DEPRECATION")

package com.sumeyyebuyukguclu.engelsizsehr

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiNetworkSpecifier
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sumeyyebuyukguclu.engelsizsehr.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DBHelper
    private val intentFilter = IntentFilter()
    private lateinit var channel: WifiP2pManager.Channel
    private lateinit var manager: WifiP2pManager


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbHelper = DBHelper(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        println("11")
        // Belirli SSID'ye sahip ve şifreli Wi-Fi ağına bağlanma isteği oluşturun
        val specifier = WifiNetworkSpecifier.Builder()
            .setSsid("Zyxel_98A1") //  Wi-Fi ağının SSID'si
            .setWpa2Passphrase("PURXQXX7JP") // Wi-Fi ağı şifresi
            .build()

        println("12")

        val request = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .setNetworkSpecifier(specifier)
            .build()

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                println("15")
            }
        }

        connectivityManager.requestNetwork(request, networkCallback)

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

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
    }

    fun signInClicked(view: View) {
        val userEmail = binding.EmailText.text.toString()
        val password = binding.PasswordText.text.toString()

        if (userEmail.isNotEmpty() && password.isNotEmpty()) {

            val isSignInSuccessful = dbHelper.checkUser(userEmail, password)

            if (isSignInSuccessful) {
                val intent = Intent(applicationContext, FeedActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Giriş başarısız. Kullanıcı adı veya şifre hatalı.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        sendBarrierCommand(true, "/x1y2")
    }

    fun checkPassword(password: String): Boolean {
        val uppercasePattern = Regex("[A-Z]")

        return password.length >= 6 && password.contains(uppercasePattern)
    }

    fun signUpClicked(view: View) {

        val email = binding.EmailText.text.toString()
        val password = binding.PasswordText.text.toString()


        if (email.isNotEmpty() && password.isNotEmpty()) {
            val isAlreadyHas = dbHelper.checkUserAlreadyHas(email)
            val isPasswordOkey = checkPassword(password)
            if (isAlreadyHas || !isPasswordOkey) {
                if (isAlreadyHas) {
                    Toast.makeText(
                        applicationContext,
                        "Kullanıcı zaten mevcut",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (!isPasswordOkey) {
                    Toast.makeText(
                        applicationContext,
                        "Şifre en az 6 karakter ve 1 büyük harf içermelidir.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                val isUserAdded = dbHelper.addUser(email, password)
                if (isUserAdded) {
                    val intent = Intent(applicationContext, FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Kullanıcı eklenirken bir hata oluştu",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}

/*
    // Bariyer komutunu gönderen fonksiyon
    private fun sendBarrierCommand(open: Boolean, command: String) {
        val client = OkHttpClient()

        val url = "http://192.168.4.1/x1y2" // Arduino'nun IP adresi ve komut yolu
        val requestBody = FormBody.Builder()
            .add("open", open.toString()) // Komutu belirt (aç veya kapat)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("MainActivity", "Request failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                // Arduino'dan dönen cevapla ilgili işlemler burada yapılabilir
            }
        })

 */
