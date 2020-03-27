package com.mancel.yann.boxoffice.views.activities

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.activities
 *
 * An abstract [AppCompatActivity] subclass.
 */
abstract class BaseActivity : AppCompatActivity() {

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Gets the integer value of the activity layout
     * @return an integer that corresponds to the activity layout
     */
    @LayoutRes
    protected abstract fun getActivityLayout(): Int

    /**
     * Get the [Toolbar]
     * @return a [Toolbar]
     */
    protected abstract fun getToolBar(): Toolbar?

    /**
     * Configures the design of each daughter class
     */
    protected abstract fun configureDesign()

    // -- AppCompatActivity --

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(this.getActivityLayout())
        this.configureDesign()
    }

    // -- Toolbar --

    /**
     * Configures the [Toolbar]
     */
    protected fun configureToolbar() {
        this.getToolBar()?.let{
            this.setSupportActionBar(it)
        }
    }
}