package com.hishabee.test.todo.custom_view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.hishabee.test.todo.databinding.LayoutLoaderFragmentBinding

class LoaderDialogFragment : DialogFragment() {

    private var _binding: LayoutLoaderFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dialog?.setCancelable(true)
        _binding = LayoutLoaderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val window = dialog?.window!!
            val layoutParams = window.attributes
            window.setBackgroundDrawableResource(android.R.color.transparent)
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            window.attributes = layoutParams
            // To make Status-bar transparent
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)// required
            window.statusBarColor = Color.TRANSPARENT
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}