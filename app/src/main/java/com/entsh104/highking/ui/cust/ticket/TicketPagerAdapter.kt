package com.entsh104.highking.ui.cust.ticket

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TicketPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WaitingFragment()
            1 -> ActiveFragment()
            2 -> CompletedFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
