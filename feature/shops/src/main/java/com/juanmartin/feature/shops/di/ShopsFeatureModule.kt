package com.juanmartin.feature.shops.di

import com.juanmartin.feature.shops.details.ShopDetailsViewModel
import com.juanmartin.feature.shops.list.ShopsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val shopsFeatureModule = module {
    viewModel { ShopsListViewModel(get(), get(), get()) }
    viewModel { ShopDetailsViewModel(get()) }
}
