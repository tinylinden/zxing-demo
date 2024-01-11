package pl.tinylinden.zxing

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PingTest {

    @Test
    fun `should answer pong to every ping`() {
        assertThat(ping()).isEqualTo("pong")
    }
}