package com.getlose.mytemplate.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class MaskModel(
    val type: String,
    val features: List<Feature>
)

@Parcelize
data class Feature(
        val type: String,
        val properties: Properties,
        val geometry: Geometry
): Parcelable

@Parcelize
data class Properties(
        val id: String,
        val name: String,
        val phone: String,
        val address: String,
        val mask_adult: Int,
        val mask_child: Int,
        val updated: String,
        val available: String,
        val note: String,
        val custom_note: String,
        val website: String,
        val county: String,
        val town: String,
        val cunli: String,
        val service_periods: String
): Parcelable

@Parcelize
data class Geometry(
        val type: String,
        val coordinates: List<Double>
): Parcelable