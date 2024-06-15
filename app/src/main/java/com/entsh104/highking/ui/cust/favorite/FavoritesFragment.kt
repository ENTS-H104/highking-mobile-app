package com.entsh104.highking.ui.cust.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.entsh104.highking.databinding.FragmentCustFavoriteBinding
import com.google.android.material.tabs.TabLayoutMediator

class FavoritesFragment : Fragment() {

    private var _binding: FragmentCustFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoritesPagerAdapter = FavoritesPagerAdapter(requireActivity())
        binding.viewPager.adapter = favoritesPagerAdapter

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Mountains"
                else -> "Trips"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
