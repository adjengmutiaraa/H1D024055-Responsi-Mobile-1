package com.example.pantaugempa.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Gempa(
    @SerializedName("Infogempa")
    val infogempa: InfoGempa
) : Serializable

data class InfoGempa(
    @SerializedName("gempa")
    val gempaList: List<GempaItem>
) : Serializable

data class GempaItem(
    @SerializedName("Tanggal") val tanggal: String,
    @SerializedName("Jam") val jam: String,
    @SerializedName("DateTime") val dateTime: String? = null,
    @SerializedName("Coordinates") val coordinates: String,
    @SerializedName("Lintang") val lintang: String? = null,
    @SerializedName("Bujur") val bujur: String? = null,
    @SerializedName("Magnitude") val magnitude: String,
    @SerializedName("Kedalaman") val kedalaman: String,
    @SerializedName("Wilayah") val wilayah: String,
    @SerializedName("Potensi") val potensi: String
) : Serializable

// Extension function sebagai pemanfaatan fitur bahasa Kotlin
fun GempaItem.formattedMagnitude(): String = "$magnitude SR"
fun GempaItem.formattedWaktu(): String = "$tanggal | $jam"