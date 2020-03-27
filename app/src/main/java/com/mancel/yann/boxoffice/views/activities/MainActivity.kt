package com.mancel.yann.boxoffice.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mancel.yann.boxoffice.R

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.activities
 *
 * A [AppCompatActivity] subclass.
 */
class MainActivity : AppCompatActivity() {

    // METHODS -------------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}