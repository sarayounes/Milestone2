package com.example.kotlincartfirebase


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlincartfirebase.MainActivity
import com.example.kotlincartfirebase.R
import com.example.kotlincartfirebase.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val registertext: TextView = findViewById(R.id.textView_register_now)

        registertext.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }

        val loginButton: Button = findViewById(R.id.button_login)

        loginButton.setOnClickListener {
            performLogin()
        }


    }
    private fun performLogin(){
        //to get input from user
        val email: EditText = findViewById(R.id.editText_email_login)
        val password: EditText = findViewById(R.id.editText_password_login)

        //null checks on inputs
        if(email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT)
                .show()
            return
        }


        val emailInput= email.text.toString()
        val passwordInput= password.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, navigate to the Main Activity
                    val intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        baseContext, "Success",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "Authentication failed.${it.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
            }




    }
}