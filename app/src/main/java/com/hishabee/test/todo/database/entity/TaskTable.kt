package com.hishabee.test.todo.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskTable(
    @PrimaryKey(autoGenerate = true) val dbId: Long?,
    @ColumnInfo(name = "task") val task: String?,
    @ColumnInfo(name = "isActive") val isActive: Boolean?
)

