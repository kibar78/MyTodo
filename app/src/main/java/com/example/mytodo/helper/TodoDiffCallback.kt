package com.example.mytodo.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.mytodo.data.Todo

class TodoDiffCallback(private val oldTodoList: List<Todo>, private val newTodoList: List<Todo>):
    DiffUtil.Callback(){

    override fun getOldListSize(): Int  = oldTodoList.size

    override fun getNewListSize(): Int = newTodoList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTodoList[oldItemPosition].id == newTodoList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTodo = oldTodoList[oldItemPosition]
        val newTodo = newTodoList[newItemPosition]
        return oldTodo.title == newTodo.title && oldTodo.description == newTodo.description
    }
    }