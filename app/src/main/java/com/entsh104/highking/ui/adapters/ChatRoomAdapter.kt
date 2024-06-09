package com.entsh104.highking.ui.cust.chatroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entsh104.highking.databinding.ItemChatReceiverBinding
import com.entsh104.highking.databinding.ItemChatSenderBinding

data class ChatMessage(val message: String, val time: String, val isSender: Boolean)

class ChatRoomAdapter(private val chatList: List<ChatMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_SENDER = 1
        private const val VIEW_TYPE_RECEIVER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].isSender) VIEW_TYPE_SENDER else VIEW_TYPE_RECEIVER
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
