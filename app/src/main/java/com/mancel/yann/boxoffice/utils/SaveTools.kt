package com.mancel.yann.boxoffice.utils

import android.content.Context

/**
 * Created by Yann MANCEL on 29/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.utils
 */
object SaveTools {

    // FIELDS --------------------------------------------------------------------------------------

    private const val SAVE_FILE_NAME = "com.mancel.yann.boxoffice.SAVE_FILE_NAME"

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Saves a [Float] thanks to SharedPreferences
     * @param context   a [Context]
     * @param key       a [String] that contains the key
     * @param value     a [Float] that contains the value
     */
    fun saveFloatIntoSharedPreferences(
        context: Context,
        key: String,
        value: Float
    ) {
        context.getSharedPreferences(SAVE_FILE_NAME, Context.MODE_PRIVATE)
               .edit()
               .putFloat(key, value)
               .apply()
    }

    /**
     * Saves a [String] thanks to SharedPreferences
     * @param context   a [Context]
     * @param key       a [String] that contains the key
     * @param value     a [String] that contains the value
     */
    fun saveStringIntoSharedPreferences(
        context: Context,
        key: String,
        value: String
    ) {
        context.getSharedPreferences(SAVE_FILE_NAME, Context.MODE_PRIVATE)
               .edit()
               .putString(key, value)
               .apply()
    }

    /**
     * Fetches a [Float] from SharedPreferences
     * @param context   a [Context]
     * @param key       a [String] that contains the key
     * @return a [Float] that contains the value
     */
    fun fetchFloatFromSharedPreferences(
        context: Context,
        key: String
    ): Float {
        return context.getSharedPreferences(SAVE_FILE_NAME, Context.MODE_PRIVATE)
                      .getFloat(key, 0.0F)
    }

    /**
     * Fetches a [String] from SharedPreferences
     * @param context   a [Context]
     * @param key       a [String] that contains the key
     * @return a [String] that contains the value
     */
    fun fetchStringFromSharedPreferences(
        context: Context,
        key: String
    ): String {
        return context.getSharedPreferences(SAVE_FILE_NAME, Context.MODE_PRIVATE)
                      .getString(key, "")!!
    }
}