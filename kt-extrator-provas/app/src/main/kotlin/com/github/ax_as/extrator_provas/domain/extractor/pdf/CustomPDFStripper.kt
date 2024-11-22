package com.github.ax_as.extrator_provas.domain.extractor.pdf

import com.github.ax_as.extrator_provas.domain.util.SpecialChar
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.text.PDFTextStripper
import org.apache.pdfbox.text.TextPosition
import java.io.Writer
import javax.inject.Inject


private val logger = KotlinLogging.logger {}


class CustomPDFStripper @Inject constructor(
    var listener: Listener = object : Listener {}
) : PDFTextStripper() {

    interface Listener {
        fun startPage(page: Int) {
        }

        fun endPage(page: PDPage?): String {
            return ""
        }

        fun wasCaptured(): Boolean {
            return false
        }

        fun returnedText(currentPageNo: Int, text: String): String {
            return text
        }

    }

    private val marginBotton: Double = 812.5
    private val marginTop: Double = 27.5

    private class Stats {
        private var accFontSize = 0f
        private var cFontSize = 0
        private var maxFontSize = -1f
        private var minFontSize = -1f
        private var wordMaxFont = ""
        private var wordMinFont = ""
        fun reset() {
            accFontSize = 0f
            cFontSize = 0
            maxFontSize = 0f
            minFontSize = -1f
            wordMaxFont = ""
            wordMinFont = ""
        }

        fun avg(): Float {
            return (accFontSize / cFontSize)
        }


        fun add(word: String, fSize: Float) {
            if (fSize < 0f || fSize > 1000f) {
                return
            }
            try {
                cFontSize++
                accFontSize += fSize
                if (fSize <= minFontSize || minFontSize < 0f) {
                    wordMinFont = word
                    minFontSize = fSize
                }
                if (fSize >= maxFontSize) {
                    wordMaxFont = word
                    maxFontSize = fSize
                }
            } catch (e: NumberFormatException) {
                logger.error(e) { "Formato inválido: $word, $fSize" }
            }
        }

        override fun toString(): String {
            return String.format(
                "\n Stats(accFontSize=%2.2f\n cFontSize=%d\n minFontSize=%2.2f\n maxFontSize=%.2f\n wordMaxFont='%s'\n wordMinFont='%s'2)",
                accFontSize,
                cFontSize,
                minFontSize,
                maxFontSize,
                wordMaxFont,
                wordMinFont,
            )
        }


    }

    private val docStats = Stats()

    companion object {
        val unicodeMap = mapOf(
            "\u0010" to "\u2014",

            )
        val superScriptMap = mapOf(
            "a" to SpecialChar.SuperScriptA.unicode,
            "o" to SpecialChar.SuperScriptO.unicode
        )
    }

    private var lastY = 0f
    private var lastX = 0f
    private var lastFontSize = 0f
    private var lastPage = 0

    private var isFooterHeader = false


    init {
        sortByPosition = true/* pageStart = PDFMarks.PageStart.unicode
         pageEnd = PDFMarks.PageEnd.unicode
         paragraphEnd = PDFMarks.ParagraphEnd.unicode
         paragraphStart = PDFMarks.ParagraphStart.unicode
         articleStart = PDFMarks.ArticleStart.unicode
         articleEnd = PDFMarks.ArticleEnd.unicode*/

    }


    override fun writeText(doc: PDDocument?, outputStream: Writer?) {
        docStats.reset()
        super.writeText(doc, outputStream)
        logger.debug {
            "Estatísticas do PDF: \n " + "$docStats \n" + "Tamanho medio da fonte: ${
                String.format(
                    "%.2f", docStats.avg()
                )
            }"
        }
    }


    override fun writeString(
        text: String?, textPositions: MutableList<TextPosition>?
    ) {

        val s = buildString {
            if (textPositions == null) {
                return@buildString
            }
            for (pos: TextPosition in textPositions) {
                if (text == null) {
                    return
                }
                if (pos.yDirAdj != lastY) {
                    if (pos.xDirAdj < 21.20) {
//                        append("|")
                    } else if (pos.xDirAdj < 35.36) {
//                        append("|")
                    }
                }


                isFooterHeader =
                    (pos.yDirAdj <= marginTop || pos.yDirAdj >= marginBotton)
                if (isFooterHeader && lastY != pos.yDirAdj) {
                    append(PDFMarks.PageMargin.unicode)
                } else if (!isFooterHeader) {
                    append(checkReplaceUnicode(pos.unicode))
                }
                lastY = pos.yDirAdj
                lastX = pos.xDirAdj
                lastFontSize = pos.fontSizeInPt
                lastPage = currentPageNo
            }
        }

        super.writeString(s, textPositions)
    }

    override fun startPage(page: PDPage?) {
        listener.startPage(currentPageNo)
        super.startPage(page)
    }

    override fun endPage(page: PDPage?) {
        if (!listener.wasCaptured()) {
            return
        }
        val text = listener.endPage(page)
        output?.appendLine(text)
        super.endPage(page)
    }

    override fun writeString(text: String?) {
        text?.let {
            val t = listener.returnedText(currentPageNo, text)
            super.writeString(t)
        }
    }


    private fun isSuperScript(u: String) =
        (u[0].isDigit() || u[0] == 'a' || u[0] == 'o')

    private fun convertoSuperScript(unicode: String): String {
        if (unicode[0].isDigit()) {
            return SpecialChar.getSuperScriptNumber(unicode[0].digitToInt())
        }
        return superScriptMap[unicode] ?: unicode
    }

    private fun checkReplaceUnicode(unicode: String): String {
        return unicodeMap[unicode] ?: unicode
    }

}