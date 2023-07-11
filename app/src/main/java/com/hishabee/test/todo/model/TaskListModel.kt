package com.hishabee.test.todo.model

import com.hishabee.test.todo.database.entity.TaskTable

data class TaskListModel(
    var id : Long,
    var task : String,
    var isActive : Boolean
)
fun String.toTaskTable(): TaskTable {
    return TaskTable(
        null,
        this,
        true
    )
}
fun TaskTable.toTaskModel():TaskListModel{
    return TaskListModel(
        id = dbId?:0,
        task = task?:"",
        isActive = isActive?:true
    )
}