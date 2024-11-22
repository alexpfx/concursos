package com.github.ax_as.extrator_provas.di

import com.github.ax_as.extrator_provas.domain.extractor.pdf.CustomPDFStripper
import com.github.ax_as.extrator_provas.domain.extractor.pdf.PDFBoxExtractor
import com.github.ax_as.extrator_provas.domain.extractor.pdf.PDFExtractor
import com.github.ax_as.extrator_provas.domain.extractor.processors.PipelineProcessor
import com.github.ax_as.extrator_provas.domain.extractor.processors.common.FilterValidQuestions
import com.github.ax_as.extrator_provas.domain.extractor.processors.common.TextNormalizer
import com.github.ax_as.extrator_provas.domain.extractor.processors.common.TextSlicer
import com.github.ax_as.extrator_provas.domain.extractor.processors.common.TextToHtml
import com.github.ax_as.extrator_provas.domain.extractor.processors.mlRegex
import com.github.ax_as.extrator_provas.domain.extractor.text.ProvaTextParser
import com.github.ax_as.extrator_provas.domain.extractor.text.TextParser
import com.github.ax_as.extrator_provas.domain.model.EnumAlternativa
import com.github.ax_as.extrator_provas.domain.model.Questao
import com.github.ax_as.extrator_provas.domain.model.Resposta
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import org.apache.pdfbox.pdmodel.PDPage


@Module
class AppModule {

    @Provides
    fun json(): Json {
        return Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
    }

    @Provides
    fun provideListener(): CustomPDFStripper.Listener {
        val tmp = StringBuilder()
        var captured = false

        return object : CustomPDFStripper.Listener {
            override fun returnedText(
                currentPageNo: Int, text: String
            ): String {
                if (!captured) {
                    return text
                }
                tmp.appendLine(text)
                return ""
            }

            override fun startPage(page: Int) {
                tmp.clear()
                captured = true
            }

            override fun wasCaptured(): Boolean {
                return captured
            }

            override fun endPage(page: PDPage?): String {
                if (!captured) {
                    return ""
                }
                captured = false
                if (tmp.contains("INSTRUÇÕES")) {
                    return "\n"
                }
                if (tmp.contains("Discursiva-Redação")) {
                    return "\n"
                }
                return tmp.toString()
            }
        }
    }

    @Provides
    fun providePDFExtractor(pdfStripper: CustomPDFStripper): PDFExtractor {
        return PDFBoxExtractor(pdfStripper)
    }

    @Provides
    fun provideTextNormalizer(): Array<TextNormalizer.Config> {
        return arrayOf(TextNormalizer.Config("""\n""") {
            " "
        }, TextNormalizer.Config("""\s{3,}""") {
            "  "
        })
    }

    @Provides
    fun provideTextSlicerConfig(): TextSlicer.Config<Questao> {
        return TextSlicer.Config(
            selector = ".questao"
        ) { node ->
            val numero = node.selectFirst(".numero")?.text() ?: "-1"
            val enunciado = node.selectFirst(".enunciado")?.text() ?: ""
            val alt = node.select(".alternativas .alternativa")
            val resp = alt.map { it.text().trim() }.map {
                val rx = """\((?<letra>[ABCDE])\)\s+\S""".mlRegex()
                val mr = rx.find(it)
                val letra = mr?.groups?.get("letra")?.value ?: ""
                Resposta(
                    EnumAlternativa.findByLetra(letra), it, correta = false
                )
            }
            Questao(
                numero = numero.toInt(),
                enunciado = enunciado,
                respostas = resp
            )
        }

    }

    @Provides
    fun provideFilterValidQuestion(): FilterValidQuestions {
        return FilterValidQuestions()
    }

    @Provides
    fun provideTextParser(processor: PipelineProcessor<String, List<Questao>>): TextParser<String, List<Questao>> {
        return ProvaTextParser(processors = processor)
    }

    @Provides
    fun provideTextToHtmlConfig(): TextToHtml.Config {
        val numero = "numero"
        val enunciado = "enunciado"
        val alternativas = "alternativas"
        val questao = "questao"

        return TextToHtml.Config(

            wrapperClass = "questoes", items = listOf(
                TextToHtml.Config.Item(
                    pattern = """(?:(?<questao>\s{2}\d{1,3}\.\s\S.*?)(?=\s{2}\d{1,3}\.\s\S|\z))""".mlRegex(),
                    groupName = listOf(questao),
                ),
                TextToHtml.Config.Item(
                    pattern = """<div class=\"questao\">(?<numero>\d+)\.\s(?<enunciado>\S.+?)(?<alternativas>\([ABCDE]\)\s+\S.+?)<\/div>""".mlRegex(),
                    groupName = listOf(
                        numero,
                        enunciado,
                        alternativas,
                    ),
                ),
                TextToHtml.Config.Item(
                    pattern = """(?<alternativa>(\([ABCDE]\)).+?)(?=\([ABCDE]\)|<\/)""".mlRegex(),
                    groupName = listOf(
                        "alternativa"
                    ),
                ),
            )
        )

    }

    @Provides
    fun providePipelineProcessor(
        normalizer: TextNormalizer,
        htmlTokenizer: TextToHtml,
        textSlicer: TextSlicer<Questao>,
        validation: FilterValidQuestions
    ): PipelineProcessor<String, List<Questao>> {

        return normalizer
            .pipe(htmlTokenizer)
            .pipe(textSlicer)
            .pipe(validation)
    }


}