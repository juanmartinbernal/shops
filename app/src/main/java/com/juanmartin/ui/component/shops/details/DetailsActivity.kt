package com.juanmartin.ui.component.shops.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import com.juanmartin.API_KEY_STATIC_MAPS
import com.juanmartin.R
import com.juanmartin.SHOP_ITEM_KEY
import com.juanmartin.URL_STATIC_MAPS
import com.juanmartin.data.dto.comercios.ShopsItem
import com.juanmartin.databinding.DetailsLayoutBinding
import com.juanmartin.ui.base.BaseActivity
import com.juanmartin.utils.loadImage
import com.juanmartin.utils.observe
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsActivity : BaseActivity() {

    private val viewModel: DetailsViewModel by viewModels()

    private lateinit var binding: DetailsLayoutBinding
    private var menu: Menu? = null


    override fun initViewBinding() {
        binding = DetailsLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initIntentData(
            intent.getParcelableExtra(SHOP_ITEM_KEY)
                ?: ShopsItem()
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun observeViewModel() {
        observe(viewModel.shopData, ::initializeView)
    }

    private fun initializeView(shopItem: ShopsItem) {
        supportActionBar?.title = shopItem.name
        binding.ivShopImage.loadImage(shopItem.logo?.url)

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
            imgUrl = shopItem.photos?.get(0)?.thumbnails?.medium.toString();
        }
        binding.imgExtras.loadImage(imgUrl)
        binding.txtInfo1Extras.text = shopItem.shortDescription
        binding.txtInfo2Extras.text = shopItem.openingHours
        binding.txtInfo2Extras.isSelected = true
        binding.txtInfo3Extras.text = shopItem.contact?.phone
        binding.txtInfo4Extras.text =
            shopItem.address?.city.plus(", ").plus(shopItem.address?.street)

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
