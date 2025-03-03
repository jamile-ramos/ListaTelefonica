package com.example.listatelefonica.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.listatelefonica.R
import com.example.listatelefonica.databinding.ActivityContactImageBinding

class ContactImageSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactImageBinding
    private lateinit var i: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        i = intent

        binding.profile1.setOnClickListener{
            sendId(R.drawable.profile1)
        }
        binding.profile2.setOnClickListener{
            sendId(R.drawable.profile2)
        }
        binding.profile3.setOnClickListener{
            sendId(R.drawable.profile3)
        }
        binding.profile4.setOnClickListener{
            sendId(R.drawable.profile4)
        }
        binding.profile5.setOnClickListener{
            sendId(R.drawable.profile5)
        }
        binding.profile6.setOnClickListener{
            sendId(R.drawable.profile6)
        }
        binding.btnRemoveImg.setOnClickListener {
            sendId(R.drawable.profiledefault)
        }
    }

    private fun sendId(id: Int) {
        i.putExtra("id", id)
        setResult(1, i)
        finish()
    }
}