package com.github.ax_as.extrator_provas.domain.extractor

import com.github.ax_as.extrator_provas.domain.extractor.pdf.PDFExtractor
import org.junit.jupiter.api.Test
import java.nio.file.Path
import kotlin.io.path.absolutePathString

class PDFBoxReaderTest : DITestLifeCycle {

    val dataDir = "/data/files/concursos/qq/TRT-18RegiaoGO-2022-Editaln1/"
    val p1Pdf = Path.of(
        dataDir,
        "fcc-2023-trt-18-regiao-go-tecnico-judiciario-tecnologia-da-informacao-prova.pdf"
    )
    val g1Pdf = Path.of(
        dataDir,
        "fcc-2023-trt-18-regiao-go-tecnico-judiciario-tecnologia-da-informacao-gabarito.pdf"
    )

    lateinit var pdfExtractor: PDFExtractor
    @Test
    fun extractText() {
        var text = pdfExtractor.extractText(
            p1Pdf.absolutePathString()
        )
        val gabarito =
            pdfExtractor.extractText(g1Pdf.absolutePathString())

        println(gabarito)
    }
}