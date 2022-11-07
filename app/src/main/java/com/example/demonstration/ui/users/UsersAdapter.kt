package com.example.demonstration.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.demonstration.databinding.UserItemLayoutBinding
import com.example.demonstration.models.User


class UsersAdapter(
    private val onUserClicked: (String?) -> Unit
): ListAdapter<User, UsersAdapter.UsersViewHolder>(diffUtil) {

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
        }
    }

    override fun submitList(list: List<User>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    inner class UsersViewHolder(private val binding: UserItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener { onUserClicked(binding.user?.login) }
        }
        fun bind(user: User){
            binding.user = user
            Glide.with(binding.root)
                .asBitmap()
                .load(user.avatarUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(25)))
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserItemLayoutBinding.inflate(inflater)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}