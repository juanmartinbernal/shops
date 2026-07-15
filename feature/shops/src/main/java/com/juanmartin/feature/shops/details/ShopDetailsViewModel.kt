package com.juanmartin.feature.shops.details

import com.juanmartin.core.mvi.MviViewModel
import com.juanmartin.domain.usecase.GetShopByIdUseCase

class ShopDetailsViewModel(
    private val getShopById: GetShopByIdUseCase
) : MviViewModel<ShopDetailsState, ShopDetailsIntent, ShopDetailsEffect>() {

    override fun initialState() = ShopDetailsState()

    override fun onIntent(intent: ShopDetailsIntent) {
        when (intent) {
            is ShopDetailsIntent.Load -> {
                val shop = getShopById(intent.shopId)
                setState { copy(isLoading = false, shop = shop) }
            }

            ShopDetailsIntent.OpenMapClicked -> {
                val shop = currentState.shop ?: return
                val lat = shop.latitude ?: return
                val lng = shop.longitude ?: return
                sendEffect { ShopDetailsEffect.OpenMap(lat, lng) }
            }
        }
    }
}
