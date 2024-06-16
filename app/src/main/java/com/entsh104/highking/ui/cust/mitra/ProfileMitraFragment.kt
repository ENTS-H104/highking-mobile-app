package com.entsh104.highking.ui.cust.mitra

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
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.entsh104.highking.R
import com.entsh104.highking.data.model.MitraProfileResponse
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentCustMitraProfileBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileMitraFragment : Fragment() {

    private var _binding: FragmentCustMitraProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository
    private val args: ProfileMitraFragmentArgs by navArgs()
    private lateinit var mitraId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustMitraProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mitraId = args.mitraId ?: ""
        val prefs = SharedPreferencesManager(requireContext())
        userRepository = UserRepository(RetrofitClient.getInstance(), prefs)

        val viewPager: ViewPager = binding.viewPager
        val tabLayout: TabLayout = binding.tabLayout

        val adapter = MitraProfilePagerAdapter(childFragmentManager, mitraId)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        binding.buttonLihatUlasan.setOnClickListener {
            findNavController().navigate(R.id.action_profileMitraFragment_to_fragmentCustReview)
        }

        fetchMitraProfile()
    }

    private fun fetchMitraProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(500)
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    try {
                        val response = userRepository.getMitraProfile(mitraId)
                        if (response.isSuccess) {
                            val mitraProfileResponse = response.getOrNull()
                            mitraProfileResponse?.data?.firstOrNull()?.let { mitraProfile ->
                                binding.textViewUsername.text = mitraProfile.username
                                Glide.with(this@ProfileMitraFragment).load(mitraProfile.image_url)
                                    .into(binding.imageViewProfile)
                            } ?: run {
                                Toast.makeText(
                                    requireContext(),
                                    "Mitra profile not found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Failed to load Mitra profile",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                            .show()
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
