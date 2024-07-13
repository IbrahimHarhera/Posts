package com.ibrahim.task.presentation.posts

import android.os.Bundle
import android.view.View
import com.ibrahim.task.databinding.FragmentPostsBinding
import com.ibrahim.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PostsFragment : BaseFragment<FragmentPostsBinding>(FragmentPostsBinding::inflate) {
    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

}