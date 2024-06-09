package com.entsh104.highking.ui.cust.mitra

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.entsh104.highking.ui.cust.trip.TripFragment
import com.entsh104.highking.ui.cust.details.DetailsFragment

class MitraProfilePagerAdapter(fm: FragmentManager, private val mitraId: String) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TripFragment.newInstance(mitraId)
            else -> DetailsFragment.newInstance(mitraId)
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Trip"
            else -> "Details"
        }
    }
}
