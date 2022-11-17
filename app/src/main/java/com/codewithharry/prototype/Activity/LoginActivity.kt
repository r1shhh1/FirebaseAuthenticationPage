package com.codewithharry.prototype.Activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.codewithharry.prototype.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        lbtn.setOnClickListener{
            when{

                TextUtils.isEmpty(l_email.text.toString().trim{it<= ' '}) ->{
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter email Id",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(l_pass.text.toString().trim{it<= ' '}) ->{
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else ->{

                    val email: String = l_email.text.toString().trim{it<= ' '}
                    val password: String = l_pass.text.toString().trim{it<= ' '}

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener{ task ->

                            if(task.isSuccessful){

                                Toast.makeText(
                                    this@LoginActivity,
                                    "You are logged in successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent =
                                    Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            }else{

                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message!!.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                        }

                        }
                }
            }
        }

        rpg.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }
}