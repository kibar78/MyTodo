package com.example.mytodo.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mytodo.data.Todo
import com.example.mytodo.data.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(application: Application): ViewModel() {
    private val mTodoRepository: TodoRepository = TodoRepository(application)

    fun getAllTodo(): LiveData<List<Todo>> = mTodoRepository.getAllTodo()

    val themeSetting: Flow<Boolean> = mTodoRepository.getThemeSetting()
}