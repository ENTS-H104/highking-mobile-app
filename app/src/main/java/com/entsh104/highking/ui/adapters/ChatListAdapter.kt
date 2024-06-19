package com.entsh104.highking.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.entsh104.highking.data.model.MessageItem
import com.entsh104.highking.data.model.MitraProfile
import com.entsh104.highking.databinding.ItemChatListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class ChatListAdapter(
    private val onChatItemClickListener: (String) -> Unit,
    private val fetchMitraProfile: suspend (String) -> MitraProfile?
) : RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    private val chatList = mutableListOf<MessageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatItem = chatList[position]
        holder.bind(chatItem)
    }

    override fun getItemCount() = chatList.size

    fun updateChatList(newChatList: List<MessageItem>) {
        chatList.clear()
        chatList.addAll(newChatList)
        notifyDataSetChanged()
    }

    inner class ChatViewHolder(private val binding: ItemChatListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(messageItem: MessageItem) {
            binding.textViewMessage.text = messageItem.message
            binding.textViewTime.text = formatTimestamp(messageItem.created_at)

            itemView.setOnClickListener {
                onChatItemClickListener(messageItem.partner_uid)
            }

            CoroutineScope(Dispatchers.Main).launch {
                val mitraProfile = withContext(Dispatchers.IO) { fetchMitraProfile(messageItem.partner_uid) }
                mitraProfile?.let {
                    binding.textViewName.text = it.username
                    Glide.with(binding.imageViewProfile.context)
                        .load(it.image_url)
                        .into(binding.imageViewProfile)
                }
            }
        }

        private fun formatTimestamp(timestamp: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val date = inputFormat.parse(timestamp)
            val now = Calendar.getInstance().time

            val outputFormat: SimpleDateFormat = when {
                isSameDay(date, now) -> SimpleDateFormat("HH:mm", Locale.getDefault())
                isYesterday(date, now) -> SimpleDateFormat("'Yesterday' HH:mm", Locale.getDefault())
                else -> SimpleDateFormat("dd MMM HH:mm", Locale.getDefault())
            }
            return outputFormat.format(date)
        }

        private fun isSameDay(date1: Date?, date2: Date): Boolean {
            val cal1 = Calendar.getInstance()
            val cal2 = Calendar.getInstance()
            cal1.time = date1
            cal2.time = date2
            return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
        }

        private fun isYesterday(date1: Date?, date2: Date): Boolean {
            val cal1 = Calendar.getInstance()
            val cal2 = Calendar.getInstance()
            cal1.time = date1
            cal2.time = date2
            cal1.add(Calendar.DAY_OF_YEAR, -1)
            return isSameDay(cal1.time, date2)
        }
    }
}
