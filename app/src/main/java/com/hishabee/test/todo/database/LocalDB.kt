package com.hishabee.test.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hishabee.test.todo.database.dao.TaskDao
import com.hishabee.test.todo.database.entity.TaskTable

@Database(
    entities = [
        TaskTable::class
    ], version = 1
)
abstract class LocalDB : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDB? = null

        fun getDatabase(context: Context): LocalDB {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDB::class.java,
                    "hishabee_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}