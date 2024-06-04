package com.entsh104.highking.ui.auth.registerverification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.entsh104.highking.databinding.FragmentAuthRegisterVerificationBinding
import com.entsh104.highking.ui.cust.CustActivity

class VerificationFragment : Fragment() {

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

        binding.verificationButton.setOnClickListener {
            val code1 = binding.codeDigit1.text.toString().trim()
            val code2 = binding.codeDigit2.text.toString().trim()
            val code3 = binding.codeDigit3.text.toString().trim()
            val code4 = binding.codeDigit4.text.toString().trim()

            if (code1.isEmpty() || code2.isEmpty() || code3.isEmpty() || code4.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(activity, CustActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
