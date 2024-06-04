package com.entsh104.highking.ui.auth.registerverification
import com.entsh104.highking.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class VerificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth_register_verification, container, false)

        val codeDigit1: TextInputEditText = view.findViewById(R.id.code_digit_1)
        val codeDigit2: TextInputEditText = view.findViewById(R.id.code_digit_2)
        val codeDigit3: TextInputEditText = view.findViewById(R.id.code_digit_3)
        val codeDigit4: TextInputEditText = view.findViewById(R.id.code_digit_4)
        val verificationButton: Button = view.findViewById(R.id.verification_button)
        val resendButton: TextView = view.findViewById(R.id.verification_resend)

        verificationButton.setOnClickListener {
            val verificationCode = codeDigit1.text.toString() +
                    codeDigit2.text.toString() +
                    codeDigit3.text.toString() +
                    codeDigit4.text.toString()

            // Validasi input
            if (verificationCode.length != 4) {
                Toast.makeText(requireContext(), "Kode verifikasi harus 4 digit", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: Implementasi logika verifikasi (misalnya, panggil API)
            // Contoh:
            // viewModel.verifyCode(verificationCode)

            // Jika verifikasi berhasil, arahkan ke halaman berikutnya (misalnya halaman utama)
//            findNavController().navigate(R.id.action_verificationFragment_to_mainActivity)
        }

        // Set onClickListener untuk TextView "Kirim Ulang"
        resendButton.setOnClickListener {
            // TODO: Implementasi logika kirim ulang kode verifikasi (misalnya, panggil API)
            Toast.makeText(requireContext(), "Kode verifikasi dikirim ulang", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
