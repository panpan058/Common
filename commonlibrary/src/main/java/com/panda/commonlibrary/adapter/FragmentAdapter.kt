package com.panda.commonlibrary.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * <pre>
 * Created by ppW
 * e-mail : wangpanpan05@163.com
 * time   : 2020/08/05 18:00
 * desc   :
 * version: 1.0   初始化
 * params:
 * <pre>
</pre></pre> */
class FragmentAdapter<T>(
    fm: FragmentManager,
    private val mData: MutableList<T>,
    private val createFragment: (T) -> Fragment
) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return createFragment(mData[position])
    }

    override fun getCount(): Int {
        return mData.size
    }

}