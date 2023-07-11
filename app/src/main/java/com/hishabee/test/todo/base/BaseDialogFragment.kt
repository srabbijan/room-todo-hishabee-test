package com.hishabee.test.todo.base


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.hishabee.test.todo.R
import com.hishabee.test.todo.custom_view.LoaderDialogFragment

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {

    val TAG by lazy { this.javaClass.simpleName }
    private var _binding: VB? = null
    // Dialog fragment- Loader
    private var loaderDialogFragment: LoaderDialogFragment? = null

    val binding: VB
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.Full_Screen_Dialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            // To make Status-bar transparent
            dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)// required
            dialog?.window?.statusBarColor = Color.TRANSPARENT
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            viewDidCreated()
        }
    }

    fun hideKeyboard(view: View) {
        try {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            super.show(manager, tag)
        } catch (e: Exception) {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        }
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: Exception) {
            dismissAllowingStateLoss()
        }
    }

    //********* Loader Functionalities Start ************//

    /**
     * Show loader view.
     * @param cancelable Can user cancel the loader or not.
     */
    fun showLoader(cancelable: Boolean = false) {
        try {
            loaderDialogFragment?.let {
                if (it.isVisible) return
            } ?: kotlin.run {
                loaderDialogFragment = LoaderDialogFragment()
                loaderDialogFragment?.isCancelable = cancelable
            }
            loaderDialogFragment?.show(childFragmentManager, "SvsLoaderDialogFragment")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideLoader() {
        try {
            loaderDialogFragment?.dismissAllowingStateLoss()
            loaderDialogFragment = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected abstract fun getViewBinding(layoutInflater: LayoutInflater): VB
    protected abstract fun viewDidCreated()
}