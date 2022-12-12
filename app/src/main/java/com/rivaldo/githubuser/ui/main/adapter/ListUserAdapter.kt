package com.rivaldo.githubuser.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldo.githubuser.R
import com.rivaldo.githubuser.databinding.CardViewUserBinding
import com.rivaldo.githubuser.model.User

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    lateinit var listUser: List<User>
    lateinit var onItemClick: ((User) -> Unit)

    @JvmName("setListUser1")
    fun setListUser(listUser: List<User>) {
        this.listUser = listUser
    }

    @JvmName("setOnItemClick1")
    fun setOnItemClick(onItemClick: (User) -> Unit) {
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            CardViewUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(private val binding: CardViewUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                if (user.avatarInt != null) {
                    circleImageView.setImageResource(user.avatarInt?: R.drawable.user1)
                } else {
                    Glide.with(itemView.context).load(user.avatar).into(circleImageView)
                }

                txtUserName.text = user.name
                txtCompany.text = user.company
                txtLocation.text = user.location
            }
            if (::onItemClick.isInitialized) {
                itemView.setOnClickListener {
                    onItemClick(user)
                }
            }

        }
    }

}