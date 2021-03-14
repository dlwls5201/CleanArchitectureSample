package com.example.toyproject.ui.detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blackjin.domain.model.RepoDetail
import com.blackjin.domain.model.base.BaseResponse
import com.blackjin.domain.usecase.GetDetailRepoUsecase
import com.example.toyproject.R
import com.example.toyproject.ui.model.RepoDetailItem
import com.example.toyproject.ui.model.mapToPresentation
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getDetailRepoUsecase: GetDetailRepoUsecase
) : ViewModel() {

    val showLoading = MutableLiveData(false)
    val showErrorMessage = MutableLiveData("")
    val repoDetailItem = MutableLiveData<RepoDetailItem>()

    fun loadData(context: Context, ownerName: String, repo: String) {
        viewModelScope.launch {
            getDetailRepoUsecase(ownerName, repo, object : BaseResponse<RepoDetail> {
                override fun onSuccess(data: RepoDetail) {
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