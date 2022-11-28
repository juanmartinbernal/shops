package com.juanmartin.data.dto.comercios

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class Shops (val shopsList : ArrayList<ShopsItem>){

    @Parcelize
    data class ShopsItem(
        val __v: Int = 0,
        val accountManager: AccountManager = AccountManager(),
        val active: Boolean = false,
        val address: Address = Address(),
        val category: String = "",
        val clonedFrom: String = "",
        val config: Config = Config(),
        val contact: Contact = Contact(),
        val contractId: String = "",
        val contractId_ornot: String = "",
        val currency: String = "",
        val deliveroo: Deliveroo = Deliveroo(),
        val description: String = "",
        val features: List<String> = listOf(),
        val franchiseId: String = "",
        val franchiseId_old: String = "",
        val franchises: List<String> = listOf(),
        val id: String = "",
        val ipad: Boolean = false,
        val ipadPhotos: IpadPhotos = IpadPhotos(),
        val ipadVideo: IpadVideo = IpadVideo(),
        val latitude: Double? = 0.0,
        val locale: String = "",
        val logo: Logo = Logo(),
        val longitude: Double? = 0.0,
        val minLegalAge: Int = 0,
        val moved: Boolean = false,
        val name: String = "",
        val oldContractId: String = "",
        val oldFranchiseId: String = "",
        val oldOwnerId: String = "",
        val oldYoin: Boolean = false,
        val openingHours: String = "",
        val ownerId: String = "",
        val photos: List<Photo>? = listOf(),
        val pointsGroupId: String = "",
        val polar: Boolean = false,
        val pos: Boolean = false,
        val salesPerson: SalesPerson = SalesPerson(),
        val shortDescription: String = "",
        val slug: String = "",
        val social: Social = Social(),
        val startDate: String = "",
        val stuart: Stuart = Stuart(),
        val surveyRequired: Boolean = false,
       // val tags: List<Any> = listOf(),
        val timezone: String = "",
        val v1: V1 = V1(),
        val vromo: Vromo = Vromo(),
        val wayletCommerceId: String = "",
        val whiteLabel: Boolean = false,
        val whiteLabelGroupId: List<String> = listOf(),
        var distance : Double = 0.0
    ): Parcelable {
        @Parcelize
        data class AccountManager(
            val email: String = "",
            val name: String = ""
        ): Parcelable
        @Parcelize
        data class Address(
            val city: String = "",
            val country: String = "",
            val street: String = ""
           // val zip: Int = 0
        ): Parcelable
        @Parcelize
        data class Config(
            val currency: String = "",
            val locale: String = "",
            val timezone: String = ""
        ): Parcelable
        @Parcelize
        data class Contact(
            val email: String = "",
            val phone: String = "",
            val web: String = ""
        ): Parcelable
        @Parcelize
        data class Deliveroo(
            val active: Boolean = false,
            val id: String = ""
        ): Parcelable
        @Parcelize
        data class IpadPhotos(
            val carousel: List<Carousel> = listOf(),
            val logo: Logo = Logo(),
            val rewards: Rewards = Rewards(),
            val welcome: Welcome = Welcome()
        ): Parcelable {
            @Parcelize
            data class Carousel(
                val _id: String = "",
                val format: String = "",
                val thumbnails: Thumbnails = Thumbnails(),
                val url: String = ""
            ): Parcelable {
                @Parcelize
                data class Thumbnails(
                    val large: String = "",
                    val medium: String = "",
                    val small: String = ""
                ): Parcelable
            }
            @Parcelize
            data class Logo(
                val format: String = "",
                val thumbnails: Thumbnails = Thumbnails(),
                val url: String = ""
            ): Parcelable {
                @Parcelize
                data class Thumbnails(
                    val large: String = "",
                    val medium: String = "",
                    val small: String = ""
                ): Parcelable
            }
            @Parcelize
            data class Rewards(
                val format: String = "",
                val thumbnails: Thumbnails = Thumbnails(),
                val url: String = ""
            ): Parcelable {
                @Parcelize
                data class Thumbnails(
                    val large: String = "",
                    val medium: String = "",
                    val small: String = ""
                ): Parcelable
            }
            @Parcelize
            data class Welcome(
                val format: String = "",
                val thumbnails: Thumbnails = Thumbnails(),
                val url: String = ""
            ): Parcelable {
                @Parcelize
                data class Thumbnails(
                    val large: String = "",
                    val medium: String = "",
                    val small: String = ""
                ): Parcelable
            }
        }
        @Parcelize
        data class IpadVideo(
            val format: String = "",
            val url: String = ""
        ): Parcelable

        @Parcelize
        data class Logo(
            val format: String = "",
            val thumbnails: Thumbnails = Thumbnails(),
            val url: String = ""
        ): Parcelable {
            @Parcelize
            data class Thumbnails(
                val large: String = "",
                val medium: String = "",
                val small: String = ""
            ): Parcelable
        }
        @Parcelize
        data class Photo(
            val _id: String = "",
            val format: String = "",
            val thumbnails: Thumbnails = Thumbnails(),
            val url: String = ""
        ) : Parcelable{
            @Parcelize
            data class Thumbnails(
                val large: String = "",
                val medium: String = "",
                val small: String = ""
            ): Parcelable
        }
        @Parcelize
        data class SalesPerson(
            val email: String = "",
            val name: String = ""
        ): Parcelable
        @Parcelize
        data class Social(
            val facebook: String = "",
            val flickr: String = "",
            val instagram: String = "",
            val pinterest: String = "",
            val restoin: String = "",
            val tripadvisor: String = "",
            val twitter: String = "",
            val youtube: String = ""
        ): Parcelable
        @Parcelize
        data class Stuart(
            val active: Boolean = false,
            val apiKey: String = "",
            val apiSecret: String = "",
            val customErrorManagement: Boolean = false,
            val customErrorMessage: String = "",
            val forceDeliverySize: ForceDeliverySize = ForceDeliverySize()
        ): Parcelable {
            @Parcelize
            data class ForceDeliverySize(
                val active: Boolean = false,
                val size: String = ""
            ): Parcelable
        }
        @Parcelize
        data class V1(
            val activeDeal: Int = 0,
            val authCards: List<String> = listOf(),
            val coverImage: String = "",
            val id: Int = 0,
            val installation: Installation = Installation(),
            val key: String = "",
            val lang: String = "",
            val mapMarker: String = "",
            val oldId: Int = 0,
            val oldid: Int = 0,
            val otrooldid: Int = 0,
            val ownerId: String = "",
           // val signatures: Signatures = Signatures(),
            val slug: String = ""
        ) : Parcelable{
            @Parcelize
            data class Installation(
                val commercial: Commercial = Commercial(),
                val date: String = "",
                val operations: Operations = Operations()
            ) : Parcelable{
                @Parcelize
                data class Commercial(
                    val email: String = "",
                    val name: String = ""
                ): Parcelable
                @Parcelize
                data class Operations(
                    val email: String = "",
                    val name: String = ""
                ): Parcelable
            }
            @Parcelize
            data class Signatures(
                val apiKey: String = "",
                val signature: String = ""
            ): Parcelable
        }
        @Parcelize
        data class Vromo(
            val active: Boolean = false,
            val apiKey: String = "",
            val siteId: String = ""
        ): Parcelable
    }
}