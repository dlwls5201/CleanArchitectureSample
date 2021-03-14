package com.example.toyproject.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.toyproject.R
import com.example.toyproject.base.BaseFragment
import com.example.toyproject.databinding.FragmentDetailBinding
import com.example.toyproject.injection.Injection
import com.example.toyproject.utils.Dlog

class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    companion object {

        private const val ARGUMENT_OWNER_NAME = "owner_name"

        private const val ARGUMENT_REPO = "repo"

        fun newInstance(ownerName: String, repoName: String) = DetailFragment()
            .apply {
                arguments = bundleOf(
                    Pair(ARGUMENT_OWNER_NAME, ownerName),
                    Pair(ARGUMENT_REPO, repoName)
                )
            }
    }

    private val detailModel by lazy {
        ViewModelProvider(viewModelStore, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DetailViewModel(
                    Injection.provideGetDetailRepoUsecase()
                ) as T
            }
        }).get(DetailViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ownerName = arguments?.getString(ARGUMENT_OWNER_NAME) ?: throw IllegalArgumentException(
            "No owner name info exists in extras"
        )

        val repo = arguments?.getString(ARGUMENT_REPO) ?: throw IllegalArgumentException(
            "No repo info exists in extras"
        )

        Dlog.d("ownerName : $ownerName , repo : $repo")
        binding.model = detailModel
        detailModel.loadData(requireContext(), ownerName, repo)
    }
}
