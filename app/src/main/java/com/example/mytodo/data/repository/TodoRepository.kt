package com.example.mytodo.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mytodo.data.Todo
import com.example.mytodo.data.TodoDao
import com.example.mytodo.data.TodoRoomDatabase
import com.example.mytodo.ui.setting.SettingPreferences
import com.example.mytodo.ui.setting.dataStore
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TodoRepository(application: Application) {
    private val mTodoDao: TodoDao
    private val pref: SettingPreferences
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = TodoRoomDatabase.getDatabase(application)
        mTodoDao = db.todo()

        pref = SettingPreferences.getInstance(application.dataStore)
    }

    fun getAllTodo(): LiveData<List<Todo>> = mTodoDao.getAllNotes()

    fun insert(todo: Todo) {
        executorService.execute { mTodoDao.insert(todo) }
    }

    fun delete(todo: Todo) {
        executorService.execute { mTodoDao.delete(todo) }
    }

    fun update(todo: Todo) {
        executorService.execute { mTodoDao.update(todo) }
    }

    fun getThemeSetting(): Flow<Boolean> {
        return pref.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean){
        pref.saveThemeSetting(isDarkModeActive)
    }
}