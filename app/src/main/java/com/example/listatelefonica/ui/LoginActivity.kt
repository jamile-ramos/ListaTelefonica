package com.example.listatelefonica.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listatelefonica.R
import com.example.listatelefonica.database.DBHelper
import com.example.listatelefonica.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)

        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        if (username != null) {
            if(username.isNotEmpty()){
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            val logged = binding.checkboxLogged.isChecked

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val res = db.login(username, password)
                if(res){
                    if(logged){
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("username", username)
                        editor.apply()
                    }
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }else{
                    Toast.makeText(
                        applicationContext, getString(R.string.login_error),
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.editUsername.setText("")
                    binding.editPassword.setText("")
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.please_insert_all_required_fields),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.textSingup.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }

        binding.textRecoverPassword.setOnClickListener {

        }

    }
}