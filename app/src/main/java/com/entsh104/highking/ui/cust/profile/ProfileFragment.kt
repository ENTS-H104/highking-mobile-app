package com.entsh104.highking.ui.cust.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentCustProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentCustProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llheaderInfoAkun.setOnClickListener {
            toggleSection(binding.contentInfoAkun, binding.arrowInfoAkun)
        }

        binding.headerSettings.setOnClickListener {
            toggleSection(binding.contentSettings, binding.arrowSettings)
        }
    }

    private fun toggleSection(contentLayout: View, arrowView: ImageView) {
        if (contentLayout.visibility == View.GONE) {
            contentLayout.visibility = View.VISIBLE
            arrowView.setImageResource(R.drawable.ic_arrow_up)
        } else {
            contentLayout.visibility = View.GONE
            arrowView.setImageResource(R.drawable.ic_arrow_down)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
