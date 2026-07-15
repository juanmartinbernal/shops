package com.juanmartin.feature.shops.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.juanmartin.feature.shops.list.ALL_CATEGORY

@Composable
fun CategoryRow(
    categories: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(categories, key = { it }) { category ->
            val visual = categoryVisual(category)
            FilterChip(
                selected = category == selected,
                onClick = { onSelected(category) },
                modifier = Modifier.padding(end = 8.dp),
                leadingIcon = {
                    AsyncImage(
                        model = visual.assetUri,
                        contentDescription = category,
                        modifier = Modifier.size(18.dp)
                    )
                },
                label = {
                    Text(if (category == ALL_CATEGORY) "ALL" else category)
                }
            )
        }
    }
}
