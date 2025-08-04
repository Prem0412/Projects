package com.example.shoppinglist

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.adapters.RvAdapter
import com.example.shoppinglist.database.ShoppingDatabase
import com.example.shoppinglist.databinding.ActivityListItemBinding
import com.example.shoppinglist.entity.ListEntity
import com.example.shoppinglist.entity.TaskEntity
import com.example.shoppinglist.model.ItemType
import com.example.shoppinglist.model.rvModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListItemActivity : AppCompatActivity() {

    lateinit var binding: ActivityListItemBinding
    lateinit var adapter: RvAdapter
    val itemList = mutableListOf<rvModel>()
    lateinit var currentListTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = ShoppingDatabase.getDatabase(this)

        adapter = RvAdapter(this, itemList)
        binding.rvItemAddedLI.layoutManager = LinearLayoutManager(this)
        binding.rvItemAddedLI.adapter = adapter

        // 🔘 Create List
       /* binding.btnCreateLI.setOnClickListener {
            val listTitle = binding.TitleIL.text.toString().trim()

            if (listTitle.isNotEmpty()) {
                currentListTitle = listTitle
                lifecycleScope.launch(Dispatchers.IO) {
                    db.shoppingListDao().addList(ListEntity(listTitle = listTitle))
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ListItemActivity, "List added", Toast.LENGTH_SHORT).show()
                        loadTasksForList(db, currentListTitle)
                    }
                }
            }
        }*/

        // ➕ Add Task
        binding.ivPlus.setOnClickListener {
            val listTitle = binding.TitleIL.text.toString().trim()
            val taskName = binding.TaskLI.text.toString().trim()

            if (listTitle.isNotEmpty() && taskName.isNotEmpty()) {
                try {
                currentListTitle = listTitle
                lifecycleScope.launch(Dispatchers.IO)
                {
                    db.shoppingListDao().addTask(TaskEntity(listName = listTitle, task = taskName))
                    withContext(Dispatchers.Main)
                    {
                        Toast.makeText(this@ListItemActivity, "Task added", Toast.LENGTH_SHORT).show()
                        loadTasksForList(db, currentListTitle)
                    }
                }

                }catch (e:Exception){
                    Log.e("DataLoading","Loading of data is failed",e)
                }
            }
        }
    }

    private fun loadTasksForList(db: ShoppingDatabase, listTitle: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val tasks = db.shoppingListDao().getAllTasksOfList(listTitle)
            val updatedList = tasks.map {
                rvModel(title = it.task, type = ItemType.TASK)
            }.toMutableList()

            withContext(Dispatchers.Main) {
                adapter.updateList(updatedList)
            }
        }
    }
}
