package com.entsh104.highking.ui.cust.chatroom

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.data.model.MessageRequest
import com.entsh104.highking.data.model.MitraProfile
import com.entsh104.highking.data.model.SendMessageRequest
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentCustChatroomBinding
import com.entsh104.highking.ui.adapters.ChatRoomAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ChatRoomFragment : Fragment() {

    private var _binding: FragmentCustChatroomBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatRoomAdapter: ChatRoomAdapter
    private lateinit var onGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener

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

        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewChatMessages.visibility = View.GONE
        chatRoomAdapter = ChatRoomAdapter(args.userEmail)
        binding.recyclerViewChatMessages.apply {
            layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
            }
            adapter = chatRoomAdapter
        }

        fetchPartnerProfile(args.partnerUid)
        fetchMessagesPeriodically(args.userUid, args.partnerUid)

        binding.progressBar.visibility = View.GONE
        binding.recyclerViewChatMessages.visibility = View.VISIBLE

        binding.editTextMessage.setOnFocusChangeListener { v, hasFocus ->

        }

        binding.buttonSend.setOnClickListener {
            val message = binding.editTextMessage.text.toString()
            if (message.isNotEmpty()) {
                sendMessage(args.userUid, args.partnerUid, message)
            } else {
                showToast("Message cannot be empty")
            }
        }

        onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            if (_binding != null) {
                val rect = Rect()
                binding.root.getWindowVisibleDisplayFrame(rect)
                val screenHeight = binding.root.rootView.height
                val keypadHeight = screenHeight - rect.bottom

                if (keypadHeight > screenHeight * 0.15) {
                    binding.recyclerViewChatMessages.scrollToPosition(chatRoomAdapter.itemCount - 1)
                }
            }
        }

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    private fun fetchPartnerProfile(partnerUid: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(500)
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    try {
                        val response = RetrofitClient.getInstance().getMitraProfile(partnerUid)
                        if (response.isSuccessful) {
                            response.body()?.data?.firstOrNull()?.let { mitraProfile ->
                                // Access the activity's toolbar and update the title
                                (activity as? AppCompatActivity)?.supportActionBar?.title =
                                    mitraProfile.username
                            } ?: showToast("Failed to get partner profile")
                        } else {
                            showToast("Failed to get partner profile")
                        }
                    } catch (e: HttpException) {
                        showToast("Failed to get partner profile")
                    } catch (e: Exception) {
                        showToast("An error occurred")
                    }
                }
            }
        }
    }

    private fun fetchMessagesPeriodically(userUid: String, partnerUid: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    try {
                        val response = RetrofitClient.getInstance()
                            .getMessages(MessageRequest(userUid, partnerUid))
                        if (response.isSuccessful) {
                            response.body()?.let {
                                chatRoomAdapter.updateMessages(it.data)
                                binding.recyclerViewChatMessages.scrollToPosition(chatRoomAdapter.itemCount - 1)
                            } ?: showToast("No data available")
                        } else {
                            showToast("Failed to fetch messages")
                        }
                    } catch (e: HttpException) {
                        showToast("Failed to fetch messages")
                    } catch (e: Exception) {
                        showToast("An error occurred")
                    }
                    delay(4000)
                }
            }
        }
    }

    private fun sendMessage(userUid: String, partnerUid: String, message: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(500)
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    try {
                        val response = RetrofitClient.getInstance().sendMessage(
                            SendMessageRequest(
                                userUid,
                                partnerUid,
                                message,
                                args.userEmail
                            )
                        )
                        if (response.isSuccessful) {
                            fetchMessagesPeriodically(userUid, partnerUid)
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
        }
    }

    private fun showToast(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (_binding != null) {
            binding.root.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
        }
        _binding = null
    }
}

