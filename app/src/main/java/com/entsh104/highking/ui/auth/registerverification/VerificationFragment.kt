package com.entsh104.highking.ui.auth.registerverification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentAuthRegisterVerificationBinding
import com.entsh104.highking.ui.util.NavOptionsUtil

class VerificationFragment : Fragment() {

    companion object {
        const val KEY_STATUS_VERIFICATION = "key_nina";
    }

    private var _binding: FragmentAuthRegisterVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthRegisterVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.getString("KEY_STATUS_VERIFICATION") == "reset") {
            binding.verificationTitle.text = getString(R.string.txt_password_label)
            binding.verificationInstruction.text = getString(R.string.txt_password_desc)
        }

        binding.goToLoginButton.setOnClickListener {
            findNavController().navigate(R.id.action_verificationFragment_to_loginFragment,null, NavOptionsUtil.defaultNavOptions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
