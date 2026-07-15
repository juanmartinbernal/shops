package com.juanmartin.feature.shops.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.juanmartin.feature.shops.R
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private const val STATIC_MAPS_URL = "https://maps.googleapis.com/maps/api/staticmap?center="
private const val STATIC_MAPS_KEY = "AIzaSyBnuBof4SnWG7zL55lVE__E2xDqhJz8a3o"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopDetailsScreen(
    shopId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShopDetailsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(shopId) {
        viewModel.onIntent(ShopDetailsIntent.Load(shopId))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ShopDetailsEffect.OpenMap -> {
                    val uri = Uri.parse("geo:0,0?q=${effect.latitude},${effect.longitude}")
                    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(state.shop?.name.orEmpty()) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        val shop = state.shop
        when {
            state.isLoading -> Box(Modifier.fillMaxSize().padding(padding)) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }

            shop == null -> Box(Modifier.fillMaxSize().padding(padding)) {
                Text(
                    stringResource(R.string.no_data),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = shop.logo.url.ifEmpty { null },
                    contentDescription = shop.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(Modifier.height(16.dp))

                val lat = shop.latitude
                val lng = shop.longitude
                if (lat != null && lng != null) {
                    AsyncImage(
                        model = "$STATIC_MAPS_URL$lat,$lng&zoom=14&size=400x400&markers=$lat,$lng&key=$STATIC_MAPS_KEY",
                        contentDescription = stringResource(R.string.location),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { viewModel.onIntent(ShopDetailsIntent.OpenMapClicked) }) {
                            Icon(Icons.Filled.LocationOn, contentDescription = null)
                        }
                        Text(stringResource(R.string.go_location))
                    }
                }

                InfoRow(shop.openingHours)
                InfoRow(shop.contact.phone, Icons.Filled.Phone)
                InfoRow(
                    listOf(shop.address.city, shop.address.street)
                        .filter { it.isNotBlank() }
                        .joinToString(", "),
                    Icons.Filled.LocationOn
                )

                if (shop.description.isNotBlank()) {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        stringResource(R.string.about_shop),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(shop.description)
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    if (text.isBlank()) return
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(Modifier.size(8.dp))
        }
        Text(text)
    }
}
