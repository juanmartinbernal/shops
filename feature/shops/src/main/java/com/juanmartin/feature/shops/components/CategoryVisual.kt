package com.juanmartin.feature.shops.components

import androidx.compose.ui.graphics.Color
import com.juanmartin.core.theme.BackgroundBeauty
import com.juanmartin.core.theme.BackgroundFood
import com.juanmartin.core.theme.BackgroundLeisure
import com.juanmartin.core.theme.BackgroundOther
import com.juanmartin.core.theme.BackgroundShopping
import com.juanmartin.feature.shops.list.ALL_CATEGORY

object Categories {
    const val SHOPPING = "SHOPPING"
    const val FOOD = "FOOD"
    const val LEISURE = "LEISURE"
    const val BEAUTY = "BEAUTY"
    const val OTHER = "OTHER"
}

/** Icono (desde assets) y color asociados a una categoría. */
data class CategoryVisual(val assetUri: String, val color: Color)

private fun asset(name: String) = "file:///android_asset/$name"

fun categoryVisual(category: String): CategoryVisual = when (category) {
    Categories.SHOPPING -> CategoryVisual(asset("Cart_white.png"), BackgroundShopping)
    Categories.FOOD -> CategoryVisual(asset("Catering_white.png"), BackgroundFood)
    Categories.LEISURE -> CategoryVisual(asset("Leisure_white.png"), BackgroundLeisure)
    Categories.BEAUTY -> CategoryVisual(asset("Truck_white.png"), BackgroundBeauty)
    ALL_CATEGORY -> CategoryVisual(asset("placeholder.png"), BackgroundOther)
    else -> CategoryVisual(asset("EES_white.png"), BackgroundOther)
}
