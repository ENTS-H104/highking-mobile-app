package com.entsh104.highking.ui.cust.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.databinding.FragmentCustChatBinding
import androidx.navigation.fragment.findNavController
import com.entsh104.highking.ui.util.NavOptionsUtil

class ChatFragment : Fragment() {

    private var _binding: FragmentCustChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatAdapter = ChatAdapter(getSampleChats(), object : OnChatItemClickListener {
            override fun onChatItemClick(chat: Chat) {
                val action = ChatFragmentDirections.actionNavChatToChatRoomFragment()
                findNavController().navigate(action, NavOptionsUtil.defaultNavOptions)
            }
        })
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewChat.adapter = chatAdapter
    }

    private fun getSampleChats(): List<Chat> {
        return listOf(
            Chat("Mitra Kencana", "Mitra Kencana is typing...", "14:28", 1),
            Chat("Mitra Kencana", "Mitra Kencana is typing...", "14:28", 1),
            Chat("Mitra Kencana", "Mitra Kencana is typing...", "14:28", 1),
            Chat("Mitra Kencana", "Mitra Kencana is typing...", "14:28", 1),
            Chat("Mitra Kencana", "Mitra Kencana is typing...", "14:28", 1)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
