package com.entsh104.highking.ui.cust.profile

import UserRepository
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
import androidx.appcompat.app.AppCompatActivity
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
import com.entsh104.highking.ui.util.NavOptionsUtil
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID

class ProfileFragment : Fragment() {

    private var _binding: FragmentCustProfileBinding? = null
    private var currentImageUri: Uri? = null

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
                if (responseLogout.isSuccess) {
                    Toast.makeText(requireContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show()
                    prefs.clear()
                    val intent = Intent(activity, AuthActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    Toast.makeText(requireContext(), "Logout failed", Toast.LENGTH_SHORT).show()
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

    private fun startGallery() {
        launcherGallery.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected Uri: $uri")
            lifecycleScope.launch {
//                val response = userRepository

            }
//            launchCropper(uri)
        } else {
            Log.d("PhotoPicker", "No Media")
        }
    }

    private fun launchCropper(uri: Uri) {
//        val destinationFileName = "${UUID.randomUUID()}.png"
//        val destinationDirectory = getExternalFilesDir("ucrop")
//        if (!destinationDirectory!!.exists()) {
//            destinationDirectory.mkdirs()
//        }
//        val destinationFile = File(destinationDirectory, destinationFileName)
//        val destinationUri = Uri.fromFile(destinationFile)
//        UCrop.of(uri, destinationUri)
//            .withAspectRatio(1F, 1F)
//            .withMaxResultSize(500, 500)
//            .start(getContext(),this)
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

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            currentImageUri = UCrop.getOutput(data!!)
//            showImage()
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError: Throwable? = UCrop.getError(data!!)
        }
    }
}
