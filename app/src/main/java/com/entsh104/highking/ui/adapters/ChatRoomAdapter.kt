package com.entsh104.highking.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entsh104.highking.data.model.MessageItem
import com.entsh104.highking.databinding.ItemChatReceiverBinding
import com.entsh104.highking.databinding.ItemChatSenderBinding
import com.entsh104.highking.ui.model.ChatMessage

class ChatRoomAdapter(private val userEmail: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_SENDER = 1
        private const val VIEW_TYPE_RECEIVER = 2
    }

    private val chatList = mutableListOf<ChatMessage>()

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].email == userEmail) VIEW_TYPE_SENDER else VIEW_TYPE_RECEIVER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENDER) {
            val binding = ItemChatSenderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SenderViewHolder(binding)
        } else {
            val binding = ItemChatReceiverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReceiverViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatItem = chatList[position]
        if (holder is SenderViewHolder) {
            holder.bind(chatItem)
        } else if (holder is ReceiverViewHolder) {
            holder.bind(chatItem)
        }
    }

    override fun getItemCount() = chatList.size

    fun updateMessages(messages: List<MessageItem>) {
        chatList.clear()
        chatList.addAll(messages.map { messageItem ->
            ChatMessage(
                message = messageItem.message,
                time = messageItem.created_at,
                email = messageItem.email
            )
        })
        notifyDataSetChanged()
    }

    class SenderViewHolder(private val binding: ItemChatSenderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatItem: ChatMessage) {
            binding.textViewMessage.text = chatItem.message
            binding.textViewTime.text = chatItem.time
        }
    }

    class ReceiverViewHolder(private val binding: ItemChatReceiverBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatItem: ChatMessage) {
            binding.textViewMessage.text = chatItem.message
            binding.textViewTime.text = chatItem.time
        }
    }
}
