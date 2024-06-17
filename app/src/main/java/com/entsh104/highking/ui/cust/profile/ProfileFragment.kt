package com.entsh104.highking.ui.cust.profile

import UserRepository
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.entsh104.highking.R
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentCustProfileBinding
import com.entsh104.highking.ui.auth.AuthActivity
import kotlinx.coroutines.delay
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
        RetrofitClient.createInstance(requireContext())
        userRepository = UserRepository(RetrofitClient.getInstance(), prefs)

        binding.llheaderInfoAkun.setOnClickListener {
            toggleSection(binding.contentInfoAkun, binding.arrowInfoAkun)
        }

        binding.btnLogout.setOnClickListener {
            val token = prefs.getToken().toString()
            viewLifecycleOwner.lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    delay(500)
                    if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                        val responseLogout = userRepository.logoutUser(token)
                        if (responseLogout.isSuccess) {
                            Toast.makeText(requireContext(), "Logout Berhasil", Toast.LENGTH_SHORT)
                                .show()
                            prefs.clear()
                            val intent = Intent(activity, AuthActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        } else {
                            Toast.makeText(requireContext(), "Logout failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }

        binding.buttonEditPhoto.setOnClickListener {
            startGallery()
        }

        binding.buttonEditProfile.setOnClickListener {
            val currentUsername = binding.textViewName.text.toString()
            val currentPhone = binding.textViewPhone.text.toString()
            val bundle = Bundle().apply {
                putString("KEY_USERNAME", currentUsername)
                putString("KEY_PHONE", currentPhone)
            }
            findNavController().navigate(R.id.action_nav_profile_to_fragmentCustProfileEdit, bundle)
        }

        binding.headerSettings.setOnClickListener{
            toggleSection(binding.contentSettings, binding.arrowSettings)
        }

        binding.buttonFavoriteItem.setOnClickListener{
            findNavController().navigate(R.id.action_nav_profile_to_favoritesFragment)
        }

        fetchUserProfile()
    }

    private fun showConfirmationDialog(imageUri: Uri) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirm Photo")
        builder.setMessage("Are you sure you want to upload this photo?")
        builder.setPositiveButton("Yes") { dialog, which ->
            viewLifecycleOwner.lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    delay(500)
                    if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                        val response = userRepository.uploadPhoto(
                            requireContext().contentResolver,
                            requireContext().cacheDir,
                            imageUri
                        )
                        if (response.isSuccess) {
                            Toast.makeText(
                                requireContext(),
                                "Photo uploaded successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            fetchUserProfile()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Failed to upload photo",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }


    private val launcherGallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        uri?.let {
            showConfirmationDialog(it)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }


    private fun fetchUserProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(500)
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.scrollViewContent.visibility = View.GONE

                    val result = userRepository.getCurrentUser()
                    if (result.isSuccess) {
                        val user = result.getOrNull()
                        user?.let {
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
                        Toast.makeText(
                            requireContext(),
                            "Failed to load profile",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
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
