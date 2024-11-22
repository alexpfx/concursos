//package com.github.ax_as.extrator_provas.domain.extractor.pdf
//
//import org.apache.pdfbox.Loader
//import technology.tabula.ObjectExtractor
//import technology.tabula.extractors.BasicExtractionAlgorithm
//import java.io.File
//
//class TabulaPDFTextExtractor : PDFTextExtractor {
//
//    private fun tabulate(filepath: String): String {
//        val pdfFile = File(filepath)
//        val sb = StringBuilder()
//        Loader.loadPDF(pdfFile).use {
//            val sea = BasicExtractionAlgorithm()
//            val extractor = ObjectExtractor(it).extract()
//
//            while (extractor.hasNext()) {
//                val page = extractor.next()
//                try {
//                    val tables = sea.extract(page)
//                    for (table in tables) {
//                        val rows = table.rows
//                        for (cell in rows) {
//                            for (content in cell) {
//                                sb.append(content.getText())
//                            }
//                            sb.appendLine()
//                        }
//                    }
//                } catch (e: Exception) {
//                    println(e)
//                }
//            }
//        }
//
//
//        return sb.toString()
//    }
//
//    override fun extractText(
//        filepath: String,
//        pageRange: Pair<Int, Int>?,
//        htmlTags: Boolean,
//        clear: Boolean,
//        regions: List<CustomPDFStripperByArea.Companion.Region>?
//    ): String {
//        TODO("Not yet implemented")
//    }
//
//}