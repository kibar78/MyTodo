package com.example.mytodo.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.mytodo.data.Todo
import com.example.mytodo.data.repository.TodoRepository

class TodoAddViewModel(application: Application): ViewModel() {
    private val mTodoRepository: TodoRepository = TodoRepository(application)

    fun insert(todo: Todo) {
        mTodoRepository.insert(todo)
    }

    fun update(todo: Todo){
        mTodoRepository.update(todo)
    }

    fun delete(todo: Todo){
        mTodoRepository.delete(todo)
    }

}