package com.example.mytodo.ui.setting

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytodo.data.repository.TodoRepository
import kotlinx.coroutines.launch

class SettingViewModel(application: Application): ViewModel() {
    private val mTodoRepository: TodoRepository = TodoRepository(application)


    fun getThemeSettings(): LiveData<Boolean>{
        return mTodoRepository.getThemeSetting().asLiveData()
    }
    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            mTodoRepository.saveThemeSetting(isDarkModeActive)
        }
    }
}