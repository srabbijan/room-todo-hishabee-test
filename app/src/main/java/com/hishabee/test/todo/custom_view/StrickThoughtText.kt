package com.hishabee.test.todo.custom_view

import android.text.SpannableString
import android.text.style.StrikethroughSpan

fun String.setStrikeThroughText(): SpannableString {
    val spannableString = SpannableString(this)
    if (this.isNotEmpty()) {
        val strikethroughSpan = StrikethroughSpan()
        spannableString.setSpan(
            strikethroughSpan,
            0,
            this.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return spannableString
}