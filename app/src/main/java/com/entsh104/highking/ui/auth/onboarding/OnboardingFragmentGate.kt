package com.entsh104.highking.ui.auth.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentAuthOnboardingGateBinding
import com.entsh104.highking.ui.util.NavOptionsUtil

class OnboardingFragmentGate : Fragment() {

    private var _binding: FragmentAuthOnboardingGateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthOnboardingGateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragmentGate_to_loginFragment, null, NavOptionsUtil.defaultNavOptions)
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragmentGate_to_registerFragment, null, NavOptionsUtil.defaultNavOptions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
