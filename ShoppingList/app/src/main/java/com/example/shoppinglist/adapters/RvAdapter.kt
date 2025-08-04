package com.example.shoppinglist.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.ListDetailsActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.database.ShoppingDatabase
import com.example.shoppinglist.entity.ListEntity
import com.example.shoppinglist.entity.TaskEntity
import com.example.shoppinglist.model.ItemType
import com.example.shoppinglist.model.rvModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RvAdapter(
    private val context: Context,
    private var rvList: MutableList<rvModel>,
    private val currentListName: String = "" // Only needed when handling tasks
) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    private val db = ShoppingDatabase.getDatabase(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvListName)
        val ivTrash: ImageView = itemView.findViewById(R.id.ivTrash_rv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = rvList[position]
        holder.tvTitle.text = item.title

        // ✅ Open ListDetailsActivity when clicking on title
        holder.tvTitle.setOnClickListener {
            if (item.type == ItemType.LIST) {
                goToListDetails(item.title)
            }
        }

        // 🗑️ Delete item logic
        holder.ivTrash.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    when (item.type) {
                        ItemType.LIST -> db.shoppingListDao()
                            .deleteList(ListEntity(listTitle = item.title))

                        ItemType.TASK -> db.shoppingListDao()
                            .deleteTask(TaskEntity(task = item.title, listName = currentListName))
                    }
                } catch (e: Exception) {
                    Log.e("Delete_Data", "Deletion operation failed: ", e)
                }
            }
        }
    }

    override fun getItemCount(): Int = rvList.size

    fun updateList(newList: MutableList<rvModel>) {
        rvList = newList
        notifyDataSetChanged()
    }

    // ✅ Launch ListDetailsActivity
    private fun goToListDetails(title: String) {
        val intent = Intent(context, ListDetailsActivity::class.java)
        intent.putExtra("listName", title)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Important if context is not an Activity
        context.startActivity(intent)
    }
}
