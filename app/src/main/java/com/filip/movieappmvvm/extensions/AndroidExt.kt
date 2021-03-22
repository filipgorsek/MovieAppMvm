package com.filip.movieappmvvm.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun FragmentActivity.goBack() {
    supportFragmentManager.popBackStack()
}

fun FragmentActivity.clearBackStack() {
    val backStackEntryCount = supportFragmentManager.backStackEntryCount
    for (i in 0 until backStackEntryCount) {
        supportFragmentManager.popBackStack()
    }
}

fun FragmentActivity.showFragment(
    @IdRes container: Int, fragment: Fragment,
    addToBackStack: Boolean = false,
    withAnimation: Boolean = false
) {
    supportFragmentManager.beginTransaction()
        .apply {
            if (addToBackStack) {
                addToBackStack(fragment.tag)
            }
            if (withAnimation) {
                setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            }

        }
        .replace(container, fragment)
        .commitAllowingStateLoss()
}

inline fun <T> LiveData<T>.subscribe(owner: LifecycleOwner, crossinline onChange: (T) -> Unit) =
    observe(owner, Observer {
        it?.run { onChange(this) }
    })

fun FragmentManager.addDialogFragment(dialogFragment: DialogFragment) {
    beginTransaction()
        .add(dialogFragment, dialogFragment.tag)
        .commitAllowingStateLoss()
}
