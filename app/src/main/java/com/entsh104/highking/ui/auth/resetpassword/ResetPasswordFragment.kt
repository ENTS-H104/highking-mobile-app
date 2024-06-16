package com.entsh104.highking.ui.auth.resetpassword

import UserRepository
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.entsh104.highking.R
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentResetPasswordBinding
import com.entsh104.highking.ui.util.NavOptionsUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ResetPasswordFragment : Fragment() {

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = com.entsh104.highking.databinding.FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RetrofitClient.createInstance(requireContext())
        userRepository = UserRepository(RetrofitClient.getInstance(), SharedPreferencesManager(requireContext()))

        binding.resetButton.setOnClickListener {
            val email = binding.fpEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Email are required", Toast.LENGTH_SHORT).show()
            } else {
                viewLifecycleOwner.lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        delay(500)
                        if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                            val result = userRepository.resetPasswordUser(email)
                            if (result.isSuccess) {
                                val bundle = Bundle().apply {
                                    putString("KEY_STATUS_VERIFICATION", "reset")
                                }
                                findNavController().navigate(
                                    R.id.action_resetPasswordFragment_to_verificationFragment,
                                    bundle,
                                    NavOptionsUtil.defaultNavOptions
                                )
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Registration failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
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