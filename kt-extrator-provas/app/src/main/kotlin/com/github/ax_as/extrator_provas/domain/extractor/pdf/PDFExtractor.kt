package com.github.ax_as.extrator_provas.domain.extractor.pdf

interface PDFExtractor {
    fun extractText(
        filepath: String,
        pageRange: Pair<Int, Int>? = null,
        htmlTags: Boolean = false,
        clear: Boolean = true,
        regions: List<CustomPDFStripperByArea.Companion.Region>? = null
    ): String
}