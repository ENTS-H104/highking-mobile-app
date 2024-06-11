package com.entsh104.highking.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mountain(
    val imageResId: Int,
    val name: String,
    val elevation: Int,
    val location: String,
    val tripCount: Int,
    var isLoved: Boolean,
    val description: String = "",
    val weather: String = "",
    val temperature: String = "",
    val entryFee: String = "",
) : Parcelable
