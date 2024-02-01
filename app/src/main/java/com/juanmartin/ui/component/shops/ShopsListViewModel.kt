package com.juanmartin.ui.component.shops

import android.location.Location
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.juanmartin.data.DataRepositorySource
import com.juanmartin.data.Resource
import com.juanmartin.data.dto.comercios.Shops
import com.juanmartin.ui.base.BaseViewModel
import com.juanmartin.ui.component.shops.entities.ParamFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShopsListViewModel @Inject
constructor(private val dataRepositoryRepository: DataRepositorySource) : BaseViewModel() {

    var params = ParamFilter(0.0, 0.0, "")

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
    val shopsLiveDataPrivate = MutableLiveData<Resource<Shops>>()
    val shopsLiveData: LiveData<Resource<Shops>> get() = shopsLiveDataPrivate

    val filterByCategoriesPrivate = MutableLiveData<String>()
    val filterByCategoriesData: LiveData<String> get() = filterByCategoriesPrivate

    val totalShopsPrivate = MutableLiveData<Int>()
    val totalShopsData: LiveData<Int> get() = totalShopsPrivate

    val totalNearShopsPrivate = MutableLiveData<Int>()
    val totalNearShopsData: LiveData<Int> get() = totalNearShopsPrivate

    val searchFoundPrivate: MutableLiveData<String> = MutableLiveData()
    val searchFound: LiveData<String> get() = searchFoundPrivate

    val noSearchFoundPrivate: MutableLiveData<Unit> = MutableLiveData()
    val noSearchFound: LiveData<Unit> get() = noSearchFoundPrivate

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    private val openShopDetailsPrivate = MutableLiveData<Shops.ShopsItem>()
    val openShopDetails: LiveData<Shops.ShopsItem> get() = openShopDetailsPrivate

    /**
     * Error handling as UI
     */
    private val showSnackBarPrivate = MutableLiveData<Any>()
    val showSnackBar: LiveData<Any> get() = showSnackBarPrivate

    private val showToastPrivate = MutableLiveData<Any>()
    val showToast: LiveData<Any> get() = showToastPrivate


    fun getShops(location: Location?) {
        if(location != null) {
            params = params.copy(
                latitude = location.latitude,
                longitude = location.longitude
            )
        }
        viewModelScope.launch {
            shopsLiveDataPrivate.value = Resource.Loading()
            dataRepositoryRepository.requestShops(params).collect {
                shopsLiveDataPrivate.value = it
            }
        }
    }

    fun openShopDetails(shopItem: Shops.ShopsItem) {
        openShopDetailsPrivate.value = shopItem
    }

    fun filterByCategories(category: String) {
        filterByCategoriesPrivate.value = category
    }

    fun updateShops(totalShops: Int) {
        totalShopsPrivate.value = totalShops
    }

    fun updateNearShops(totalNearShops: Int) {
        totalNearShopsPrivate.value = totalNearShops
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = error.description
    }

    fun onSearchClick(query: String) {
        searchFoundPrivate.value = query
    }
}
