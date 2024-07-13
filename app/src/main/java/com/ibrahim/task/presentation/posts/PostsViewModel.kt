package com.ibrahim.task.presentation.posts

import androidx.lifecycle.viewModelScope
import com.ibrahim.task.data.model.Post
import com.ibrahim.task.domain.useCases.DeleteUseCase
import com.ibrahim.task.domain.useCases.PostsUseCase
import com.ibrahim.ui.base.BaseViewModel
import com.ibrahim.utility.connectivity.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PostsViewModel @Inject constructor(
    private val deleteUseCase: DeleteUseCase,
    private val postsUseCase: PostsUseCase
) : BaseViewModel() {

    private val _posts = MutableSharedFlow<Resource<List<Post?>?>>()
    val posts: SharedFlow<Resource<List<Post?>?>>
        get() = _posts
    private val _delete = MutableSharedFlow<Resource<Post>>()
    val delete: SharedFlow<Resource<Post>>
        get() = _delete

    fun getPosts() {
        postsUseCase(Unit).onEach {
            _posts.emit(it)
        }.launchIn(viewModelScope)
    }

    fun deletePost(id: Int) {
        deleteUseCase(id)
            .onEach {
                _delete.emit(it)
            }.launchIn(viewModelScope)
    }
}