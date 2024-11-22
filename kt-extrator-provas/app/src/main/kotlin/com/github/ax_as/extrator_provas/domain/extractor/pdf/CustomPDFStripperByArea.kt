package com.github.ax_as.extrator_provas.domain.extractor.pdf

import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.text.PDFTextStripperByArea
import java.awt.geom.Rectangle2D

class CustomPDFStripperByArea() :
    PDFTextStripperByArea() {
    init {
        sortByPosition = true
    }

    companion object {

        data class Region(
            val page: Int,
            val x: Float,
            val y: Float,
            val width: Float,
            val height: Float,
            val regionName: String
        )
    }

    fun extractRegion(region: Region, page: PDPage): String {
        val width = page.mediaBox.width
        val height = page.mediaBox.height

        region.let {
            addRegion(
                it.regionName,
                Rectangle2D.Double(
                    it.x.toDouble(),
                    it.y.toDouble(),
                    it.width.toDouble(),
                    it.height.toDouble()
                )
            )
            extractRegions(page)
            return getTextForRegion(it.regionName)
        }
    }
}