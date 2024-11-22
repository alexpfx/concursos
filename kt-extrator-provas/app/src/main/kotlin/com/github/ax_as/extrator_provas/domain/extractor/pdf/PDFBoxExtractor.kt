package com.github.ax_as.extrator_provas.domain.extractor.pdf

//import org.apache.pdfbox.Loader
import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File
import javax.inject.Inject
import kotlin.math.min


class PDFBoxExtractor @Inject constructor(
    val textStripper: PDFTextStripper,
) :
    PDFExtractor {
    companion object {

    }

    override fun extractText(
        filepath: String,
        pageRange: Pair<Int, Int>?,
        htmlTags: Boolean,
        clear: Boolean,
        regions: List<CustomPDFStripperByArea.Companion.Region>?
    ): String {
        val pdfFile = File(filepath)
        val text: String
        // sincronizar

        Loader.loadPDF(pdfFile).use { doc ->
            text = buildString {

                if (textStripper is CustomPDFStripperByArea) {
                    regions?.forEach {
                        append(
                            textStripper.extractRegion(
                                it,
                                doc.getPage(it.page)
                            )
                        )
                    }
                } else {
                    append(textStripper.getText(doc))
                }
            }
        }
        if (clear) {
            return clearText(text)
        }
        return text
    }

    private fun newClear(text: String): String {
        return text
    }


    private fun clearText(rawText: String): String {
        val lineWithOnlyNumber = Regex(
            "(?<=^|\\n)\\s*(\\d+)\\s*(?=\$|\\n)\n", setOf(RegexOption.MULTILINE)
        )

        var t = rawText.replace(lineWithOnlyNumber, "").lines().filter {
            it.isNotBlank()
        }.joinToString("\n").replace(lineWithOnlyNumber, "")

        val wc = ""

        t.forEach {


        }


//        return joinBrokenWords(t)

        return t

    }

    private fun getRange(
        pageRange: Pair<Int, Int>?, it: PDDocument
    ) = when (pageRange) {
        null -> Pair(1, it.numberOfPages)
        else -> Pair(
            pageRange.first, min(pageRange.second, it.numberOfPages)

        )
    }


}