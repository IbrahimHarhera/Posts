package com.ibrahim.task.presentation.post

import androidx.lifecycle.viewModelScope
import com.ibrahim.task.data.model.Post
import com.ibrahim.task.data.model.RequestPost
import com.ibrahim.task.domain.useCases.AddUseCase
import com.ibrahim.task.domain.useCases.UpdateUseCase
import com.ibrahim.task.presentation.utils.CREATE
import com.ibrahim.task.presentation.utils.PostStatus
import com.ibrahim.ui.base.BaseViewModel
import com.ibrahim.utility.connectivity.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PostViewModel @Inject constructor(
    private val addPostUseCase: AddUseCase,
    private val updatePostUseCase: UpdateUseCase,
) : BaseViewModel() {
    private val _addPostModel = MutableSharedFlow<Resource<Post>>()
    val addPostModel: SharedFlow<Resource<Post>>
        get() = _addPostModel


    private val _updatePostModel = MutableSharedFlow<Resource<Post>>()
    val updatePostModel: SharedFlow<Resource<Post>>
        get() = _updatePostModel

    private val _validation = MutableStateFlow<PostStatus>(PostStatus.IsValid)
    val validation: StateFlow<PostStatus>
        get() = _validation


    fun addUpdatePost(
        title: String?,
        body: String?,
        action: String
    ) {
        if (title.isNullOrEmpty() || body.isNullOrEmpty()) {
            emitIfChanged(PostStatus.Invalid)
        } else {
            emitIfChanged(PostStatus.IsValid)
            if (action == CREATE)
                addPostUseCase(
                    RequestPost(title = title, body = body)
                ).onEach {
                    _addPostModel.emit(it)
                }.launchIn(viewModelScope)
            else
                updatePostUseCase(
                    RequestPost(
                        title = title, body = body
                    )
                ).onEach {
                    _updatePostModel.emit(it)
                }.launchIn(viewModelScope)
        }
    }


    private fun emitIfChanged(newValue: PostStatus) {
        if (_validation.value != newValue) {
            _validation.value = newValue
        }
    }
}