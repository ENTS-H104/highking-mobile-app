package com.entsh104.highking.ui.model

data class Mountain(
    val imageResId: Int,
    val name: String,
    val elevation: Int,
    val city: String,
    val openTrips: Int,
    var isLoved: Boolean
)
