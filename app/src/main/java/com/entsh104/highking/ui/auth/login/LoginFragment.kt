package com.entsh104.highking.ui.auth.login
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

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth_login, container, false)

        val usernameInput: TextInputEditText = view.findViewById(R.id.username_input)
        val passwordInput: TextInputEditText = view.findViewById(R.id.password_input)
        val loginButton: Button = view.findViewById(R.id.login_button)
        val forgotPasswordTextView: TextView = view.findViewById(R.id.forgot_password)
        val registerPromptTextView: TextView = view.findViewById(R.id.register_prompt)

        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Username dan password harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: Implementasi logika login (misalnya, panggil API atau cek database)
            // Jika login berhasil, arahkan ke halaman utama
            // Jika login gagal, tampilkan pesan error
        }

        forgotPasswordTextView.setOnClickListener {
            // TODO: Implementasi navigasi ke halaman "Forgot Password"
//            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        registerPromptTextView.setOnClickListener {
//            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return view
    }
}
