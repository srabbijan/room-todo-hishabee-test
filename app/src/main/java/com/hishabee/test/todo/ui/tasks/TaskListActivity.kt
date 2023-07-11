package com.hishabee.test.todo.ui.tasks

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.hishabee.test.todo.R
import com.hishabee.test.todo.application.AppManager
import com.hishabee.test.todo.base.BaseActivity
import com.hishabee.test.todo.base.showConfirmationDialog
import com.hishabee.test.todo.base.showErrorDialog
import com.hishabee.test.todo.custom_view.SwipeToDeleteCallback
import com.hishabee.test.todo.database.repository.TaskRepository
import com.hishabee.test.todo.databinding.ActivityTaskListBinding
import com.hishabee.test.todo.model.TaskListModel
import com.hishabee.test.todo.model.toTaskTable
import com.hishabee.test.todo.ui.dialogs.TaskInsertDialog
import com.hishabee.test.todo.uitls.DataBundle
import com.hishabee.test.todo.uitls.DataObserver
import com.hishabee.test.todo.uitls.Status

class TaskListActivity : BaseActivity<TaskListViewModel, ActivityTaskListBinding>() {
    private val saveTaskObserver = DataObserver { onSaveTaskResponse(it) }
    private val fetchTaskObserver = DataObserver { onFetchTaskResponse(it) }
    private val updateTaskObserver = DataObserver { onUpdateTaskResponse(it) }
    private val deleteTaskObserver = DataObserver { onDeleteTaskResponse(it) }

    private var isListView = true
    private lateinit var adapter: TaskListActivityAdapter
    override fun getViewBinding(): ActivityTaskListBinding {
        return ActivityTaskListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setupUI() {
        setRecyllerView()
        binding.btnAddTask.setOnClickListener {
            openTaskAddDialog()
        }
        binding.actionBar.ivListGrid.setOnClickListener {
            when (isListView) {
                true -> {
                    binding.rvTaskList.apply {
                        layoutManager = GridLayoutManager(this@TaskListActivity, 2)
                    }
                    binding.actionBar.ivListGrid.setImageDrawable(getDrawable(R.drawable.list))
                }
                false -> {
                    binding.rvTaskList.apply {
                        layoutManager = LinearLayoutManager(this@TaskListActivity)
                    }
                    binding.actionBar.ivListGrid.setImageDrawable(getDrawable(R.drawable.menu))
                }
            }

            isListView = !isListView
        }
    }

    private fun setRecyllerView() {
        adapter = TaskListActivityAdapter(
            onItemClicked = ::makeTaskComplete,
            onItemDelete = ::onDelete
        )
        binding.rvTaskList.adapter = adapter
        val swipeToDeleteCallback = SwipeToDeleteCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvTaskList)
    }

    override fun initializeData() {
        viewModel.repo = TaskRepository(
            (application as AppManager).database.taskDao()
        )
    }

    override fun setupObserver() {
        viewModel.saveTaskLiveData.observe(this, saveTaskObserver)
        viewModel.fetchTaskLiveData.observe(this, fetchTaskObserver)
        viewModel.updateTaskLiveData.observe(this, updateTaskObserver)
        viewModel.deleteTaskLiveData.observe(this, deleteTaskObserver)
    }

    override fun callInitialApi() {
        viewModel.fetchTask()
    }

    private fun openTaskAddDialog() {
        val fragmentManager = supportFragmentManager
        val dialog = TaskInsertDialog()
        dialog.onCloseClick { dialog.dismissAllowingStateLoss() }
        dialog.onDoneClick {
            dialog.dismissAllowingStateLoss()
            if (it != null && it.isNotEmpty()) {
                viewModel.saveTask(
                    it.toString().toTaskTable()
                )
            }

        }
        dialog.show(fragmentManager, "add_dialog")
    }

    private fun onSaveTaskResponse(response: DataBundle) {
        when (response.status) {
            Status.SUCCESS -> {
                hideLoader()
                val data = response.data as? Boolean ?: return
                if (data){
                    viewModel.fetchTask()
                }
            }

            Status.ERROR -> {
                hideLoader()
            }

            Status.LOADING -> {
                showLoader(isCancelAble = true)
            }
        }
    }

    private fun onUpdateTaskResponse(response: DataBundle) {
        when (response.status) {
            Status.SUCCESS -> {
                hideLoader()
                val data = response.data as? Boolean ?: return
                if (data){
                    viewModel.fetchTask()
                }
            }

            Status.ERROR -> {
                hideLoader()
            }

            Status.LOADING -> {
                showLoader(isCancelAble = true)
            }
        }
    }

    private fun onDeleteTaskResponse(response: DataBundle) {
        when (response.status) {
            Status.SUCCESS -> {
                hideLoader()
                val data = response.data as? Boolean ?: return
                if (data){
                    viewModel.fetchTask()
                }
            }

            Status.ERROR -> {
                hideLoader()
            }

            Status.LOADING -> {
                showLoader(isCancelAble = true)
            }
        }
    }

    private fun onFetchTaskResponse(response: DataBundle) {
        when (response.status) {
            Status.SUCCESS -> {
                hideLoader()
                val data = response.data as? List<TaskListModel> ?: return
                adapter.dataLoad(data)

            }

            Status.ERROR -> {
                hideLoader()
            }

            Status.LOADING -> {
                showLoader(isCancelAble = true)
            }
        }
    }

    private fun makeTaskComplete(data: TaskListModel) {
        if (data.isActive) {
            showConfirmationDialog(
                dialogMessage = getString(R.string.make_it_done),
                onPositiveButtonClick = {
                    viewModel.updateTask(data.id)
                }
            )
        }
    }

    private fun onDelete(data: TaskListModel) {
        showConfirmationDialog(
            dialogMessage = getString(R.string.delete_it),
            onPositiveButtonClick = {
                viewModel.deleteTask(data.id)
            },
            onNegativeButtonClick = {
                viewModel.fetchTask()
            }
        )
    }
}