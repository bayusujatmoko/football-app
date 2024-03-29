package match.football.submission2.submission2.app.util

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat

class UtilsKtTest {

    @Test
    fun toDateStringTest() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val date = dateFormat.parse("02/28/2018")
        assertEquals("Wed, 28 Feb 2018", toDateString(date))
    }
}