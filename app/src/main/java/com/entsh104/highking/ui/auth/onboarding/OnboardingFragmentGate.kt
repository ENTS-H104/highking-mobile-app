package com.entsh104.highking.ui.auth.onboarding

import com.entsh104.highking.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class OnboardingFragmentGate : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth_onboarding_gate, container, false)

        val logoImageView: ImageView = view.findViewById(R.id.logo)
        val illustrationImageView: ImageView = view.findViewById(R.id.illustration)
        val titleTextView: TextView = view.findViewById(R.id.onboarding_title)
        val descriptionTextView: TextView = view.findViewById(R.id.onboarding_description)

        val buttonContainer: LinearLayout = view.findViewById(R.id.button_container)
        val loginButton: Button = buttonContainer.findViewById(R.id.login_button)
        val registerButton: Button = buttonContainer.findViewById(R.id.register_button)

        loginButton.setOnClickListener {
            // Navigasi ke halaman login atau lakukan tindakan lain
        }

        registerButton.setOnClickListener {
            // Navigasi ke halaman registrasi atau lakukan tindakan lain
        }

        return view
    }
}
