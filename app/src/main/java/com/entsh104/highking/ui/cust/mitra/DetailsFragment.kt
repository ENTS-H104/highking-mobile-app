package com.entsh104.highking.ui.cust.details

import UserRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.entsh104.highking.data.helper.ViewModelFactory
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.data.viewmodel.FavoritesViewModel
import com.entsh104.highking.databinding.FragmentCustMitraProfileDetailsBinding
import com.entsh104.highking.ui.adapters.TripsAdapter
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private var _binding: FragmentCustMitraProfileDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mitraId: String
    private lateinit var userRepository: UserRepository


    companion object {
        private const val ARG_MITRA_ID = "mitra_id"

        fun newInstance(mitraId: String): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putString(ARG_MITRA_ID, mitraId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mitraId = it.getString(ARG_MITRA_ID) ?: ""
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustMitraProfileDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = SharedPreferencesManager(requireContext())
        RetrofitClient.createInstance(requireContext())
        userRepository = UserRepository(RetrofitClient.getInstance(), prefs)

//        setupRecyclerView()
        fetchTrips()
    }
    private fun fetchTrips() {
        // Show ProgressBar
//        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            val result = userRepository.getMitraProfile(mitraId)

            if (result.isSuccess) {
                val mitraProfileResponse = result.getOrNull()
                mitraProfileResponse?.data?.firstOrNull()?.let { mitraProfile ->
                    binding.textViewJoinDate.text = mitraProfile.updated_at
                    binding.textViewDescription.text = mitraProfile.domicile_address
                } ?: run {
                    Toast.makeText(requireContext(), "Mitra profile not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Failed to load mitra", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtainViewModel(activity: FragmentActivity): FavoritesViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoritesViewModel::class.java]
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
