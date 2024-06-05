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
        ChatMessage("Halo, apakah ada opentrip ke Gunung Semeru minggu depan?", "09:00 AM", MessageType.SENT),
        ChatMessage("Halo, terima kasih telah menghubungi kami. Ya, kami memiliki opentrip ke Gunung Semeru minggu depan.", "09:05 AM", MessageType.RECEIVED),
        ChatMessage("Bagus, berapa biayanya?", "09:10 AM", MessageType.SENT),
        ChatMessage("Biaya untuk opentrip ke Gunung Semeru minggu depan adalah Rp 1.500.000 per orang.", "09:15 AM", MessageType.RECEIVED),
        ChatMessage("Apakah sudah termasuk transportasi dan akomodasi?", "09:20 AM", MessageType.SENT),
        ChatMessage("Ya, biaya tersebut sudah termasuk transportasi pulang pergi, akomodasi, makanan, dan guide.", "09:25 AM", MessageType.RECEIVED),
        ChatMessage("Baik, saya akan ikut. Bagaimana cara pendaftarannya?", "09:30 AM", MessageType.SENT),
        ChatMessage("Anda dapat mendaftar melalui website kami atau menghubungi kami langsung melalui WhatsApp.", "09:35 AM", MessageType.RECEIVED),
        ChatMessage("Oke, terima kasih atas informasinya.", "09:40 AM", MessageType.SENT)
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
