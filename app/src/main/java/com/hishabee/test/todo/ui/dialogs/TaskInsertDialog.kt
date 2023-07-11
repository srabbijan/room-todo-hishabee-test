package com.hishabee.test.todo.ui.dialogs

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hishabee.test.todo.base.BaseDialogFragment
import com.hishabee.test.todo.databinding.DialogTaskAddBinding


class TaskInsertDialog() :
    BaseDialogFragment<DialogTaskAddBinding>() {
    private var onDoneClicked : ((String?)->Unit)? = null
    private var onCloseClicked : (()->Unit)? = null


    override fun getViewBinding(layoutInflater: LayoutInflater): DialogTaskAddBinding {
        return DialogTaskAddBinding.inflate(
            layoutInflater,
            null,
            false
        )
    }

    override fun viewDidCreated() {

        binding.buttonClose.setOnClickListener { onCloseClicked?.invoke() }
        binding.buttonAction.setOnClickListener {
            onDoneClicked?.invoke(binding.noteInputEt.text.toString())
        }

    }

    fun onDoneClick(clicked: ((String?) -> Unit)) {
        this.onDoneClicked = clicked
    }
    fun onCloseClick(clicked: (() -> Unit)) {
        this.onCloseClicked = clicked
    }
    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog?.window?.setLayout(width, height)
        }
    }
}