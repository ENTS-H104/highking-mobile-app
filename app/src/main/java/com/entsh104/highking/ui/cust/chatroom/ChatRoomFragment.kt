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
        ChatMessage("Halo, apakah ada opentrip ke Gunung Semeru minggu depan?", "09:00 AM", true),
        ChatMessage("Halo, terima kasih telah menghubungi kami. Ya, kami memiliki opentrip ke Gunung Semeru minggu depan.", "09:05 AM", false),
        ChatMessage("Bagus, berapa biayanya?", "09:10 AM", true),
        ChatMessage("Biaya untuk opentrip ke Gunung Semeru minggu depan adalah Rp 1.500.000 per orang.", "09:15 AM", false),
        ChatMessage("Apakah sudah termasuk transportasi dan akomodasi?", "09:20 AM", true),
        ChatMessage("Ya, biaya tersebut sudah termasuk transportasi pulang pergi, akomodasi, makanan, dan guide.", "09:25 AM", false),
        ChatMessage("Baik, saya akan ikut. Bagaimana cara pendaftarannya?", "09:30 AM", true),
        ChatMessage("Anda dapat mendaftar melalui website kami atau menghubungi kami langsung melalui WhatsApp.", "09:35 AM", false),
        ChatMessage("Oke, terima kasih atas informasinya.", "09:40 AM", true)
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
