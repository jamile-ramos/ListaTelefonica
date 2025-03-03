package com.example.listatelefonica.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listatelefonica.R
import com.example.listatelefonica.adapter.ContactListAdapter
import com.example.listatelefonica.adapter.listener.ContactOnClickListener
import com.example.listatelefonica.database.DBHelper
import com.example.listatelefonica.databinding.ActivityMainBinding
import com.example.listatelefonica.model.ContactModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactList: MutableList<ContactModel> // Mudado para MutableList
    private lateinit var adapter: ContactListAdapter
    private lateinit var result: ActivityResultLauncher<Intent>
    private lateinit var db: DBHelper
    private var ascDes: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)

        val sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)

        binding.btnLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("username", "")
            editor.apply()
            finish()
        }

        binding.recyclerViewContacts.layoutManager = LinearLayoutManager(applicationContext)

        loadList(db)

        binding.btnAdd.setOnClickListener {
            result.launch(Intent(applicationContext, NewContactActivity::class.java))
        }

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultData ->
            if (resultData?.resultCode == 1) {
                loadList(db) // Atualiza a lista após adicionar ou editar
            } else if (resultData?.resultCode == 0) {
                Toast.makeText(applicationContext, "Operation Canceled", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnOrder.setOnClickListener {
            if (ascDes) {
                binding.btnOrder.setImageResource(R.drawable.baseline_arrow_upward_24)
                contactList.sortBy { it.name } // Ordenação crescente
            } else {
                binding.btnOrder.setImageResource(R.drawable.baseline_arrow_downward_24)
                contactList.sortByDescending { it.name } // Ordenação decrescente
            }

            ascDes = !ascDes
            placeAdapter() // Atualiza o adapter com a nova ordem
        }
    }

    private fun placeAdapter() {
        adapter = ContactListAdapter(contactList, ContactOnClickListener { contact ->
            val i = Intent(applicationContext, ContactDetailActivity::class.java)
            i.putExtra("id", contact.id)
            result.launch(i)
        })

        binding.recyclerViewContacts.adapter = adapter
    }

    private fun loadList(db: DBHelper) {
        // Ordena diretamente com sortedBy() e converte para MutableList
        contactList = db.getAllContact().sortedBy { it.name }.toMutableList()
        placeAdapter() // Atualiza o adapter após carregar a lista
    }
}
