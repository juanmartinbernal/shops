package com.juanmartin.feature.shops.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.juanmartin.feature.shops.details.ShopDetailsScreen
import com.juanmartin.feature.shops.list.ShopsListScreen
import kotlinx.coroutines.launch

object ShopsDestinations {
    const val LIST = "shops_list"
    const val DETAILS = "shop_details/{shopId}"
    fun details(shopId: String) = "shop_details/$shopId"
}

/** Registra el grafo de navegación de la feature de comercios. */
fun NavGraphBuilder.shopsGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    composable(ShopsDestinations.LIST) {
        val scope = rememberCoroutineScope()
        ShopsListScreen(
            onShopClick = { shopId ->
                navController.navigate(ShopsDestinations.details(shopId))
            },
            onMessage = { message ->
                scope.launch { snackbarHostState.showSnackbar(message) }
            }
        )
    }
    composable(
        route = ShopsDestinations.DETAILS,
        arguments = listOf(navArgument("shopId") { type = NavType.StringType })
    ) { entry ->
        val shopId = entry.arguments?.getString("shopId").orEmpty()
        ShopDetailsScreen(
            shopId = shopId,
            onBack = { navController.popBackStack() }
        )
    }
}
