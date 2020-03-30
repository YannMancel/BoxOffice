package com.mancel.yann.boxoffice

import com.mancel.yann.boxoffice.utils.OMDbTools
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Yann MANCEL on 29/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice
 *
 * Test on [OMDbTools].
 */
class OMDbToolsTest {

    // METHODS -------------------------------------------------------------------------------------

    @Test
    fun getData_isCorrect() {
        val data = "  A, B, C    "
        val dataList = OMDbTools.getData(data)

        assertEquals(listOf("A", "B", "C"), dataList)
    }

    @Test
    fun getData_isEmpty() {
        val data = "     "
        val dataList = OMDbTools.getData(data)

        assertEquals(emptyList<String>(), dataList)
    }
}