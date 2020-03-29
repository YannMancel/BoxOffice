package com.mancel.yann.boxoffice

import com.mancel.yann.boxoffice.utils.ActorTools
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Yann MANCEL on 29/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice
 *
 * Test on [ActorTools].
 */
class ActorToolsTest {

    // METHODS -------------------------------------------------------------------------------------

    @Test
    fun getActors_isCorrect() {
        val casting = "  A, B, C    "
        val actors = ActorTools.getActors(casting)

        assertEquals(listOf("A", "B", "C"), actors)
    }

    @Test
    fun getActors_isEmpty() {
        val casting = "     "
        val actors = ActorTools.getActors(casting)

        assertEquals(emptyList<String>(), actors)
    }
}