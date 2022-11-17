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
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        rbtn.setOnClickListener{
            when{
                TextUtils.isEmpty(r_fname.text.toString().trim{it<= ' '}) ->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter your First Name!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(r_lname.text.toString().trim{it<= ' '}) ->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter your Last Name!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(r_email.text.toString().trim{it<= ' '}) ->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter your Email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(r_pass.text.toString().trim{it<= ' '}) ->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Password can't be empty!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else->{

                    val fname:String = r_fname.text.toString().trim{it<= ' '}
                    val lname:String = r_lname.text.toString().trim{it<= ' '}
                    val email:String = r_email.text.toString().trim{it<= ' '}
                    val password:String = r_pass.text.toString().trim{it<= ' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener{task ->

                            if(task.isSuccessful){

                                val firebaseUser : FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@RegisterActivity,
                                    "You are registered successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent =
                                    Intent(this@RegisterActivity, LoginActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", email)
                                intent.putExtra("fname", fname)
                                intent.putExtra("lname", lname)
                                startActivity(intent)
                                finish()
                            }else{

                                Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message!!.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }

                }
            }
        }

        lpg.setOnClickListener{
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}