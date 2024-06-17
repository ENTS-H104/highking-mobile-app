package com.entsh104.highking.ui.cust.mountain

import UserRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.entsh104.highking.data.model.MountainResponse
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.data.viewmodel.MountainViewModel
import com.entsh104.highking.databinding.FragmentCustListMountainBinding
import com.entsh104.highking.ui.adapters.MountainAdapter
import com.entsh104.highking.ui.cust.trip.GridSpacingItemDecoration

class ListMountainFragment : Fragment() {

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

        val gridLayoutManager = GridLayoutManager(context, 1)
        binding.recyclerViewMountains.layoutManager = gridLayoutManager
        binding.recyclerViewMountains.addItemDecoration(GridSpacingItemDecoration(2, 1, true))

        arguments?.let {
            val searchResults = ListMountainFragmentArgs.fromBundle(it).mountainList
            displayMountains(searchResults.toList())
        }
    }

    private fun displayMountains(mountains: List<MountainResponse>) {
        val mountainsAdapter = MountainAdapter(mountains, mountainViewModel, false)
        if (mountainsAdapter.itemCount <= 0){
            binding.noMountains.visibility = View.VISIBLE
            binding.recyclerViewMountains.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        }else{
            binding.noMountains.visibility = View.GONE
            binding.recyclerViewMountains.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            binding.recyclerViewMountains.adapter = mountainsAdapter
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
