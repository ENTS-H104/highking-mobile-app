package com.entsh104.highking.ui.auth.register

import UserRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.entsh104.highking.R
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentAuthRegisterBinding
import com.entsh104.highking.ui.util.NavOptionsUtil
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentAuthRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userRepository = UserRepository(RetrofitClient.instance, SharedPreferencesManager(requireContext()))

        binding.registerButton.setOnClickListener {
            val username = binding.usernameInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()
            val phone_number = binding.phoneInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            val retypePassword = binding.retypePasswordInput.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || phone_number.isEmpty() || password.isEmpty() || retypePassword.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            } else if (password != retypePassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val result = userRepository.registerUser(email, username, phone_number, password)
                    if (result.isSuccess) {
                        findNavController().navigate(R.id.action_registerFragment_to_verificationFragment, null, NavOptionsUtil.defaultNavOptions)
                    } else {
                        Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.loginPrompt.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
