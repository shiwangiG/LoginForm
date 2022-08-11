package com.example.loginform

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginform.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    private lateinit var auth: FirebaseAuth

    val database = FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        auth = FirebaseAuth.getInstance()

//        setContentView(R.layout.activity_main)


        //getUserList after login
        binding!!.subBtn.setOnClickListener {
            val username = binding?.username?.text.toString()
            val password = binding?.password?.text.toString()
            login(username, password)
        }

    }

    fun login(email: String, password: String) {
        if (checking(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, UserDataActivity::class.java)
//                            intent.putExtra("Email", email)
                        startActivity(intent)
                    } else {

                        if (password.length < 7) {
                            Toast.makeText(
                                this,
                                "Password should be of 7 characters or greater",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (!isValidPassword(password)) {
                            Toast.makeText(
                                this,
                                "Password should be combination of number, special character",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(this, "Wrong Details", Toast.LENGTH_LONG).show()
                        }
                    }
                }
        } else {
            Toast.makeText(this, "Enter The Details", Toast.LENGTH_LONG).show()
        }
    }

    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher: Matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private fun checking(email: String, password: String): Boolean {

        if (email.trim()
                .isNotEmpty() && password.trim()
                .isNotEmpty()
        ) {
            return true
        }
        return false
    }
}