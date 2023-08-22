package com.sumeyyebuyukguclu.engelsizsehr

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.sumeyyebuyukguclu.engelsizsehr.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        if (currentUser != null) { //güncel kullanıcı varsa
            val intent = Intent(applicationContext, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

        fun signInClicked(view : View) {

            val userEmail = binding.EmailText.text.toString()
            val password = binding.PasswordText.text.toString()

            if (userEmail.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(userEmail,password).addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        //Signed In
                        Toast.makeText(applicationContext,"Welcome: ${auth.currentUser?.email.toString()}",
                            Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext, FeedActivity::class.java)
                        startActivity(intent)
                        finish()

                    }

                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }

        }

        fun signUpClicked(view : View) {

            val userEmail = binding.EmailText.text.toString()
            val password = binding.PasswordText.text.toString()

            if (userEmail.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(userEmail, password)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            val intent = Intent(applicationContext, FeedActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }.addOnFailureListener { exception ->
                    Toast.makeText(
                        applicationContext,
                        exception.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }
            }

        }
}