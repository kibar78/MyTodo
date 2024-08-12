package com.example.mytodo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.data.Todo
import com.example.mytodo.databinding.ItemTodoBinding
import com.example.mytodo.helper.TodoDiffCallback
import com.example.mytodo.ui.insert.TodoAddActivity

class TodoAdapter: RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private val listTodo = ArrayList<Todo>()
    fun setListNotes(listNotes: List<Todo>) {
        val diffCallback = TodoDiffCallback(this.listTodo, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listTodo.clear()
        this.listTodo.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(listTodo[position])
    }

    override fun getItemCount(): Int = listTodo.size

    inner class TodoViewHolder(private val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            with(binding) {
                tvItemTitle.text = todo.title
                tvItemDate.text = todo.date
                tvItemDescription.text = todo.description
                cvItemNote.setOnClickListener {
                    val intent = Intent(it.context, TodoAddActivity::class.java)
                    intent.putExtra(TodoAddActivity.EXTRA_NOTE, todo)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}