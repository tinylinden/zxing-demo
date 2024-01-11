package pl.tinylinden.zxing

import com.google.zxing.BinaryBitmap
import com.google.zxing.Result
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeReader
import org.apache.pdfbox.Loader
import org.apache.pdfbox.io.RandomAccessReadBuffer
import org.apache.pdfbox.pdmodel.PDResources
import org.apache.pdfbox.pdmodel.graphics.image.PDImage
import java.awt.image.BufferedImage
import java.io.InputStream

internal fun decodeQrCodes(input: InputStream): Set<String> =
    images(input)
        .mapNotNull { textOrNull(it) }
        .toSet()

private fun images(input: InputStream): List<BufferedImage> =
    input.use {
        Loader.loadPDF(RandomAccessReadBuffer(input))
            .pages
            .flatMap { images(it.resources) }
    }

private fun images(resources: PDResources): List<BufferedImage> =
    resources.xObjectNames
        .map { resources.getXObject(it) }
        .filterIsInstance<PDImage>()
        .map { it.image }

private fun textOrNull(image: BufferedImage): String? =
    try {
        decode(image).text
    } catch (ex: Exception) {
        null
    }

private fun decode(image: BufferedImage): Result =
    QRCodeReader().decode(
        BinaryBitmap(
            HybridBinarizer(
                BufferedImageLuminanceSource(image)
            )
        )
    )