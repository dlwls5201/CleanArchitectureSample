package com.example.toyproject.ui

import android.os.Bundle
import com.example.toyproject.R
import com.example.toyproject.base.BaseActivity
import com.example.toyproject.databinding.ActivityMainBinding
import com.example.toyproject.ui.detail.DetailFragment
import com.example.toyproject.ui.search.SearchFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTitle()
        initMainFragment()
    }

    private fun initTitle() {
        title = getString(R.string.search_repository)
    }

    private fun initMainFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.lylFragmentContainer, SearchFragment.newInstance())
            .commit()
    }

    fun goToDetailFragment(ownerName: String, repoName: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.lylFragmentContainer, DetailFragment.newInstance(ownerName, repoName))
            .addToBackStack(null)
            .commit()
    }
}
