package com.github.ax_as.extrator_provas.cmd

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ax_as.extrator_provas.AppCtx
import com.github.ax_as.extrator_provas.domain.extractor.pdf.PDFExtractor
import com.github.ax_as.extrator_provas.domain.extractor.text.TextParser
import com.github.ax_as.extrator_provas.domain.metadata.MetaDataLoader
import com.github.ax_as.extrator_provas.domain.model.Prova
import com.github.ax_as.extrator_provas.domain.model.Questao
import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.File
import java.nio.file.Path
import javax.inject.Inject
import kotlin.io.path.exists
import kotlin.io.path.name
import kotlin.io.path.notExists
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.time.TimeSource

private val logger = KotlinLogging.logger {}

class ExtractCmd() : CliktCommand(
    name = "extract",
    help = """A partir do diretório de download do sistema e 
do nome da banca, informados como parâmetro, faz uma varredura em busca dos metadados
com informações de cada concurso. Para cada arquivo de metadado, obtém a lista
de arquivos e para cada arquivo verifica se o texto do PDF já foi extraido. 
Caso não tenha sido extraido, faz a extração e salva o texto no mesmo diretório
e atualiza o arquivo de metadados"""
) {

    @Inject
    lateinit var metaDataLoader: MetaDataLoader

    @Inject
    lateinit var textExtractor: PDFExtractor

    @Inject
    lateinit var textParser: TextParser<String, List<Questao>>


    private val overwrite by option(names = arrayOf("-w")).flag()
        .help("Caso o arquivo a ser gerado já exista, ele será sobreescrito.")
    private val genTextFile by option(names = arrayOf("-txt")).flag()
        .help("Converte o arquivo de PDF para um arquivo de texto")
    private val genJsonFile by option(names = arrayOf("-json")).flag()
        .help("Extrair os dados da prova do arquivo de texto, caso exista, e grava em formato Json")

    private val filter: String by argument().default("")
    private val timeSource = TimeSource.Monotonic
    private var timeMark = timeSource.markNow()
    private var pdfProcessed = 0
    private val config by requireObject<Map<String, Any>>()
    override fun run() {
        val dataDir = config["dataDir"] as Path
        val qq = config["servico"] as String
        val appCtx = AppCtx(dataDir, qq, "appId")
        timeMark = timeSource.markNow()

        val mdDir = appCtx.openMetadataDir()
        val concursoMetadataFiles =
            metaDataLoader.loadAllFiles(mdDir)

        logger.debug { "encontrou ${concursoMetadataFiles.size} arquivos de metadados" }

        for (mdf: File in concursoMetadataFiles) {
            val metadata = metaDataLoader.load(mdf)
            val chave = metadata.chave
            logger.info { "processando arquivos de [${chave}]" }
            val concursoPath = Path.of(mdf.parent, chave)
            if (concursoPath.notExists()) {
                logger.error { "diretório não existe [$concursoPath]" }
                continue
            }
            val concursoDir = concursoPath.toFile()

            val pdfFiles = concursoDir.listFiles { f ->
                f.name.endsWith(
                    ".pdf",
                    ignoreCase = true
                )
            }?.filter {
                if (filter == "") true else it.name.contains(
                    filter
                )
            } ?: continue

            for (pdfFile in pdfFiles) {
                val txtFileName = pdfFile.nameWithoutExtension + ".txt"
                val txtFilePath = Path.of(pdfFile.parent, txtFileName)
                val jsonFileName = pdfFile.nameWithoutExtension + ".json"
                val jsonFilePath = Path.of(pdfFile.parent, jsonFileName)

                if (genTextFile) {
                    genTextFile(pdfFile, txtFilePath)
                    pdfProcessed++
                }
                if (genJsonFile) {
                    genJsonFile(txtFilePath, jsonFilePath)
                }
            }
        }

        logger.debug {
            val elapsed = timeMark.elapsedNow()
            "$pdfProcessed pdfs processados em $elapsed [tempo médio: ${
                elapsed.div(
                    pdfProcessed
                )
            }]"
        }
    }

    private fun genJsonFile(txtFilePath: Path, jsonFilePath: Path) {
        if (txtFilePath.notExists()) {
            return
        }
        if (jsonFilePath.exists() && !overwrite) {
            return
        }
        logger.debug { "extraindo [${txtFilePath.name}] -> [${jsonFilePath.name}]" }
        val text = txtFilePath.readText()
        val qs = textParser.parse(text)
        val prova = Prova(qs)

    }

    private fun genTextFile(pdfFile: File, txtFilePath: Path) {
        if (txtFilePath.exists() && !overwrite) {
            return
        }
        val pdfName = pdfFile.name
        val txtName = txtFilePath.name
        logger.debug { "extraindo [$pdfName] -> [${txtName}]" }
        val text = extractFromPdf(pdfFile)
        if (text.isBlank()) {
            return
        }
        txtFilePath.writeText(text)
        logger.debug { "arquivo texto gerado [$txtName]" }
    }

    private fun extractFromPdf(pdfFile: File): String {
        return try {
            textExtractor.extractText(
                pdfFile.absolutePath, clear = false
            )
        } catch (e: Exception) {
            logger.error(e) { "não foi possível extrair pdf [${pdfFile.name}]" }
            return ""
        }
    }
}