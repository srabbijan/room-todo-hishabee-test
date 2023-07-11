package com.hishabee.test.todo.base

import com.hishabee.test.todo.R
import com.hishabee.test.todo.ui.dialogs.ConfirmationDialog
import com.hishabee.test.todo.ui.dialogs.ShowMessageDialog

fun BaseActivity<*, *>.showErrorDialog(
    message: String?,
    buttonText: String = getString(R.string.okay),
    onButtonClick: (() -> Unit)? = null,
) {
    val dialog = ShowMessageDialog().apply {
        this.dialogMessage = message
        this.negativeButtonText = buttonText
        this.onNegativeButtonClick = onButtonClick
    }
    try {
        dialog.show(supportFragmentManager, "ShowSingleButtonPopUpDialog")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun BaseActivity<*, *>.showConfirmationDialog(
    dialogTitle: String = getString(R.string.confirmation),
    dialogMessage: String?,
    positiveButtonText:String = getString(R.string.text_Yes),
    negativeButtonText:String = getString(R.string.text_Cancel),
    onPositiveButtonClick : (() -> Unit)? = null,
    onNegativeButtonClick :(() -> Unit)? = null
) {
    val dialog = ConfirmationDialog().apply {
        this.dialogTitle = dialogTitle
        this.dialogMessage = dialogMessage
        this.positiveButtonText = positiveButtonText
        this.onPositiveButtonClick = onPositiveButtonClick
        this.negativeButtonText = negativeButtonText
        this.onNegativeButtonClick = onNegativeButtonClick
    }
    try {
        dialog.show(supportFragmentManager, "ShowSingleButtonPopUpDialog")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}



