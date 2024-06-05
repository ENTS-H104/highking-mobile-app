package com.entsh104.highking.ui.cust.chatroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entsh104.highking.databinding.ItemChatMessageBinding

class ChatRoomAdapter(private val chatMessages: List<ChatMessage>) : RecyclerView.Adapter<ChatRoomAdapter.ChatRoomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        val binding = ItemChatMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatRoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        holder.bind(chatMessages[position])
    }

    override fun getItemCount(): Int = chatMessages.size

    inner class ChatRoomViewHolder(private val binding: ItemChatMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMessage: ChatMessage) {
            binding.textViewMessage.text = chatMessage.message
            binding.textViewTimestamp.text = chatMessage.timestamp
        }
    }
}
