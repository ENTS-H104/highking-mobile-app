package com.entsh104.highking.ui.cust.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entsh104.highking.databinding.ItemChatBinding

class ChatAdapter(
    private val chatList: List<Chat>,
    private val listener: OnChatItemClickListener
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatItem = chatList[position]
        holder.bind(chatItem)
    }

    override fun getItemCount() = chatList.size

    inner class ChatViewHolder(private val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatItem: Chat) {
            binding.textViewName.text = chatItem.name
            binding.textViewMessage.text = chatItem.message
            binding.textViewTime.text = chatItem.time
            binding.textViewUnreadCount.text = chatItem.unreadCount.toString()
            binding.root.setOnClickListener {
                listener.onChatItemClick(chatItem)
            }
        }
    }
}
