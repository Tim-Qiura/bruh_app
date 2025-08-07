package com.example.bruh.models

data class Patient(
    var id: String? = null,
    val name: String?= null,
    val gender: String? = null,
    val nationality: String? = null,
    val phone_number : String? = null,
    val age : String?= null,
    val diagnosis : String?= null,
    val imageUrl : String? = null,
)
