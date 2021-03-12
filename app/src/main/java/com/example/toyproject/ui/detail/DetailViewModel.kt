package com.example.toyproject.ui.detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyproject.R
import com.example.toyproject.data.base.BaseResponse
import com.example.toyproject.data.model.RepoDetailModel
import com.example.toyproject.data.model.mapToPresentation
import com.example.toyproject.repository.RepoRepository
import com.example.toyproject.ui.model.RepoDetailItem
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repoRepository: RepoRepository
) : ViewModel() {

    val showLoading = MutableLiveData(false)
    val showErrorMessage = MutableLiveData("")
    val repoDetailItem = MutableLiveData<RepoDetailItem>()

    fun loadData(context: Context, ownerName: String, repo: String) {
        viewModelScope.launch {
            repoRepository.getDetailRepository(ownerName, repo, object : BaseResponse<RepoDetailModel> {
                override fun onSuccess(data: RepoDetailModel) {
                    repoDetailItem.postValue(data.mapToPresentation(context))
                }

                override fun onFail(description: String) {
                    showErrorMessage(description)
                }

                override fun onError(throwable: Throwable) {
                    showErrorMessage(
                        throwable.message ?: context.getString(R.string.unexpected_error)
                    )
                }

                override fun onLoading() {
                    hideErrorMessage()
                    showLoading()
                }

                override fun onLoaded() {
                    hideLoading()
                }
            })
        }
    }

    private fun showLoading() {
        showLoading.postValue(true)
    }

    private fun hideLoading() {
        showLoading.postValue(false)
    }

    private fun showErrorMessage(error: String) {
        showErrorMessage.postValue(error)
    }

    private fun hideErrorMessage() {
        showErrorMessage.postValue("")
    }
}