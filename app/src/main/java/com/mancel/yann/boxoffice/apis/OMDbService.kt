package com.mancel.yann.boxoffice.apis

import com.mancel.yann.boxoffice.models.OMDbMovie
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.apis
 */
interface OMDbService {

    // FIELDS --------------------------------------------------------------------------------------

    companion object {

        private const val BASE_URL = "https://www.omdbapi.com"

        val retrofit: Retrofit = Retrofit.Builder()
                                         .baseUrl(BASE_URL)
                                         .addConverterFactory(MoshiConverterFactory.create())
                                         .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                         .build()
    }

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Gets a [OMDbMovie] by title with a HTTP request
     * @param title         a [String] that contains the title
     * @param key           a [String] that contains your application's API key
     * @param resultType    a [String] that defines the type of result to return
     * @param dataType      a [String] that defines the data type to return
     * @param plot          a [String] that is either short or full plot
     * @return a [Single] of [OMDbMovie]
     */
    @GET("/?")
    fun getFilmByTitle(
        @Query("t") title: String,
        @Query("apikey") key: String,
        @Query("type") resultType: String,
        @Query("r") dataType: String,
        @Query("plot") plot: String
    ): Single<OMDbMovie>
}