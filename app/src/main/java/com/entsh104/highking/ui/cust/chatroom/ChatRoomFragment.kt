package com.entsh104.highking.ui.cust.chatroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.navArgs
import com.entsh104.highking.data.model.MessageRequest
import com.entsh104.highking.data.model.SendMessageRequest
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentCustChatroomBinding
import com.entsh104.highking.ui.adapters.ChatRoomAdapter
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ChatRoomFragment : Fragment() {

    private var _binding: FragmentCustChatroomBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatRoomAdapter: ChatRoomAdapter

    private val args: ChatRoomFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustChatroomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatRoomAdapter = ChatRoomAdapter(args.userEmail)
        binding.recyclerViewChatMessages.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatRoomAdapter
        }

        fetchMessages(args.userUid, args.partnerUid)

        binding.buttonSend.setOnClickListener {
            val message = binding.editTextMessage.text.toString()
            if (message.isNotEmpty()) {
                sendMessage(args.userUid, args.partnerUid, message)
            } else {
                showToast("Message cannot be empty")
            }
        }
    }

    private fun fetchMessages(userUid: String, partnerUid: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.getInstance().getMessages(MessageRequest(userUid, partnerUid))
                if (response.isSuccessful) {
                    response.body()?.let {
                        chatRoomAdapter.updateMessages(it.data)
                    } ?: showToast("No data available")
                } else {
                    showToast("Failed to fetch messages")
                }
            } catch (e: HttpException) {
                showToast("Failed to fetch messages")
            } catch (e: Exception) {
                showToast("An error occurred")
            }
        }
    }

    private fun sendMessage(userUid: String, partnerUid: String, message: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.getInstance().sendMessage(SendMessageRequest(userUid, partnerUid, message, args.userEmail))
                if (response.isSuccessful) {
                    fetchMessages(userUid, partnerUid)
                    binding.editTextMessage.text.clear()
                } else {
                    showToast("Failed to send message")
                }
            } catch (e: HttpException) {
                showToast("Failed to send message")
            } catch (e: Exception) {
                showToast("An error occurred")
            }
        }
    }

    private fun showToast(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
