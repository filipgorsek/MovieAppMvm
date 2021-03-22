package com.filip.movieappmvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.filip.movieappmvvm.extensions.clearBackStack

abstract class BaseActivity<BindingType : ViewBinding> : AppCompatActivity() {

    private var mBinding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> BindingType

    val binding: BindingType
        get() = mBinding as BindingType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(mBinding).root)
    }

    protected fun replaceFragment(@IdRes id: Int, fragment: Fragment, addToBackStack: Boolean = false) =
        with(supportFragmentManager.beginTransaction())
        {
            replace(id, fragment)
            if (addToBackStack) {
                addToBackStack(fragment.tag)
            }
            commitAllowingStateLoss()
        }

    fun addFragment(@IdRes id: Int, fragment: Fragment, addToBackStack: Boolean = false) =
        with(supportFragmentManager.beginTransaction()) {
            add(id, fragment)
            if (addToBackStack) {
                addToBackStack(fragment.tag)
            }
            commitAllowingStateLoss()

        }

    fun clearFragmentBackStack() {
        clearBackStack()
    }

}