package com.sumeyyebuyukguclu.engelsizsehr

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sumeyyebuyukguclu.engelsizsehr.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("checkuser-1")

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        dbHelper = DBHelper(this)
    }

    fun signInClicked(view: View) {
            val userEmail = binding.EmailText.text.toString()
            val password = binding.PasswordText.text.toString()
            println("checkuser0")

            if (userEmail.isNotEmpty() && password.isNotEmpty()) {
                val isSignInSuccessful = dbHelper.checkUser(userEmail, password)
                println("checkuser çıktı")
                if (isSignInSuccessful) {
                    println("geldi")
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
        }



    fun signUpClicked(view: View) {

        val email = binding.EmailText.text.toString()
        val password = binding.PasswordText.text.toString()


        if (email.isNotEmpty() && password.isNotEmpty()) {
            val isAlreadyHas = dbHelper.checkUser(email, password)
            if(isAlreadyHas){
                Toast.makeText(
                    applicationContext,
                    "Kullanıcı zaten mevcut",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
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