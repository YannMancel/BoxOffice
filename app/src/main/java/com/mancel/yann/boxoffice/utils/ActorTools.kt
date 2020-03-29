package com.mancel.yann.boxoffice.utils

/**
 * Created by Yann MANCEL on 29/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.utils
 */
object ActorTools {

    /**
     * Gets actors
     * @param casting a [String] that contains the actors
     * @return a [List] of [String]
     */
    fun getActors(casting: String): List<String> {
        // No actor
        if (casting.trim().isEmpty()) return emptyList()

        return casting.trim().split(", ")
    }
}