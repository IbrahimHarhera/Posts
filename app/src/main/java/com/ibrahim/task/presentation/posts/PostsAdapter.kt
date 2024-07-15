package com.ibrahim.task.presentation.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.task.data.model.Post
import com.ibrahim.task.databinding.ItemPostBinding

class PostsAdapter(
    private val itemClickDelete: (Int) -> Unit,
    private val itemClickEdit: (Post) -> Unit
) :
    ListAdapter<Post, PostsAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HistoryViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.ivDelete.setOnClickListener {
                itemClickDelete.invoke(getItem(layoutPosition).id)
            }
            binding.ivEdit.setOnClickListener {
                itemClickEdit.invoke(getItem(layoutPosition))
            }
        }

        fun bind(post: Post) {
            binding.apply {
                tvUserID.text = post.realId.toString().plus(" - ")
                tvTitle.text = post.title
                tvBody.text = post.body
            }
        }
    }
}