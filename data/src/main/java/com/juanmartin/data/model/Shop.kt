package com.juanmartin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/** Comercio devuelto por la API. */
@Parcelize
data class Shop(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val shortDescription: String = "",
    val openingHours: String = "",
    val currency: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: Address = Address(),
    val contact: Contact = Contact(),
    val logo: Logo = Logo(),
    val photos: List<Photo> = emptyList(),
    var distance: Double = 0.0
) : Parcelable {

    @Parcelize
    data class Address(
        val city: String = "",
        val country: String = "",
        val street: String = ""
    ) : Parcelable

    @Parcelize
    data class Contact(
        val email: String = "",
        val phone: String = "",
        val web: String = ""
    ) : Parcelable

    @Parcelize
    data class Logo(
        val format: String = "",
        val url: String = "",
        val thumbnails: Thumbnails = Thumbnails()
    ) : Parcelable

    @Parcelize
    data class Photo(
        val _id: String = "",
        val format: String = "",
        val url: String = "",
        val thumbnails: Thumbnails = Thumbnails()
    ) : Parcelable

    @Parcelize
    data class Thumbnails(
        val large: String = "",
        val medium: String = "",
        val small: String = ""
    ) : Parcelable
}
