package com.juanmartin.feature.shops.list

import androidx.lifecycle.viewModelScope
import com.juanmartin.core.error.ErrorMapper
import com.juanmartin.core.location.LocationProvider
import com.juanmartin.core.mvi.MviViewModel
import com.juanmartin.domain.common.Resource
import com.juanmartin.domain.location.GeoLocation
import com.juanmartin.domain.model.Shop
import com.juanmartin.domain.usecase.GetShopsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ShopsListViewModel(
    private val getShops: GetShopsUseCase,
    private val locationProvider: LocationProvider,
    private val errorMapper: ErrorMapper
) : MviViewModel<ShopsListState, ShopsListIntent, ShopsListEffect>() {

    override fun initialState() = ShopsListState()

    override fun onIntent(intent: ShopsListIntent) {
        when (intent) {
            ShopsListIntent.LoadShops,
            ShopsListIntent.Retry -> loadShops()

            is ShopsListIntent.SelectCategory ->
                setState { copy(selectedCategory = intent.category) }

            is ShopsListIntent.QueryChanged ->
                setState { copy(query = intent.query) }

            ShopsListIntent.ToggleNearOnly ->
                setState { copy(showNearOnly = !showNearOnly) }

            is ShopsListIntent.ShopClicked ->
                sendEffect { ShopsListEffect.NavigateToDetails(intent.shop.id) }
        }
    }

    private fun loadShops() {
        viewModelScope.launch {
            val location = runCatching { locationProvider.getCurrentLocation() }.getOrNull()
                ?: GeoLocation(0.0, 0.0)
            getShops(location)
                .onEach { resource -> reduce(resource) }
                .launchIn(viewModelScope)
        }
    }

    private fun reduce(resource: Resource<List<Shop>>) {
        when (resource) {
            Resource.Loading ->
                setState { copy(isLoading = true, errorMessage = null) }

            is Resource.Success ->
                setState {
                    copy(
                        isLoading = false,
                        allShops = resource.data,
                        categories = buildCategories(resource.data),
                        errorMessage = null
                    )
                }

            is Resource.Error -> {
                val error = errorMapper.getError(resource.errorCode)
                setState { copy(isLoading = false, errorMessage = error.description) }
                sendEffect { ShopsListEffect.ShowMessage(error.description) }
            }
        }
    }

    private fun buildCategories(shops: List<Shop>): List<String> {
        val categories = shops.map { it.category }
            .filter { it.isNotEmpty() }
            .distinct()
        return listOf(ALL_CATEGORY) + categories
    }
}
