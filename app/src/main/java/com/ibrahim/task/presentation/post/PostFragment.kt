package com.ibrahim.task.presentation.post

import android.os.Bundle

import android.view.View

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ibrahim.task.R
import com.ibrahim.task.data.model.Post
import com.ibrahim.task.databinding.FragmentPostBinding
import com.ibrahim.task.presentation.utils.PostStatus
import com.ibrahim.task.presentation.utils.UPDATE
import com.ibrahim.ui.base.BaseFragment
import com.ibrahim.ui.extensions.collect
import com.ibrahim.ui.extensions.hide
import com.ibrahim.ui.extensions.navigateSafe
import com.ibrahim.ui.extensions.showMessageDialog
import com.ibrahim.ui.resProvider.IResourceProvider
import com.ibrahim.utility.connectivity.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PostFragment :
    BaseFragment<FragmentPostBinding>(FragmentPostBinding::inflate) {

    @Inject
    lateinit var resourceProvider: IResourceProvider
    private val viewModel: PostViewModel by viewModels()
    private var action: String? = null
    private var postsModel: Post? = null

    private fun getFragmentArguments() {
        val args: PostFragmentArgs by navArgs()
        action = args.action
        postsModel = args.post
    }

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

        getFragmentArguments()
        observer()
        viewBinding().apply {
            if (action == UPDATE) {
                etTitle.setText(postsModel?.title)
                etBody.setText(postsModel?.body)
            }
            confirm.setOnClickListener {
                etTitle.hide()
                etBody.hide()
                viewModel.addUpdatePost(
                    tvTitle.text.toString(),
                    tvBody.text.toString(),
                    action ?: ""
                )
            }
        }
    }

    private fun observer() {
        collect(viewModel.validation) {
            when (it) {
                PostStatus.IsValid -> {}
                PostStatus.Invalid ->
                    showMessageDialog(resourceProvider.getText(R.string.please_put_correct_data))
            }
        }
        collect(viewModel.addPostModel) {
            when (it) {
                is Resource.Success -> {
                    hideLoading()
                    if (it.value.uploaded) {
                        Toast.makeText(
                            requireActivity(),
                            resourceProvider.getText(R.string.created_successfully),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            resourceProvider.getText(R.string.created_local), Toast.LENGTH_SHORT
                        ).show()
                    }

                    navigateSafe(
                        PostFragmentDirections.actionPostFragmentToPostsFragment()
                    )
                }

                is Resource.Failure -> {
                    hideLoading()
                    showMessageDialog(it.exception.message ?: "")
                }

                else -> showLoading()
            }
        }

        collect(viewModel.updatePostModel) {
            when (it) {
                is Resource.Success -> {
                    hideLoading()
                    if (it.value.uploaded)
                        Toast.makeText(
                            requireActivity(),
                            resourceProvider.getText(R.string.updated_successfully),
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            requireActivity(),
                            getString(R.string.updated_local), Toast.LENGTH_SHORT
                        ).show()

                    navigateSafe(
                        PostFragmentDirections.actionPostFragmentToPostsFragment()
                    )

                }

                is Resource.Failure -> {
                    hideLoading()
                    showMessageDialog(it.exception.message ?: "")
                }

                else -> showLoading()
            }
        }
    }

}