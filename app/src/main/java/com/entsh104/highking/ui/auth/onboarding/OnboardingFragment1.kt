package com.entsh104.highking.ui.auth.onboarding
import com.entsh104.highking.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class OnboardingFragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth_onboarding_1, container, false)

        val logoImageView: ImageView = view.findViewById(R.id.logo)
        val illustrationImageView: ImageView = view.findViewById(R.id.illustration)
        val titleTextView: TextView = view.findViewById(R.id.onboarding_title)
        val descriptionTextView: TextView = view.findViewById(R.id.onboarding_description)
        val nextButton: Button = view.findViewById(R.id.next_button)

        nextButton.setOnClickListener {

        }

        return view
    }
}
