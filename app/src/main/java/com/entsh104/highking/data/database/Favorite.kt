package com.entsh104.highking.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tb_favorite")
@Parcelize
data class Favorite(
    @PrimaryKey(autoGenerate = false)

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "mountainUuid")
    var mountainUuid: String = "",

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String? = null,
) : Parcelable