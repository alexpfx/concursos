package com.github.ax_as.extrator_provas.domain.extractor.fcc

import com.github.ax_as.extrator_provas.TestHelper
import com.github.ax_as.extrator_provas.TestHelper.Companion.g1TxtOriginal
import com.github.ax_as.extrator_provas.TestHelper.Companion.p1TxtOriginal
import com.github.ax_as.extrator_provas.domain.extractor.text.TextParser
import com.github.ax_as.extrator_provas.domain.extractor.text.GabaritoTextParser
import com.github.ax_as.extrator_provas.domain.extractor.text.ProvaTextParser
import com.github.ax_as.extrator_provas.domain.model.Questao
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FCCExtractorTest  {


    private lateinit var fccProvaParser: TextParser<String, List<Questao>>
    private lateinit var fccGabaritoParser: TextParser<Pair<String, List<Questao>>, List<Questao>>

    @Test
    fun extractQuestoes() {
        val text = TestHelper.readFile(p1TxtOriginal)
        val questions = fccProvaParser.parse(text)
        assertEquals(60, questions.size)
    }

    @Test
    fun extractGabarito() {
        val prova = TestHelper.readFile(p1TxtOriginal)
        val text = TestHelper.readFile(g1TxtOriginal)
        val questoes = fccProvaParser.parse(prova)

        val questoesComGabarito =
            fccGabaritoParser.parse(Pair(text, questoes))
        assertEquals(60, questoesComGabarito.size)
        assertEquals(60,
            questoesComGabarito.count { it.respostas.any() { r -> r.correta } })
    }


}