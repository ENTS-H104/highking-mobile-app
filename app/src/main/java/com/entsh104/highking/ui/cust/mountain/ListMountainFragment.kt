package com.entsh104.highking.ui.cust.mountain

import UserRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.R
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentCustListMountainBinding
import com.entsh104.highking.ui.adapters.MountainAdapter
import kotlinx.coroutines.launch

class ListMountainFragment : Fragment() {

    private var _binding: FragmentCustListMountainBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustListMountainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = SharedPreferencesManager(requireContext())
            RetrofitClient.createInstance(requireContext()) 
    userRepository = UserRepository(RetrofitClient.getInstance(), prefs)

        binding.recyclerViewMountains.layoutManager = LinearLayoutManager(context)

        fetchMountains()
    }

    private fun fetchMountains() {
        // Show ProgressBar
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            val result = userRepository.getMountains()
            if (result.isSuccess) {
                val mountains = result.getOrNull() ?: emptyList()
                val mountainsAdapter = MountainAdapter(mountains, false)
                binding.recyclerViewMountains.adapter = mountainsAdapter
            } else {
                Toast.makeText(requireContext(), "Failed to load mountains", Toast.LENGTH_SHORT).show()
            }

            // Hide ProgressBar
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
