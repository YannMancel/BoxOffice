package com.mancel.yann.boxoffice

import androidx.multidex.MultiDexApplication
import com.mancel.yann.boxoffice.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice
 *
 * A [MultiDexApplication] subclass.
 */
class BoxOfficeApplication : MultiDexApplication() {

    // METHODS -------------------------------------------------------------------------------------

    // -- MultiDexApplication --

    override fun onCreate() {
        super.onCreate()

        // KOIN: Dependency injection framework
        startKoin {
            androidLogger()
            androidContext(this@BoxOfficeApplication)
            modules(appModule)
        }
    }
}