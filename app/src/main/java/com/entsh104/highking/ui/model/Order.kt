package com.entsh104.highking.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val imageResId: Int,
    val name: String,
    val price: String
) : Parcelable
