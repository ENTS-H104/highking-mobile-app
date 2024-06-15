package com.entsh104.highking.ui.cust.profile

import UserRepository
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.entsh104.highking.R
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentCustProfileBinding
import com.entsh104.highking.ui.auth.AuthActivity
import com.entsh104.highking.ui.cust.CustActivity
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentCustProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = SharedPreferencesManager(requireContext())
        RetrofitClient.createInstance(requireContext())  // Suspicious indentation: This is indented but is not continuing the previous expression (val prefs = SharedPr...) (Previous statement here
        userRepository = UserRepository(RetrofitClient.getInstance(), prefs)

        binding.llheaderInfoAkun.setOnClickListener {
            toggleSection(binding.contentInfoAkun, binding.arrowInfoAkun)
        }

        binding.btnLogout.setOnClickListener {
            val token = prefs.getToken().toString()
            lifecycleScope.launch {
                val responseLogout = userRepository.logoutUser(token)
                if(responseLogout.isSuccess){
                    Toast.makeText(requireContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show()
                    prefs.clear()
                    val intent = Intent(activity, AuthActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }else{
                    Toast.makeText(requireContext(), "Logout failed", Toast.LENGTH_SHORT).show()
                }

            }
        }

        binding.buttonEditProfile.setOnClickListener{
            findNavController().navigate(R.id.action_nav_profile_to_fragmentCustProfileEdit)
        }

        binding.headerSettings.setOnClickListener {
            toggleSection(binding.contentSettings, binding.arrowSettings)
        }

        binding.buttonFavoriteItem.setOnClickListener{
            findNavController().navigate(R.id.action_nav_profile_to_favoritesFragment)
        }

        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            binding.scrollViewContent.visibility = View.GONE

            val result = userRepository.getCurrentUser()
            if (result.isSuccess) {
                val user = result.getOrNull()
                user?.let {
                    Log.d("ProfileFragment", "User: $it")
                    binding.textViewUsername.text = "@" + it.username
                    binding.textViewName.text = it.username
                    binding.textViewEmail.text = it.email
                    binding.textViewPhone.text = it.phone_number
                    Glide.with(this@ProfileFragment).load("${it.image_url}")
                        .into(binding.imageViewProfile)
                    binding.progressBar.visibility = View.GONE
                    binding.scrollViewContent.visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(requireContext(), "Failed to load profile", Toast.LENGTH_SHORT)
                    .show()
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun toggleSection(contentLayout: View, arrowView: ImageView) {
        if (contentLayout.visibility == View.GONE) {
            contentLayout.visibility = View.VISIBLE
            arrowView.setImageResource(R.drawable.ic_arrow_up)
        } else {
            contentLayout.visibility = View.GONE
            arrowView.setImageResource(R.drawable.ic_arrow_down)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
