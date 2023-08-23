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

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        dbHelper = DBHelper(this)
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
            if(isAlreadyHas || !isPasswordOkey){
                if(isAlreadyHas){
                    Toast.makeText(
                        applicationContext,
                        "Kullanıcı zaten mevcut",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else if(!isPasswordOkey)
                {
                    Toast.makeText(
                        applicationContext,
                        "Şifre en az 6 karakter ve 1 büyük harf içermelidir.",
                        Toast.LENGTH_LONG
                    ).show()
                }
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