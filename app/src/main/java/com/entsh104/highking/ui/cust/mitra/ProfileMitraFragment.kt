package com.entsh104.highking.ui.cust.mitra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentCustMitraProfileBinding
import com.google.android.material.tabs.TabLayout

class ProfileMitraFragment : Fragment() {

    private var _binding: FragmentCustMitraProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustMitraProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager: ViewPager = binding.viewPager
        val tabLayout: TabLayout = binding.tabLayout

        val adapter = MitraProfilePagerAdapter(childFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        binding.buttonLihatUlasan.setOnClickListener {
            findNavController().navigate(R.id.action_profileMitraFragment_to_fragmentCustReview)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
