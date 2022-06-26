package com.juanmartin.ui.base.listeners

import com.juanmartin.data.dto.comercios.Shops

interface RecyclerItemListener {
    fun onItemSelected(shopItem: Shops.ShopsItem)
}