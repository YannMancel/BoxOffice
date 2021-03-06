package com.mancel.yann.boxoffice.views.activities

import android.app.SearchManager
import android.content.Context
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.utils.MessageTools
import com.mancel.yann.boxoffice.views.fragments.BaseFragment
import com.mancel.yann.boxoffice.views.fragments.FragmentCallback
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.activities
 *
 * A [BaseActivity] subclass which implements [FragmentCallback].
 */
class MainActivity : BaseActivity(), FragmentCallback {

    // FIELDS --------------------------------------------------------------------------------------

    private val mNavController by lazy { this.findNavController(R.id.activity_main_NavHostFragment) }
    private lateinit var mSearchView: SearchView

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

    // -- Activity --

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.menu_toolbar, menu)
        this.configureBehaviorOfToolBar()
        this.configureSearchItemOfToolbar(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.toolbar_menu_sync -> {
                // Call the syncData method of current Fragment
                val fragment = this.supportFragmentManager
                                   .findFragmentById(R.id.activity_main_NavHostFragment)!!
                                   .childFragmentManager
                                   .fragments[0]

                if (fragment is BaseFragment) {
                    fragment.syncData()
                }

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // -- FragmentCallback interface --

    override fun showMessage(message: String) {
        MessageTools.showMessageWithSnackbar(this.activity_main_CoordinatorLayout, message)
    }

    override fun navigationEvent() {
        if (!this.mSearchView.isIconified) {
            this.getToolBar()?.menu!!
                              .findItem(R.id.toolbar_menu_search)
                              .collapseActionView()
        }
    }

    // -- Toolbar --

    /**
     * Configures the behavior of the [Toolbar]
     */
    private fun configureBehaviorOfToolBar() {
        this.mNavController.addOnDestinationChangedListener { _, destination, _ ->
            val syncItem = this.getToolBar()!!
                               .menu!!
                               .findItem(R.id.toolbar_menu_sync)

            val searchItem = this.getToolBar()!!
                                 .menu!!
                                 .findItem(R.id.toolbar_menu_search)

            when (destination.id) {
                R.id.navigation_ListFragment -> {
                    syncItem.isVisible = true
                    searchItem.isVisible = true
                }

                R.id.navigation_DetailsFragment -> {
                    syncItem.isVisible = false
                    searchItem.isVisible = false
                }

                else -> Log.e(this@MainActivity::class.simpleName,
                    "addOnDestinationChangedListener: The Id of the current destination is not good.")
            }
        }
    }

    /**
     * Configures the Search item fo the [Toolbar]
     */
    private fun configureSearchItemOfToolbar(menu: Menu?) {
        // Associate searchable configuration with the SearchView
        val searchManager = this.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        this.mSearchView = (menu!!.findItem(R.id.toolbar_menu_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))

            setOnQueryTextListener(object: SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // Call the searchData method of current Fragment
                    newText?.let {
                        val fragment = this@MainActivity.supportFragmentManager
                                                        .findFragmentById(R.id.activity_main_NavHostFragment)!!
                                                        .childFragmentManager
                                                        .fragments[0]

                        if (fragment is BaseFragment) {
                            fragment.searchData(it)
                        }
                    }

                    return false
                }
            })
        }
    }

    // -- Navigation component --

    private fun configureFragmentNavigation() {
        // AppBarConfiguration
        val appBarConfiguration = AppBarConfiguration(this.mNavController.graph)

        // Toolbar
        this.activity_main_Toolbar.setupWithNavController(this.mNavController, appBarConfiguration)
    }
}