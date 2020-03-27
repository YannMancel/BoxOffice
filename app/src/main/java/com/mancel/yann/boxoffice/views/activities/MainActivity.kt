package com.mancel.yann.boxoffice.views.activities

import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mancel.yann.boxoffice.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.activities
 *
 * A [BaseActivity] subclass.
 */
class MainActivity : BaseActivity() {

    // METHODS -------------------------------------------------------------------------------------

    // -- BaseActivity --

    override fun getActivityLayout(): Int = R.layout.activity_main

    override fun getToolBar(): Toolbar? = this.activity_main_Toolbar

    override fun configureDesign() {
        // UI
        this.configureToolbar()

        // Navigation
        this.configureFragmentNavigation()
    }

    // -- Navigation component --

    private fun configureFragmentNavigation() {
        // NavController
        val navController = this.findNavController(R.id.activity_main_NavHostFragment)

        // AppBarConfiguration
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        // Toolbar
        this.activity_main_Toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}