package com.juanmartin.ui.component.shops.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.juanmartin.API_KEY_STATIC_MAPS
import com.juanmartin.SHOP_ITEM_KEY
import com.juanmartin.URL_STATIC_MAPS
import com.juanmartin.data.dto.comercios.Shops
import com.juanmartin.databinding.DetailsLayoutBinding
import com.juanmartin.ui.base.BaseFragment
import com.juanmartin.utils.loadImage
import com.juanmartin.utils.observe
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : BaseFragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var binding: DetailsLayoutBinding
    private lateinit var navController: NavController

    override fun observeViewModel() {
        observe(viewModel.shopData, ::initializeView)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailsLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.initIntentData(
                it.getParcelable(SHOP_ITEM_KEY)
                    ?: Shops.ShopsItem()
            )
        }
    }

    private fun initializeView(shopItem: Shops.ShopsItem) {
        activity?.title = shopItem.name
        binding.ivShopImage.loadImage(shopItem.logo.url)

        //location
        val latitude = shopItem.latitude
        val longitude = shopItem.longitude

        binding.txtLocation.setOnClickListener {
            goToMap(latitude, longitude)
        }
        binding.imgLocationMap.setOnClickListener {
            goToMap(latitude, longitude)
        }

        binding.imgLocationMap.loadImage(URL_STATIC_MAPS + latitude.toString() + "," + longitude.toString() + "&zoom=14&size=400x400&markers=" + latitude.toString() + "," + longitude.toString() + "&key=" + API_KEY_STATIC_MAPS)

        //extras
        var imgUrl = ""
        if (shopItem.photos?.size != 0) {
            imgUrl = shopItem.photos?.get(0)?.thumbnails?.medium.toString()
        }
        binding.imgExtras.loadImage(imgUrl)
        binding.txtInfo1Extras.text = shopItem.shortDescription
        binding.txtInfo2Extras.text = shopItem.openingHours
        binding.txtInfo2Extras.isSelected = true
        binding.txtInfo3Extras.text = shopItem.contact.phone
        binding.txtInfo4Extras.text =
            shopItem.address.city.plus(", ").plus(shopItem.address.street)

        //about shop
        binding.tvDescription.text = shopItem.description
    }

    private fun goToMap(latitude: Double?, longitude: Double?) {
        val locationUri: Uri =
            Uri.parse("geo:0,0?q=".plus(latitude.toString()).plus(",").plus(longitude.toString()))
        val intent = Intent(Intent.ACTION_VIEW, locationUri)
        startActivity(intent)
    }
}
