package com.github.ax_as.extrator_provas.domain.extractor.processors

import com.github.ax_as.extrator_provas.TestHelper
import com.github.ax_as.extrator_provas.domain.extractor.DITestLifeCycle
import com.github.ax_as.extrator_provas.domain.extractor.processors.common.TextNormalizer
import com.github.ax_as.extrator_provas.domain.extractor.text.Patterns
import com.github.ax_as.extrator_provas.domain.extractor.text.TextPatterns
import com.github.ax_as.extrator_provas.domain.extractor.text.build
import com.github.ax_as.extrator_provas.domain.extractor.text.plus
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.output
import kotlinx.html.stream.createHTML
import org.junit.jupiter.api.Test

class TextNormalizerTest : DITestLifeCycle {
    private lateinit var textNormalizer: TextNormalizer
    @Test
    fun doProcess_original() {
        val testHelper = TestHelper(TestHelper.testFilesDir)
        val original =
            testHelper.readTestFile(TestHelper.prefixRawText, TestHelper.p1Name)
        println("Texto Original ${original.length}")
        print(original)
        println("Texto Original ${original.length}")

    }

    @Test
    fun doProcess_after() {
        val testHelper = TestHelper(TestHelper.testFilesDir)
        val original =
            testHelper.readTestFile(TestHelper.prefixRawText, TestHelper.p1Name)
        val res = textNormalizer.doProcess(original)
        println("Texto Limpo ${res.length}")
        print(res)
        println("Texto Limpo ${res.length}")

        val html = createHTML(prettyPrint = true).body {
            div (classes = "questao"){
                output() {
                    +"output"
                }
            }
        }

        println(html)
        val cfg = Patterns(TextPatterns.numeroQuestao).plus(TextPatterns.alternativa).build()
        val cfg2 = Patterns(TextPatterns.numeroQuestao).plus(TextPatterns.startBody, prefix = "xpto").build()

//        println(cfg)
//        println(cfg2)

    }
}