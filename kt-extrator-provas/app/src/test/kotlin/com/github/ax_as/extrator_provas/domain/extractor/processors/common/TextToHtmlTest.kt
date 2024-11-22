package com.github.ax_as.extrator_provas.domain.extractor.processors.common

import com.github.ax_as.extrator_provas.TestHelper
import com.github.ax_as.extrator_provas.domain.extractor.DITestLifeCycle
import org.junit.jupiter.api.Test

class TextToHtmlTest : DITestLifeCycle {

    private lateinit var textToHtml: TextToHtml
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
            testHelper.readTestFile(TestHelper.prefixCleanerText, TestHelper.p1Name)
        val res = textToHtml.doProcess(original)
        println("Texto Limpo ${res.length}")
        print(res)
        println("Texto Limpo ${res.length}")


//        println(cfg)
//        println(cfg2)

    }


}
