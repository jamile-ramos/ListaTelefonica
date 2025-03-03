package com.example.listatelefonica.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.listatelefonica.R
import com.example.listatelefonica.database.DBHelper
import com.example.listatelefonica.databinding.ActivityNewContactBinding

class NewContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewContactBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var id: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(applicationContext)
        val i = intent

        binding.btnSave.setOnClickListener {
            val name = binding.editName.text.toString()
            val address = binding.editAddress.text.toString()
            val email = binding.editEmail.text.toString()
            val phoneText = binding.editPhone.text.toString().replace(Regex("[^0-9]"), "") // Remove qualquer caractere não numérico
            val phone = if (phoneText.isNotEmpty()) {
                phoneText.toIntOrNull() ?: 0  // Tenta converter para Int, se falhar, retorna 0
            } else {
                0  // Se estiver vazio, retorna 0
            }

            val imageId = id ?: -1

            if (name.isNotEmpty() && address.isNotEmpty() && email.isNotEmpty()) {
                val res = db.insertContact(name, address, email, phone, imageId)

                if (res > 0) {
                    Toast.makeText(applicationContext, "Insert OK", Toast.LENGTH_SHORT).show()
                    setResult(1, i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Insert error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnCancelar.setOnClickListener {
            setResult(0, i)
            finish()
        }

        binding.imageContact.setOnClickListener {
            launcher.launch(Intent(applicationContext, ContactImageSelectionActivity::class.java))
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                id = it.data?.extras?.getInt("id")
                binding.imageContact.setImageDrawable(ContextCompat.getDrawable(this, id ?: R.drawable.profiledefault))
            } else {
                id = -1
                binding.imageContact.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.profiledefault))
            }
        }
    }
}
