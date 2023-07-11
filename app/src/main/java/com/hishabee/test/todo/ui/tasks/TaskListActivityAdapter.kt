package com.hishabee.test.todo.ui.tasks

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hishabee.test.todo.custom_view.setStrikeThroughText
import com.hishabee.test.todo.databinding.ItemTaskActiveBinding
import com.hishabee.test.todo.databinding.ItemTaskInactiveBinding
import com.hishabee.test.todo.model.TaskListModel

enum class TaskViewType(val value: Int) {
    ACTIVE(1),
    INACTIVE(2),

}

class TaskListActivityAdapter(
    private val onItemClicked: ((TaskListModel) -> Unit)? = null,
    private val onItemDelete:((TaskListModel) -> Unit)? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var mContext: Context
    private var dataList: ArrayList<TaskListModel> = arrayListOf()
    @SuppressLint("NotifyDataSetChanged")
    fun dataLoad(list: List<TaskListModel>) {
        this.dataList.clear()
        this.dataList.addAll(list)
        notifyDataSetChanged()
    }
    fun deleteItem(position: Int) {
        onItemDelete?.invoke(dataList[position])
        dataList.removeAt(position)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ActiveItemViewHolder -> {
                holder.setValues(dataList[position])
            }
            is InactiveItemViewHolder -> {
                holder.setValues(dataList[position])
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataList.isEmpty()) TaskViewType.ACTIVE.value
        else when (dataList[position].isActive) {
            true -> TaskViewType.ACTIVE.value
            false -> TaskViewType.INACTIVE.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        return when (viewType) {
            TaskViewType.INACTIVE.value -> {
                InactiveItemViewHolder(
                    ItemTaskInactiveBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                ActiveItemViewHolder(
                    ItemTaskActiveBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }


    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class ActiveItemViewHolder(private val binding: ItemTaskActiveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setValues(data: TaskListModel) {
            binding.tvTitle.text = data.task
            binding.root.setOnClickListener {
                onItemClicked?.invoke(data)
            }
        }
    }

    inner class InactiveItemViewHolder(private val binding: ItemTaskInactiveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setValues(data: TaskListModel) {
            binding.tvTitle.text = data.task.setStrikeThroughText()
            binding.root.setOnClickListener {
                onItemClicked?.invoke(data)
            }
        }
    }


}