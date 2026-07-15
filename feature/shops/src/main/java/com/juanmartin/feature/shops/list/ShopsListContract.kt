package com.juanmartin.feature.shops.list

import com.juanmartin.core.mvi.UiEffect
import com.juanmartin.core.mvi.UiIntent
import com.juanmartin.core.mvi.UiState
import com.juanmartin.data.model.Shop

/** Categoría especial que representa "todas". */
const val ALL_CATEGORY = ""
private const val NEAR_THRESHOLD_KM = 10.0

data class ShopsListState(
    val isLoading: Boolean = false,
    val allShops: List<Shop> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String = ALL_CATEGORY,
    val query: String = "",
    val showNearOnly: Boolean = false,
    val errorMessage: String? = null
) : UiState {

    val visibleShops: List<Shop>
        get() = allShops.asSequence()
            .filter { selectedCategory == ALL_CATEGORY || it.category == selectedCategory }
            .filter {
                query.isBlank() ||
                    it.name.contains(query, ignoreCase = true) ||
                    it.category.contains(query, ignoreCase = true)
            }
            .filter { !showNearOnly || it.distance < NEAR_THRESHOLD_KM }
            .toList()

    val nearCount: Int get() = allShops.count { it.distance < NEAR_THRESHOLD_KM }
}

sealed interface ShopsListIntent : UiIntent {
    data object LoadShops : ShopsListIntent
    data object Retry : ShopsListIntent
    data class SelectCategory(val category: String) : ShopsListIntent
    data class QueryChanged(val query: String) : ShopsListIntent
    data object ToggleNearOnly : ShopsListIntent
    data class ShopClicked(val shop: Shop) : ShopsListIntent
}

sealed interface ShopsListEffect : UiEffect {
    data class NavigateToDetails(val shopId: String) : ShopsListEffect
    data class ShowMessage(val message: String) : ShopsListEffect
}
