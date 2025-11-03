package com.devsphere.interfaceimpl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devsphere.interfaceimpl.databinding.ItemUserBinding

class UserAdapter(
    private var items: List<User>,
    private val listener: OnUserClickListener
) : RecyclerView.Adapter<UserAdapter.VH>() {

    interface OnUserClickListener {
        fun onUserClicked(user: User)
    }

    inner class VH(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(u: User) {
            binding.tvName.text = u.name
            binding.tvEmail.text = u.email
            binding.root.setOnClickListener { listener.onUserClicked(u) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun submitData(newItems: List<User>) {
        items = newItems
        notifyDataSetChanged()
    }
}
