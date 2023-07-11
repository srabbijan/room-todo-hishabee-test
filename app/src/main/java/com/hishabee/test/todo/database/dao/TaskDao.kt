package com.hishabee.test.todo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hishabee.test.todo.database.entity.TaskTable

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(dataList: TaskTable)

    @Query("SELECT * FROM task_table order by dbId desc")
    suspend fun getAllTaskList(): List<TaskTable>

    @Query("UPDATE task_table SET isActive = 0 where dbId =:dbId")
    suspend fun doTaskComplete(dbId: Long)

    @Query("DELETE FROM task_table where dbId =:dbId")
    suspend fun deleteById(dbId: Long)
}
