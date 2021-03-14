package com.example.toyproject.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.toyproject.R
import com.example.toyproject.base.BaseFragment
import com.example.toyproject.base.ext.longToast
import com.example.toyproject.base.ext.observeEvent
import com.example.toyproject.databinding.FragmentSearchBinding
import com.example.toyproject.injection.Injection
import com.example.toyproject.ui.MainActivity
import com.example.toyproject.ui.adapter.RepositoryAdapter
import com.example.toyproject.utils.AppUtils

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    companion object {

        fun newInstance() = SearchFragment()
    }

    private val repoAdapter by lazy {
        RepositoryAdapter().apply {
            onItemClick = {
                (requireActivity() as MainActivity).goToDetailFragment(
                    it.owner.ownerName,
                    it.repoName
                )
                hideKeyboard()
            }
        }
    }

    private val searchModel by lazy {
        ViewModelProvider(viewModelStore, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SearchViewModel(
                    Injection.provideRepoRepository()
                ) as T
            }
        }).get(SearchViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = searchModel
        initRecyclerView()
        initEditText()
    }

    override fun onViewModelSetup() {
        searchModel.items.observe(viewLifecycleOwner) {
            repoAdapter.setItems(it)
        }

        searchModel.eventShowKeyboard.observeEvent(viewLifecycleOwner) {
            if (it) {
                showKeyboard()
            } else {
                hideKeyboard()
            }
        }

        searchModel.eventToast.observeEvent(viewLifecycleOwner) {
            longToast(it)
        }
    }

    private fun initRecyclerView() {
        binding.listSearchRepository.adapter = repoAdapter
    }

    private fun initEditText() {
        binding.etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        searchModel.searchRepository(requireContext())
                        return true
                    }
                    else -> {
                        return false
                    }
                }
            }
        })
    }

    private fun showKeyboard() {
        binding.etSearch.requestFocus()
        AppUtils.showSoftKeyBoard(requireActivity())
    }

    private fun hideKeyboard() {
        AppUtils.hideSoftKeyBoard(requireActivity())
    }
}