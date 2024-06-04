package com.entsh104.highking.ui.auth.register
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

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth_register, container, false)

        // Inisialisasi Views
        val usernameInput: TextInputEditText = view.findViewById(R.id.username_input)
        val emailInput: TextInputEditText = view.findViewById(R.id.email_input)
        val phoneInput: TextInputEditText = view.findViewById(R.id.phone_input)
        val passwordInput: TextInputEditText = view.findViewById(R.id.password_input)
        val retypePasswordInput: TextInputEditText = view.findViewById(R.id.retype_password_input)
        val registerButton: Button = view.findViewById(R.id.register_button)
        val loginPromptTextView: TextView = view.findViewById(R.id.login_prompt)

        // Set onClickListener untuk button Register
        registerButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val email = emailInput.text.toString()
            val phone = phoneInput.text.toString()
            val password = passwordInput.text.toString()
            val retypePassword = retypePasswordInput.text.toString()

            // Validasi input
            if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || retypePassword.isEmpty()) {
                Toast.makeText(requireContext(), "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != retypePassword) {
                Toast.makeText(requireContext(), "Password tidak cocok", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: Implementasi logika registrasi (misalnya, panggil API)
            // Contoh:
            // viewModel.registerUser(username, email, phone, password)
            // ...

            // Jika registrasi berhasil, arahkan ke halaman verifikasi
//            findNavController().navigate(R.id.action_registerFragment_to_verificationFragment)
        }

        // Set onClickListener untuk TextView "Already registered? Login"
        loginPromptTextView.setOnClickListener {
//            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return view
    }
}
