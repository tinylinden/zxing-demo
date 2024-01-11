package pl.tinylinden.zxing

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.InputStream
import java.util.stream.Stream

internal class QRCodesDecoderTest {

    @ParameterizedTest
    @MethodSource("pdfFiles")
    fun `should decode text from qr-codes`(pdf: InputStream, expected: Set<String>) {
        // when
        val actual = decodeQrCodes(pdf)

        // expect failure
        assertThat(actual).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        fun pdfFiles(): Stream<Arguments> = Stream.of(
            arguments(
                inputStream("/generated.pdf"),
                setOf(
                    "86ac4b0a-30af-42ce-8770-015c7118cc4b:3",
                    "93d11fa2-5645-4c71-8199-783e95884a2f:2",
                )
            ),
            arguments(
                inputStream("/scanned-2.pdf"),
                setOf(
                    "93d11fa2-5645-4c71-8199-783e95884a2f:2",
                )
            ),
            arguments(
                inputStream("/scanned-3.pdf"),
                setOf(
                    "86ac4b0a-30af-42ce-8770-015c7118cc4b:3",
                )
            ),
        )

        @JvmStatic
        private fun inputStream(location: String): InputStream =
            Companion::class.java.getResource(location)?.openStream()!!
    }
}