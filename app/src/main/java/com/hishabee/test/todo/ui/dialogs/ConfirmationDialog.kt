package com.hishabee.test.todo.ui.dialogs

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.hishabee.test.todo.R
import com.hishabee.test.todo.databinding.DialogConfirmationBinding

class ConfirmationDialog() : DialogFragment() {

     var onPositiveButtonClick: (() -> Unit)? = null

     var onNegativeButtonClick: (() -> Unit)? = null


     var dialogTitle: String? = null

     var dialogMessage: String? = null

     var positiveButtonText: String? = null

     var negativeButtonText: String? = null

    private var _binding: DialogConfirmationBinding? = null

    private val binding: DialogConfirmationBinding
        get() = _binding!!

    constructor(
        dialogTitle: String,
        dialogMessage: String,
        positiveButtonText: String? = null,
        negativeButtonText: String? = null,
        onPositiveButtonClick: (() -> Unit)? = null,
        onNegativeButtonClick: (() -> Unit)? = null
    ) : this() {
        this.dialogTitle = dialogTitle
        this.dialogMessage = dialogMessage
        this.positiveButtonText = positiveButtonText
        this.negativeButtonText = negativeButtonText
        this.onPositiveButtonClick = onPositiveButtonClick
        this.onNegativeButtonClick = onNegativeButtonClick
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.Full_Screen_Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val window = dialog!!.window!!
            window.setBackgroundDrawableResource(android.R.color.transparent)
            // To make Status-bar transparent
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)// required
            window.statusBarColor = Color.TRANSPARENT
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.tvTitle.text = dialogTitle ?: getString(R.string.confirmation)
        binding.tvMsg.text = dialogMessage ?: ""
        binding.tvPositiveButton.text = positiveButtonText ?: getString(R.string.text_Yes)
        binding.tvNegativeButton.text = negativeButtonText ?: getString(R.string.text_Cancel)

        binding.tvPositiveButton.setOnClickListener {
            dismiss()
            onPositiveButtonClick?.invoke()
        }
        binding.tvNegativeButton.setOnClickListener {
            dismiss()
            onNegativeButtonClick?.invoke()
        }
    }
}