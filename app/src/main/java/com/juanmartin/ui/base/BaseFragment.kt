package com.juanmartin.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.juanmartin.utils.popBackStackAllInstances

abstract class BaseFragment : Fragment() {

    abstract fun observeViewModel()
    var isNavigated = false
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        navController = Navigation.findNavController(view)
    }

    fun navigateWithParams(resId: Int, bundle: Bundle) {
        isNavigated = true
        navController.navigate(resId, bundle)
    }

    fun navigate(resId: Int) {
        isNavigated = true
        navController.navigate(resId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!isNavigated)
        // requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (navController.currentBackStackEntry?.destination?.id != null) {
                navController.popBackStackAllInstances(
                    navController.currentBackStackEntry?.destination?.id!!,
                    true
                )
            } else
                navController.popBackStack()
        //}
    }

}