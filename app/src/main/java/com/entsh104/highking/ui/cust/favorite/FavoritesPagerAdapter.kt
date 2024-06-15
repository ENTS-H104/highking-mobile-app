package com.entsh104.highking.ui.cust.favorites

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.entsh104.highking.ui.cust.mountain.FavoriteMountainFragment
import com.entsh104.highking.ui.cust.trip.FavoriteTripFragment

class FavoritesPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteMountainFragment()
            else -> FavoriteTripFragment()
        }
    }
}
