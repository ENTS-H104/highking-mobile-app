package com.entsh104.highking.ui.cust.mountain

import UserRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.data.viewmodel.MountainViewModel
import com.entsh104.highking.databinding.FragmentCustListMountainBinding
import com.entsh104.highking.ui.adapters.MountainAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListAllMountainFragment : Fragment() {

    private var _binding: FragmentCustListMountainBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository
    private val mountainViewModel: MountainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustListMountainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewMountains.visibility = View.GONE
        val prefs = SharedPreferencesManager(requireContext())
        RetrofitClient.createInstance(requireContext())
        userRepository = UserRepository(RetrofitClient.getInstance(), prefs)

        binding.recyclerViewMountains.layoutManager = LinearLayoutManager(context)

        fetchMountains()
    }

    private fun fetchMountains() {
        binding.progressBar.visibility = View.VISIBLE

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(500)
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    val result = userRepository.getMountains()
                    if (result.isSuccess) {
                        val mountains = result.getOrNull() ?: emptyList()
                        val mountainsAdapter = MountainAdapter(mountains, mountainViewModel, false)

                        if (mountainsAdapter.itemCount <= 0){
                            binding.noMountains.visibility = View.VISIBLE
                            binding.recyclerViewMountains.visibility = View.GONE
                            binding.progressBar.visibility = View.GONE
                        } else{
                            binding.noMountains.visibility = View.GONE
                            binding.recyclerViewMountains.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerViewMountains.adapter = mountainsAdapter
                        }

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to load mountains",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.noMountains.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
