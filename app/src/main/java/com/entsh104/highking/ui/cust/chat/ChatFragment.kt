package com.entsh104.highking.ui.cust.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.databinding.FragmentCustChatBinding

class ChatFragment : Fragment() {

    private var _binding: FragmentCustChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(context)
        // Set up your adapter for RecyclerView here
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
