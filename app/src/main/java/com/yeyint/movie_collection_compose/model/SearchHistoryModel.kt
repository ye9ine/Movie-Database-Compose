package com.yeyint.movie_collection.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "searchHistoryTable")

data class SearchHistoryModel(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo
    val searchKey: String,
): Parcelable