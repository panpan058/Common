package com.panda.commonlibrary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseVBFragment<T : ViewBinding>(layoutId: Int) : Fragment(layoutId) {
    private var _binding: T? = null

    val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = initBinding(view)
        init()
    }

    /**
     * 初始化 [_binding]
     */
    abstract fun initBinding(view: View): T

    abstract fun init()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
