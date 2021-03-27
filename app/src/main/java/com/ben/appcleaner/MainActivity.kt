package com.ben.appcleaner

import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import com.ben.appcleaner.cleaner.fragment.CleanerFragment
import com.ben.appcleaner.cleaner.viewmodel.CleanerViewModel
import com.ben.appcleaner.databinding.ActivityMainBinding
import com.ben.libcom.base.BaseActivity
import com.ben.libcom.base.BaseFragment
import com.ben.libcom.base.BaseNormalActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * 首页：管理四个fragment
 * @author: BD
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var mCurrentFragment: BaseFragment<*, *>? = null
    private val mFragmentMap: SparseArray<BaseFragment<*, *>> =
        SparseArray(4)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        mFragmentMap.put(R.id.rb_cleaner, CleanerFragment.newInstance())
        mFragmentMap.put(R.id.rb_slimming, CleanerFragment.newInstance())
        mFragmentMap.put(R.id.rb_video, CleanerFragment.newInstance())
        mFragmentMap.put(R.id.rb_mine, CleanerFragment.newInstance())

        binding.rgTab.setOnCheckedChangeListener { _, checkedId ->
            val ft = supportFragmentManager.beginTransaction()
            val nextFragment: BaseFragment<*, *> = mFragmentMap[checkedId]
            if (mCurrentFragment == null) {
                ft.add(R.id.container, nextFragment.also { mCurrentFragment = it })
            } else {
                if (nextFragment.isAdded) {
                    ft.hide(mCurrentFragment!!)
                    ft.show(nextFragment.also { mCurrentFragment = it })
                } else {
                    ft.hide(mCurrentFragment!!)
                    ft.add(R.id.container, nextFragment.also { mCurrentFragment = it })
                }
            }
            ft.commitNowAllowingStateLoss()
        }
        binding.rgTab.check(R.id.rb_cleaner)
    }

}
