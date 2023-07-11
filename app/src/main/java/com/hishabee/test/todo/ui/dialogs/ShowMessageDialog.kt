package com.hishabee.test.todo.ui.dialogs

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.hishabee.test.todo.R
import com.hishabee.test.todo.databinding.DialogShowMessageBinding


/**
 * Template Dialog Class to Show Message
 */
class ShowMessageDialog() : DialogFragment() {

    var onNegativeButtonClick: (() -> Unit)? = null

    var dialogMessage: String? = null

    var negativeButtonText: String? = null

    private var _binding: DialogShowMessageBinding? = null

    private val binding: DialogShowMessageBinding
        get() = _binding!!

    constructor(
        dialogMessage: String,
        negativeButtonText: String? = null,
        onNegativeButtonClick: (() -> Unit)? = null
    ) : this() {

        this.dialogMessage = dialogMessage
        this.negativeButtonText = negativeButtonText
        this.onNegativeButtonClick = onNegativeButtonClick
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.Full_Screen_Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogShowMessageBinding.inflate(inflater, container, false)
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
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)// required
            window.statusBarColor = Color.TRANSPARENT
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.tvMessage.text = dialogMessage ?: ""
        binding.tvNegativeButton.text = negativeButtonText ?: getString(R.string.text_close)

        binding.tvNegativeButton.setOnClickListener {
            dismiss()
            onNegativeButtonClick?.invoke()
        }
    }

}