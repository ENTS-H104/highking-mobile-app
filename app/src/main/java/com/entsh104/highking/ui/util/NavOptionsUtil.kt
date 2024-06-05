package com.entsh104.highking.ui.util

import androidx.navigation.NavOptions
import com.entsh104.highking.R

object NavOptionsUtil {
    val defaultNavOptions: NavOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()
}
