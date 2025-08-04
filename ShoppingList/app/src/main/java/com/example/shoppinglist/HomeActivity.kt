package com.example.shoppinglist

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.adapters.RvAdapter
import com.example.shoppinglist.database.ShoppingDatabase
import com.example.shoppinglist.databinding.ActivityHomeBinding
import com.example.shoppinglist.entity.ListEntity
import com.example.shoppinglist.model.ItemType
import com.example.shoppinglist.model.rvModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: RvAdapter
    private lateinit var db: ShoppingDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ShoppingDatabase.getDatabase(this)

        setupRecyclerView()
        loadListsFromDatabase()

        // ➕ New List Button
        binding.newListIconHS.setOnClickListener {
            showAddListDialog()
        }
    }

    private fun setupRecyclerView() {
        binding.rvListCollectionHS.layoutManager = LinearLayoutManager(this)
        adapter = RvAdapter(this, mutableListOf())
        binding.rvListCollectionHS.adapter = adapter
    }

    private fun loadListsFromDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val collectionOfLists = db.shoppingListDao().getAllLists().map {
                    rvModel(title = it.listTitle, type = ItemType.LIST)
                }
                withContext(Dispatchers.Main) {
                    adapter.updateList(collectionOfLists.toMutableList())
                }
            } catch (e: Exception) {
                Log.e("data_loading", "Data loading of lists is failed", e)
            }
        }
    }

    private fun showAddListDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("New List")

        val input = EditText(this)
        input.hint = "Enter list name"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("Add") { dialog, _ ->
            val listName = input.text.toString().trim()
            if (listName.isNotEmpty()) {
                addListToDatabase(listName)
                startActivity(Intent(this,ListItemActivity::class.java))
            } else {
                Toast.makeText(this, "List name cannot be empty", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun addListToDatabase(listName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                db.shoppingListDao().addList(ListEntity(listTitle = listName))
                loadListsFromDatabase() // Refresh the RecyclerView


            } catch (e: Exception) {
                Log.e("DB_INSERT_ERROR", "Failed to add list", e)
            }
        }
    }

    private fun navigateToListItem() {
        startActivity(Intent(this, ListItemActivity::class.java))
    }
}
