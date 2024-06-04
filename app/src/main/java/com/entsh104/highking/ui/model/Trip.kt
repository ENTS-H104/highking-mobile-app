package com.entsh104.highking.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trip(
    val imageResId: Int,
    val name: String,
    val mountainName: String,
    val price: String,
    var isLoved: Boolean,
    val capacity: Int
) : Parcelable
