package com.juanmartin.feature.shops.details

import com.juanmartin.core.mvi.UiEffect
import com.juanmartin.core.mvi.UiIntent
import com.juanmartin.core.mvi.UiState
import com.juanmartin.data.model.Shop

data class ShopDetailsState(
    val isLoading: Boolean = true,
    val shop: Shop? = null
) : UiState

sealed interface ShopDetailsIntent : UiIntent {
    data class Load(val shopId: String) : ShopDetailsIntent
    data object OpenMapClicked : ShopDetailsIntent
}

sealed interface ShopDetailsEffect : UiEffect {
    data class OpenMap(val latitude: Double, val longitude: Double) : ShopDetailsEffect
}
