package com.juanmartin.feature.shops.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juanmartin.feature.shops.R
import com.juanmartin.feature.shops.components.CategoryRow
import com.juanmartin.feature.shops.components.ShopItem
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopsListScreen(
    onShopClick: (String) -> Unit,
    onMessage: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShopsListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(ShopsListIntent.LoadShops)
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ShopsListEffect.NavigateToDetails -> onShopClick(effect.shopId)
                is ShopsListEffect.ShowMessage -> onMessage(effect.message)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(stringResource(R.string.shops_title)) }) }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {

            OutlinedTextField(
                value = state.query,
                onValueChange = { viewModel.onIntent(ShopsListIntent.QueryChanged(it)) },
                singleLine = true,
                label = { Text(stringResource(R.string.search)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.total_shops, state.visibleShops.size),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = state.showNearOnly,
                    onClick = { viewModel.onIntent(ShopsListIntent.ToggleNearOnly) },
                    leadingIcon = {
                        Icon(Icons.Filled.NearMe, contentDescription = null)
                    },
                    label = {
                        Text(stringResource(R.string.near_shops, state.nearCount))
                    }
                )
            }

            if (state.categories.isNotEmpty()) {
                Spacer(Modifier.size(8.dp))
                CategoryRow(
                    categories = state.categories,
                    selected = state.selectedCategory,
                    onSelected = { viewModel.onIntent(ShopsListIntent.SelectCategory(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Box(Modifier.fillMaxSize()) {
                when {
                    state.isLoading -> CircularProgressIndicator(
                        Modifier.align(Alignment.Center)
                    )

                    state.visibleShops.isEmpty() -> Text(
                        text = stringResource(R.string.no_data),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    else -> LazyColumn(Modifier.fillMaxSize()) {
                        items(state.visibleShops, key = { it.id }) { shop ->
                            ShopItem(
                                shop = shop,
                                onClick = { viewModel.onIntent(ShopsListIntent.ShopClicked(shop)) }
                            )
                        }
                    }
                }
            }
        }
    }
}
