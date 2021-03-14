package com.example.toyproject.ui.search

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blackjin.data.base.BaseResponse
import com.blackjin.data.model.RepoSearchResponse
import com.blackjin.data.repository.RepoRepository
import com.example.toyproject.R
import com.example.toyproject.base.ext.EventMutableLiveData
import com.example.toyproject.base.ext.postEvent
import com.example.toyproject.ui.model.RepoItem
import com.example.toyproject.ui.model.mapToPresentation
import com.example.toyproject.utils.Dlog
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: RepoRepository
) : ViewModel() {

    val eventShowKeyboard = EventMutableLiveData<Boolean>()
    val eventToast = EventMutableLiveData<String>()

    val showLoading = MutableLiveData(false)
    val showErrorMessage = MutableLiveData("")
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

    init {
        showKeyboard()
    }

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
        showLoading.postValue(true)
    }

    private fun hideLoading() {
        showLoading.postValue(false)
    }

    private fun showKeyboard() {
        eventShowKeyboard.postEvent(true)
    }

    private fun hideKeyboard() {
        eventShowKeyboard.postEvent(false)
    }

    private fun showErrorMessage(error: String) {
        showErrorMessage.postValue(error)
    }

    private fun hideErrorMessage() {
        showErrorMessage.postValue("")
    }
}