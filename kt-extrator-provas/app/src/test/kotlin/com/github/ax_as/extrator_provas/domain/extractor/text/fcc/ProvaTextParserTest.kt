package com.github.ax_as.extrator_provas.domain.extractor.text.fcc

import com.github.ax_as.extrator_provas.TestHelper
import com.github.ax_as.extrator_provas.domain.extractor.DITestLifeCycle
import com.github.ax_as.extrator_provas.domain.extractor.text.ProvaTextParser
import com.github.ax_as.extrator_provas.domain.model.EnumAlternativa
import com.github.ax_as.extrator_provas.domain.model.Questao
import kotlinx.html.TH
import net.bytebuddy.matcher.ElementMatchers.named
import org.junit.jupiter.api.Test


class ProvaTextParserTest : DITestLifeCycle {

    lateinit var provaPDFTextParser: ProvaTextParser<Questao>


    lateinit var gabaritoTextParser: ProvaTextParser<EnumAlternativa>

    @Test
    fun parse() {
        val testHelper = TestHelper(TestHelper.testFilesDir)
        val original =
            testHelper.readTestFile(
                TestHelper.prefixCleanerText,
                TestHelper.p1Name
            )
        val gabarito = testHelper.readTestFile(TestHelper.prefixRawText, TestHelper.gab1Name)

//        val questaos = provaPDFTextParser.parse(original)
        val gabaritos = gabaritoTextParser.parse(gabarito)
//        println(questaos)
        println(gabaritos)
    }
}