package com.ibrahim.task.presentation.posts

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahim.task.R
import com.ibrahim.task.data.model.Post
import com.ibrahim.task.databinding.FragmentPostsBinding
import com.ibrahim.task.presentation.utils.CREATE
import com.ibrahim.task.presentation.utils.UPDATE
import com.ibrahim.ui.base.BaseFragment
import com.ibrahim.ui.extensions.collect
import com.ibrahim.ui.extensions.navigateSafe
import com.ibrahim.ui.extensions.showMessageDialog
import com.ibrahim.ui.resProvider.IResourceProvider
import com.ibrahim.utility.connectivity.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PostsFragment : BaseFragment<FragmentPostsBinding>(FragmentPostsBinding::inflate) {


    private val viewModel: PostsViewModel by viewModels()
    private lateinit var postsAdapter: PostsAdapter

    @Inject
    lateinit var resourceProvider: IResourceProvider

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getPosts()
        viewBinding().btnAdd.setOnClickListener {
            navigateSafe(
                PostsFragmentDirections.actionPostsFragmentToPostFragment(
                    Post(),
                    CREATE
                )
            )

        }
        observer()
    }

    private fun observer() {
        collect(viewModel.posts) {
            when (it) {
                is Resource.Success -> {
                    hideLoading()
                    initPosts(it.value)
                }

                is Resource.Failure -> {
                    hideLoading()
                    showMessageDialog(it.exception.message ?: "")
                }

                else -> showLoading()
            }
        }

        collect(viewModel.delete) {
            when (it) {
                is Resource.Success -> {
                    hideLoading()
                    if (it.value.uploaded)
                        Toast.makeText(
                            requireActivity(),
                            resourceProvider.getText(R.string.deleted_successfully),
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            requireActivity(),
                            resourceProvider.getText(R.string.deleted_local), Toast.LENGTH_SHORT
                        ).show()

                }

                is Resource.Failure -> {
                    hideLoading()
                    showMessageDialog(it.exception.message ?: "")
                }

                else -> showLoading()
            }
        }
    }

    private fun initPosts(list: List<Post?>?) {
        postsAdapter = PostsAdapter({
            viewModel.deletePost(it)
        }, {
            navigateSafe(
                PostsFragmentDirections.actionPostsFragmentToPostFragment(
                    it,
                    UPDATE
                )
            )
        }
        )
        postsAdapter.submitList(list)
        viewBinding().recyclerPosts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postsAdapter

        }
    }
}