package com.entsh104.highking.ui.cust.profile

import UserRepository
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.entsh104.highking.R
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentCustProfileEditBinding
import com.entsh104.highking.ui.util.NavOptionsUtil
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID

class FragmentCustProfileEdit : Fragment() {

    private var _binding: FragmentCustProfileEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustProfileEditBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_cust_profile_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = SharedPreferencesManager(requireContext())
        RetrofitClient.createInstance(requireContext())  // Suspicious indentation: This is indented but is not continuing the previous expression (val prefs = SharedPr...) (Previous statement here
        userRepository = UserRepository(RetrofitClient.getInstance(), prefs)


        val phone2 = arguments?.getString("KEY_PHONE")
        val username2 = arguments?.getString("KEY_USERNAME")

        binding.inputEditUsername.setText(username2)
        binding.inputEditPhone.setText(phone2)

        binding.btnSaveEdit.setOnClickListener {
            val username = binding.inputEditUsername.text.toString().trim()
            val phone = binding.inputEditPhone.text.toString().trim()

            if (username.isEmpty() || phone.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewLifecycleOwner.lifecycleScope.launch {
                    val response = userRepository.editProfileUser(username, phone)
                    Toast.makeText(requireContext(), "Update Profile Berhasil", Toast.LENGTH_SHORT)
                        .show()

                    findNavController().navigate(R.id.action_fragmentCustProfileEdit_to_nav_profile)
                }
            }
        }

    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_USERNAME = "username"
        const val KEY_PHONE = "phone"
    }

}