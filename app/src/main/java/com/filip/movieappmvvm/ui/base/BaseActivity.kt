package com.filip.movieappmvvm.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.filip.movieappmvvm.extensions.clearBackStack
import com.filip.movieappmvvm.extensions.goBack

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
            if (addToBackStack) {
                addToBackStack(fragment.tag)
            }
            replace(id, fragment)
            commitAllowingStateLoss()
        }

    fun clearFragmentBackStack() {
        clearBackStack()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        Log.d("FragmentCount",count.toString())
        if (count <= 1) {
            finish()
            overridePendingTransition(0, android.R.anim.fade_out)
        } else {
            goBack()
        }
    }
}