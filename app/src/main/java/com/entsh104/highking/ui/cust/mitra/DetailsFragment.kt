package com.entsh104.highking.ui.cust.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.entsh104.highking.databinding.FragmentCustMitraProfileDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentCustMitraProfileDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mitraId: String

    companion object {
        private const val ARG_MITRA_ID = "mitra_id"

        fun newInstance(mitraId: String): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putString(ARG_MITRA_ID, mitraId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mitraId = it.getString(ARG_MITRA_ID) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustMitraProfileDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
