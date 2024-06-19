package com.entsh104.highking.ui.cust.chat

import UserRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.data.model.MitraProfile
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentCustChatBinding
import com.entsh104.highking.ui.adapters.ChatListAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ChatFragment : Fragment() {

    private var _binding: FragmentCustChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatAdapter: ChatListAdapter
    private lateinit var userRepository: UserRepository
    private lateinit var prefs: SharedPreferencesManager

    private var userUid: String? = null
    private var userEmail: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustChatBinding.inflate(inflater, container, false)
        prefs = SharedPreferencesManager(requireContext())
        userRepository = UserRepository(RetrofitClient.getInstance(), prefs)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewChat.visibility = View.GONE
        chatAdapter = ChatListAdapter(
            onChatItemClickListener = { partnerUid ->
                val action = ChatFragmentDirections.actionNavChatToChatRoom(partnerUid, userUid!!, userEmail!!)
                findNavController().navigate(action)
            },
            fetchMitraProfile = { partnerUid -> fetchMitraProfile(partnerUid) }
        )
        binding.recyclerViewChat.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatAdapter
        }

        fetchCurrentUserAndChatList()
    }

    private fun fetchCurrentUserAndChatList() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(500)
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    val result = userRepository.getCurrentUser()
                    result.onSuccess { user ->
                        userUid = user.user_uid
                        userEmail = user.email
                        fetchChatList(user.user_uid!!)

                        binding.progressBar.visibility = View.GONE
                        binding.recyclerViewChat.visibility = View.VISIBLE
                    }.onFailure {
                        showToast("Failed to get current user")
                    }
                }
            }
        }
    }

    private fun fetchChatList(userUid: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(500)
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    try {
                        val response = RetrofitClient.getInstance().getAllMessages(userUid)
                        if (response.isSuccessful) {
                            response.body()?.let {
                                chatAdapter.updateChatList(it.data)
                            } ?: showToast("No data available")
                        } else {
                            showToast("Failed to fetch chat list")
                        }
                    } catch (e: HttpException) {
                        showToast("Failed to fetch chat list")
                    } catch (e: Exception) {
                        showToast("An error occurred")
                    }
                }
            }
        }
    }

    private suspend fun fetchMitraProfile(partnerUid: String): MitraProfile? {
        return try {
            val response = RetrofitClient.getInstance().getMitraProfile(partnerUid)
            if (response.isSuccessful) {
                response.body()?.data?.firstOrNull()
            } else {
                showToast("Failed to fetch Mitra profile")
                null
            }
        } catch (e: HttpException) {
            showToast("Failed to fetch Mitra profile")
            null
        } catch (e: Exception) {
            showToast("An error occurred")
            null
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
