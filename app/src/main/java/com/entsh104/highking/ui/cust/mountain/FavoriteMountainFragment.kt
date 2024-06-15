// ui/cust/mountain/FavoriteMountainFragment.kt
package com.entsh104.highking.ui.cust.mountain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.data.mapper.MountainMapper
import com.entsh104.highking.data.viewmodel.MountainViewModel
import com.entsh104.highking.databinding.FragmentCustFavMountainBinding
import com.entsh104.highking.ui.adapters.MountainAdapter

class FavoriteMountainFragment : Fragment() {

    private var _binding: FragmentCustFavMountainBinding? = null
    private val binding get() = _binding!!
    private val mountainViewModel: MountainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustFavMountainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MountainAdapter(emptyList(), mountainViewModel, isSimpleLayout = false)
        binding.recyclerViewFavoriteMountains.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFavoriteMountains.adapter = adapter

        mountainViewModel.favoriteMountains.observe(viewLifecycleOwner) { mountains ->
            val mountainResponses = mountains.map { MountainMapper.mapEntityToResponse(it) }
            adapter.mountains = mountainResponses
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
