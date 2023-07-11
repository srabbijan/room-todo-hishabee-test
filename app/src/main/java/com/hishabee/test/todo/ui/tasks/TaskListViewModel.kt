package com.hishabee.test.todo.ui.tasks

import androidx.lifecycle.viewModelScope
import com.hishabee.test.todo.base.BaseViewModel
import com.hishabee.test.todo.database.entity.TaskTable
import com.hishabee.test.todo.database.repository.TaskRepository
import com.hishabee.test.todo.uitls.DataBundleLiveData
import com.hishabee.test.todo.uitls.callRepo
import kotlinx.coroutines.Dispatchers

class TaskListViewModel:BaseViewModel() {
    var repo: TaskRepository? = null

    val saveTaskLiveData by lazy { DataBundleLiveData() }
    fun saveTask(
        request: TaskTable
    ) = viewModelScope.callRepo(Dispatchers.IO, saveTaskLiveData) {
        repo?.saveTask(request)
    }
    val fetchTaskLiveData by lazy { DataBundleLiveData() }
    fun fetchTask() = viewModelScope.callRepo(Dispatchers.IO, fetchTaskLiveData) {
        repo?.fetchTaskList()
    }

    val updateTaskLiveData by lazy { DataBundleLiveData() }
    fun updateTask(id:Long) = viewModelScope.callRepo(Dispatchers.IO, updateTaskLiveData) {
        repo?.makeItComplete(id)
    }
    val deleteTaskLiveData by lazy { DataBundleLiveData() }
    fun deleteTask(id:Long) = viewModelScope.callRepo(Dispatchers.IO, deleteTaskLiveData) {
        repo?.deleteById(id)
    }
}