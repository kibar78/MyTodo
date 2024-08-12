package com.example.mytodo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoRoomDatabase : RoomDatabase() {
    abstract fun todo(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: TodoRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): TodoRoomDatabase {
            if (INSTANCE == null) {
                synchronized(TodoRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TodoRoomDatabase::class.java, "todo_database")
                        .build()
                }
            }
            return INSTANCE as TodoRoomDatabase
        }
    }
}