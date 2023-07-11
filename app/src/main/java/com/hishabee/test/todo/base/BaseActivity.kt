package com.hishabee.test.todo.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.hishabee.test.todo.custom_view.LoaderDialogFragment
import java.lang.reflect.ParameterizedType
import java.util.*


abstract class BaseActivity<baseVM : ViewModel, baseVB : ViewBinding> : AppCompatActivity() {

    val TAG by lazy { this.javaClass.simpleName }

    lateinit var viewModel: baseVM
    lateinit var binding: baseVB
    private var loaderDialogFragment: LoaderDialogFragment? = null

    var isOnline = false

    abstract fun getViewBinding(): baseVB
    protected open fun setupObserver() {}
    protected open fun initializeData() {}
    protected abstract fun setupUI()
    protected open fun callInitialApi() {}
    protected open fun logView() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[getViewModelClass()]
        binding = getViewBinding()
        setContentView(binding.root)
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        initializeData()
        setupObserver()
        setupUI()
        callInitialApi()
        logView()
    }

    private fun getViewModelClass(): Class<baseVM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        @Suppress("UNCHECKED_CAST")
        return type as Class<baseVM>
    }

    fun showLoader(isCancelAble: Boolean = false) {
        try {
            loaderDialogFragment?.let {
                if (it.isVisible) return
            } ?: kotlin.run {
                loaderDialogFragment = LoaderDialogFragment()
                loaderDialogFragment?.isCancelable = isCancelAble
            }
            loaderDialogFragment?.show(supportFragmentManager, "LoaderDialogFragment")
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

    //------------------------------------------------

    //------------------------------------------------
    // Handle API Error
    //------------------------------------------------


}