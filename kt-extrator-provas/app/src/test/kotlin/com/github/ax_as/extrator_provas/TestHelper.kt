package com.github.ax_as.extrator_provas

import com.github.ax_as.extrator_provas.domain.extractor.pdf.PDFExtractor
import com.github.ax_as.extrator_provas.domain.extractor.processors.PipelineProcessor
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readText
import kotlin.io.path.writeText


class TestHelper(val testDir: String) {

    fun generateTextFiles(
        pdfExtractor: PDFExtractor, vararg pdfFiles: String
    ) {
        val target = Path.of(testDir)

        target.toFile().mkdirs()

        for (pdfPathStr in pdfFiles) {
            val text = pdfExtractor.extractText(pdfPathStr)
            writeText(target, prefixRawText, pdfPathStr, text)
        }


    }

    fun readTestFiles(globFilter: String, name: String): List<String> {
        val files =
            Path.of(testDir).listDirectoryEntries(glob = "$globFilter$name.txt")


        return files.map {
            readFile(it)
        }
    }

    fun readTestFile(prefix: String, name: String): String {
        val testFile = Path.of(testDir, "$prefix.$name.txt")
        return readFile(testFile)
    }

    private fun listener(
        targetDir: Path,
        prefix: String,
        filePathStr: String,
    ): PipelineProcessor.Listener<String, String> =

        object : PipelineProcessor.Listener<String, String> {
            override fun call(input: String, output: String) {
                println("chamou da classe filha $prefix")
                writeText(targetDir, prefix, filePathStr, output)
            }
        }

    private fun listenerStrList(
        testDir: Path,
        prefix: String,
        filePathStr: String,
    ): PipelineProcessor.Listener<String, List<String>> =
        object : PipelineProcessor.Listener<String, List<String>> {
            override fun call(input: String, output: List<String>) {
                output.forEachIndexed { i, s ->
                    println("chamou da classe filha $prefix")
                    writeText(testDir, "$prefix-$i", filePathStr, s)
                }

            }
        }

    private fun writeText(
        targetDir: Path, prefix: String, filePathStr: String, text: String
    ) {
        val f = Path.of(filePathStr)
        val name = f.nameWithoutExtension
        val targetFile =
            Path.of(targetDir.absolutePathString(), "$prefix.$name.txt")
        targetFile.writeText(text)


    }

    companion object {
        const val prefixRawText = "text"

        const val prefixCleanerText = "cleaner"
        const val prefixHtmlText = "html"

        const val prefixSliceText = "slice"

        const val prefixBodyText = "body"

        const val testFilesDir = "/data/files/concursos/testFiles/"

        const val dataDir =
            "/data/files/concursos/qq/TRT-18RegiaoGO-2022-Editaln1/"

        const val p1Name =
            "fcc-2023-trt-18-regiao-go-tecnico-judiciario-tecnologia-da-informacao-prova"

        val p1TxtOriginal = Path.of(
            dataDir, "$p1Name.txt"
        )
        val p1PdfOriginal = Path.of(
            dataDir, "$p1Name.pdf"
        )
        const val gab1Name =
            "fcc-2023-trt-18-regiao-go-tecnico-judiciario-tecnologia-da-informacao-gabarito"

        val g1PdfOriginal = Path.of(
            dataDir, "$gab1Name.pdf"
        )

        val g1TxtOriginal = Path.of(
            dataDir, "$gab1Name.txt"
        )


        fun readFile(filePath: Path): String {
            return filePath.readText()
        }


    }

}

