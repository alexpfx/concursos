package com.github.ax_as.extrator_provas.domain.extractor.processors.common

import com.github.ax_as.extrator_provas.TestHelper
import com.github.ax_as.extrator_provas.domain.extractor.DITestLifeCycle

import org.junit.jupiter.api.Test

class TextToQuestaoTest : DITestLifeCycle {
    lateinit var textToQuestao: TextToQuestao
    @Test
    fun doProcess_after() {
        val testHelper = TestHelper(TestHelper.testFilesDir)
        val list = testHelper.readTestFiles(
            TestHelper.prefixSliceText + "-*.",
            TestHelper.p1Name
        )
        val res = textToQuestao.process(list)
        for (re in res) {
            println(re.enunciado)
        }
    }
}