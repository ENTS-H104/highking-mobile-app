package com.entsh104.highking.ui.cust.chatroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.databinding.FragmentCustChatroomBinding

class ChatRoomFragment : Fragment() {

    private var _binding: FragmentCustChatroomBinding? = null
    private val binding get() = _binding!!

    private val chatMessages = listOf(
        ChatMessage("Sam, are you ready? 😂😂", "15:18 PM", MessageType.RECEIVED),
        ChatMessage("Actually yes, lemme see..", "15:19 PM", MessageType.SENT),
        ChatMessage("Done, I just finished it! 😆🙌", "15:19 PM", MessageType.SENT),
        ChatMessage("Nah, it's crazy 😅", "15:20 PM", MessageType.RECEIVED),
        ChatMessage("Cheating?", "15:20 PM", MessageType.RECEIVED),
        ChatMessage("No way, lol", "15:20 PM", MessageType.SENT),
        ChatMessage("I'm a pro, that's why 😎", "15:20 PM", MessageType.SENT),
        ChatMessage("Still, can't believe 😆", "15:21 PM", MessageType.RECEIVED),
        ChatMessage("Read about inflation news, now!!", "15:22 PM", MessageType.SENT)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustChatroomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.buttonSend.setOnClickListener {
            // Handle send button click
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewChatMessages.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewChatMessages.adapter = ChatRoomAdapter(chatMessages)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class ChatMessage(val message: String, val timestamp: String, val type: MessageType)

enum class MessageType {
    SENT, RECEIVED
}
