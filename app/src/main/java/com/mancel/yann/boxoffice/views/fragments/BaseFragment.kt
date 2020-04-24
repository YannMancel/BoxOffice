package com.mancel.yann.boxoffice.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.mancel.yann.boxoffice.viewModels.BoxOfficeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.fragments
 *
 * An abstract [Fragment] subclass.
 */
abstract class BaseFragment : Fragment() {

    // FIELDS --------------------------------------------------------------------------------------

    protected lateinit var mRootView: View
    protected var mCallback: FragmentCallback? = null
    protected val mViewModel: BoxOfficeViewModel by viewModel()

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Gets the integer value of the fragment layout
     * @return an integer that corresponds to the fragment layout
     */
    @LayoutRes
    protected abstract fun getFragmentLayout(): Int

    /**
     * Configures the design of each daughter class
     * @param savedInstanceState a [Bundle] to check the configuration changes of [Fragment]
     */
    protected abstract fun configureDesign(savedInstanceState: Bundle?)

    // -- Fragment --

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Configures the callback to the parent activity
        if (context is FragmentCallback) {
            this.mCallback = context
        }
        else {
            throw ClassCastException("$context must implement FragmentCallback")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.mRootView = inflater.inflate(this.getFragmentLayout(), container, false)

        this.configureDesign(savedInstanceState)

        return this.mRootView
    }

    // -- Sync Data --

    /**
     * Method to override to perform an action after the user's click on toolbar
     */
    open fun syncData() { /* Do nothing */}

    // -- Search Data --

    /**
     * Method to override to perform an action after the user's click on toolbar
     */
    open fun searchData(newText: String) { /* Do nothing */}
}