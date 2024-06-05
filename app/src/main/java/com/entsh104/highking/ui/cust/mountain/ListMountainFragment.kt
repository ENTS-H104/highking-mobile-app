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
import com.entsh104.highking.ui.model.Trip

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

        // Setup RecyclerView
        binding.recyclerViewMountains.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewMountains.adapter = MountainAdapter(getMountainsData(), false)
    }

    private fun getMountainsData(): List<Mountain> {
        return listOf(
            Mountain(R.drawable.iv_mountain, "Bromo", 2329, "Probolinggo, Jawa Timur", 9, false, "Bromo adalah gunung berapi yang masih aktif dan paling terkenal di Indonesia.", "Cerah", "25", "Rp 50.000"),
            Mountain(R.drawable.iv_mountain, "Bromo", 2329, "Probolinggo, Jawa Timur", 9, false, "Bromo adalah gunung berapi yang masih aktif dan paling terkenal di Indonesia.", "Cerah", "25", "Rp 50.000"),
            Mountain(R.drawable.iv_mountain, "Bromo", 2329, "Probolinggo, Jawa Timur", 9, false, "Bromo adalah gunung berapi yang masih aktif dan paling terkenal di Indonesia.", "Cerah", "25", "Rp 50.000"),
            Mountain(R.drawable.iv_mountain, "Bromo", 2329, "Probolinggo, Jawa Timur", 9, false, "Bromo adalah gunung berapi yang masih aktif dan paling terkenal di Indonesia.", "Cerah", "25", "Rp 50.000"),
            Mountain(R.drawable.iv_mountain, "Bromo", 2329, "Probolinggo, Jawa Timur", 9, false, "Bromo adalah gunung berapi yang masih aktif dan paling terkenal di Indonesia.", "Cerah", "25", "Rp 50.000"),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
