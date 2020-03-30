package com.mancel.yann.boxoffice.utils

/**
 * Created by Yann MANCEL on 29/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.utils
 */
object OMDbTools {

    /**
     * Gets data
     * @param data a [String] that contains data
     * @return a [List] of [String]
     */
    fun getData(data: String): List<String> {
        // No data
        if (data.trim().isEmpty()) return emptyList()

        return data.trim().split(", ")
    }
}