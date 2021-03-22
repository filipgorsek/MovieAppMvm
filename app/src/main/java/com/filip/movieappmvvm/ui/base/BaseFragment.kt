package com.filip.movieappmvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<BindingType : ViewBinding> : Fragment() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BindingType

    val binding: BindingType
        get() = _binding as BindingType

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun goBack() {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).onBackPressed()
        }
    }

    protected fun clearFragmentStack() {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).clearFragmentBackStack()
        }
    }
}