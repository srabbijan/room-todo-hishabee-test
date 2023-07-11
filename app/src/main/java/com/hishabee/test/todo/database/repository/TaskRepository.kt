package com.hishabee.test.todo.database.repository

import com.hishabee.test.todo.uitls.DataResource
import com.hishabee.test.todo.database.dao.TaskDao
import com.hishabee.test.todo.database.entity.TaskTable
import com.hishabee.test.todo.model.TaskListModel
import com.hishabee.test.todo.model.toTaskModel

class TaskRepository(
    private val dao: TaskDao
) {
    suspend fun saveTask(
        dataList: TaskTable
    ): DataResource<*> {
        dao.insertData(dataList)
        return DataResource.success(data = true)
    }


    suspend fun fetchTaskList(): DataResource<*> {
        val draftList: List<TaskTable> = dao.getAllTaskList()
        val dataList : ArrayList<TaskListModel> = arrayListOf()
        draftList.forEach {
            dataList.add(it.toTaskModel())
        }
        return DataResource.success(data = dataList)
    }

    suspend fun makeItComplete(
        dbId: Long
    ): DataResource<*> {
        dao.doTaskComplete(dbId)
        return DataResource.success(data = true)
    }
    suspend fun deleteById(
        dbId: Long
    ): DataResource<*> {
        dao.deleteById(dbId)
        return DataResource.success(data = true)
    }
}