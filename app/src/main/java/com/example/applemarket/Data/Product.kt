package com.example.applemarket.Data;

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val imageUrl: Int,
    val price:Int,
    val seller : String,
    val address : String,
    val description : String
) : Parcelable


