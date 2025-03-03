package com.example.listatelefonica.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listatelefonica.R
import com.example.listatelefonica.database.DBHelper
import com.example.listatelefonica.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)

        binding.btnSingUp.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            val passwordC = binding.editConfirmPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && passwordC.isNotEmpty()) {

                if (password.equals(passwordC)) {
                    val res = db.insertUser(username, password)
                    if(res > 0){
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.signup_ok),
                            Toast.LENGTH_SHORT
                        ).show()
                        finish() // volta para activity anterior
                    }else{
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.signup_error),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.editUsername.setText("")
                        binding.editPassword.setText("")
                        binding.editConfirmPassword.setText("")
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.password_don_t_match),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.please_insert_all_required_fields),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}