package com.hishabee.test.todo.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val TAG: String by lazy {
        this.javaClass.simpleName
    }

    override fun onCleared() {
        super.onCleared()
    }
}