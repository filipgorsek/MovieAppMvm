package com.filip.movieappmvvm.extensions

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.filip.movieappmvvm.common.Lambda

inline fun EditText.onTextChange(crossinline onChange: (String) -> Unit) =
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(input: CharSequence?, start: Int, before: Int, count: Int) {
            onChange(input?.toString() ?: "")
        }
    })

inline fun View.onClick(waitTime: Long = 1000L, crossinline onClick: Lambda<View>) {
    var lastClickTime = 0L
    setOnClickListener {
        if (System.currentTimeMillis() > lastClickTime + waitTime) {
            onClick(it)
            lastClickTime = System.currentTimeMillis()
        }
    }
}

fun ImageView.loadImage(urlImage: String) {
    Glide.with(this).load(urlImage).into(this)
}

fun ImageView.loadDrawable(res: Int) {
    Glide.with(this).load(res).into(this)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.isVisible() = visibility == View.VISIBLE

fun View.gone() {
    visibility = View.GONE
}

fun TextView.changeTextColor(tintRes: Int) {
    setTextColor(ContextCompat.getColor(context, tintRes))
}

fun Button.changeBackgroundColor(tintRes: Int) {
    setBackgroundColor(ContextCompat.getColor(context, tintRes))
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}