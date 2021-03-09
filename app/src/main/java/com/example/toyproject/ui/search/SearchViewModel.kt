package com.example.toyproject.ui.search

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyproject.R
import com.example.toyproject.data.base.BaseResponse
import com.example.toyproject.data.model.RepoSearchResponse
import com.example.toyproject.data.model.mapToPresentation
import com.example.toyproject.repository.RepoRepository
import com.example.toyproject.ui.model.RepoItem
import com.example.toyproject.utils.Dlog
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: RepoRepository
) : ViewModel() {

    val isLoading = MutableLiveData(false)

    val isKeyboard = MutableLiveData(false)

    val errorMessage = MutableLiveData("")

    val editSearchText = MutableLiveData("")

    val enableSearchButton = MediatorLiveData<Boolean>().apply {
        addSource(editSearchText) { query ->
            Dlog.d("enableSearchButton query : $query")
            if (query.isNullOrEmpty()) {
                postValue(false)
            } else {
                postValue(true)
            }
        }
    }

    val items = MutableLiveData<List<RepoItem>>(emptyList())

    fun searchRepository(context: Context) {
        hideKeyboard()

        viewModelScope.launch {
            val query = editSearchText.value ?: return@launch
            Dlog.d("query : $query")

            searchRepository.searchRepositories(query, object : BaseResponse<RepoSearchResponse> {
                override fun onSuccess(data: RepoSearchResponse) {
                    items.postValue(data.items.map { it.mapToPresentation(context) })

                    if (0 == data.totalCount) {
                        showErrorMessage(context.getString(R.string.no_search_result))
                    }
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
                    clearItems()
                    showLoading()
                    hideErrorMessage()
                }

                override fun onLoaded() {
                    hideLoading()
                }
            })
        }
    }

    private fun clearItems() {
        items.postValue(emptyList())
    }

    private fun showLoading() {
        isLoading.postValue(true)
    }

    private fun hideLoading() {
        isLoading.postValue(false)
    }

    private fun hideKeyboard() {
        isKeyboard.postValue(false)
    }

    private fun showErrorMessage(error: String) {
        errorMessage.postValue(error)
    }

    private fun hideErrorMessage() {
        errorMessage.postValue("")
    }
}