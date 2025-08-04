package com.example.shoppinglist

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.shoppinglist.database.ShoppingDatabase
import com.example.shoppinglist.databinding.ActivityListDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listName = intent.getStringExtra("listName").orEmpty().trim()
        binding.listTitleLD.text = listName

        val db = ShoppingDatabase.getDatabase(this)

        lifecycleScope.launch {
            // Fetch all tasks for the given list name in the background
            val taskList = withContext(Dispatchers.IO) {
                db.shoppingListDao().getAllTasksOfList(listName)
            }

            // Extract task names from TaskEntity list
            val taskNames = taskList.map { it.task }

            // Set to ListView
            val adapter = ArrayAdapter(
                this@ListDetailsActivity,
                android.R.layout.simple_list_item_1,
                taskNames
            )
            binding.listViewLD.adapter = adapter
        }
    }
}
