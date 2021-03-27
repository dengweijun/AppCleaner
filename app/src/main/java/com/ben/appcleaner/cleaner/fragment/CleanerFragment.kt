package com.ben.appcleaner.cleaner.fragment

import android.os.Bundle
import com.ben.appcleaner.cleaner.viewmodel.CleanerViewModel
import com.ben.appcleaner.databinding.FragmentCleanerBinding
import com.ben.libcom.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 *  页面：清理
 * @author: BD
 */
@AndroidEntryPoint
class CleanerFragment : BaseFragment<FragmentCleanerBinding, CleanerViewModel>() {

    companion object {
        fun newInstance(): CleanerFragment {
            val args = Bundle()
            val fragment = CleanerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        binding.lifecycleOwner = this
    }

    override fun initData() {

    }
}