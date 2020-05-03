package com.mancel.yann.boxoffice.koin

import com.mancel.yann.boxoffice.repositories.MovieRepository
import com.mancel.yann.boxoffice.repositories.OMDbRepository
import com.mancel.yann.boxoffice.viewModels.BoxOfficeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Yann MANCEL on 29/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.koin
 */

val appModule = module {

    // Repositories
    single<MovieRepository> { OMDbRepository() }

    // ViewModel
    viewModel { BoxOfficeViewModel(get()) }
}