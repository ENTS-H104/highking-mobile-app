package com.entsh104.highking.ui.cust.mountain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentCustListMountainBinding
import com.entsh104.highking.ui.adapters.MountainAdapter
import com.entsh104.highking.ui.model.Mountain

class ListMountainFragment : Fragment() {

    private var _binding: FragmentCustListMountainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustListMountainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        binding.recyclerViewMountains.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewMountains.adapter = MountainAdapter(getMountainsData(), false)
    }

    private fun getMountainsData(): List<Mountain> {
        return listOf(
            Mountain(R.drawable.iv_mountain, "Bromo", 2329, "Probolinggo, Jawa Timur", 9, false),
            Mountain(R.drawable.iv_mountain, "Semeru", 3676, "Lumajang, Jawa Timur", 5, true),
            Mountain(R.drawable.iv_mountain, "Merapi", 2355, "Boyolali, Jawa Tengah", 10, true),
            Mountain(R.drawable.iv_mountain, "Rinjani", 3726, "Lombok, NTB", 7, false)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
