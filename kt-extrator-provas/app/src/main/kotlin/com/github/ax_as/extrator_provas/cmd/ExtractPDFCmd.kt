package com.github.ax_as.extrator_provas.cmd

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.arguments.validate
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import com.github.ax_as.extrator_provas.domain.extractor.pdf.CustomPDFStripper
import com.github.ax_as.extrator_provas.domain.extractor.pdf.PDFBoxExtractor
import com.github.ax_as.extrator_provas.domain.extractor.text.cebraspe.CebraspeExtract
import com.github.ax_as.extrator_provas.domain.model.Prova
import kotlinx.serialization.json.Json
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.name
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.writeText

class ExtractPDFCmd() : CliktCommand(name = "extract-pdf") {

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }
    private lateinit var pdfFiles: List<Path>
    private val files: List<Path> by argument(name = "files").path().multiple()
        .validate {
            val f = it.filter { file ->
                file.name.lowercase().endsWith(".pdf")
            }
            pdfFiles = f
        }
    private val writeJson by option(names = arrayOf("--json")).flag()
    private val banca by option().default("fcc")

    override fun run() {
        val extractor = CebraspeExtract()

        val pdfReader =
            PDFBoxExtractor(textStripper = CustomPDFStripper())

        for (pdfFile in pdfFiles) {
            val parent = pdfFile.parent
            parent?.let {
                val txtFile = Path.of(
                    parent.absolutePathString(),
                    pdfFile.nameWithoutExtension + ".TXT"
                )

                val text = pdfReader.extractText(
                    pdfFile.absolutePathString(), clear = false
                )
                txtFile.writeText(text)

                if (writeJson) {
                    val qs = extractor.parse(text)
                    val prova = Prova(qs)
                    writeJson(parent, pdfFile, prova)
                }
            }
        }

    }

    private fun writeJson(
        parent: Path, pdfFile: Path, prova: Prova
    ) {
        val jsonFile = Path.of(
            parent.absolutePathString(), pdfFile.nameWithoutExtension + ".json"
        )
        val jsonStr = json.encodeToString(Prova.serializer(), prova)
        jsonFile.writeText(jsonStr)
    }
}