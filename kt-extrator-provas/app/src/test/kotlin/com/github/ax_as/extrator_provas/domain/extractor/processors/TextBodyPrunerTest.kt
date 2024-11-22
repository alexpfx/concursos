package com.github.ax_as.extrator_provas.domain.extractor.processors

import com.github.ax_as.extrator_provas.TestHelper
import com.github.ax_as.extrator_provas.domain.extractor.DITestLifeCycle
import com.github.ax_as.extrator_provas.domain.extractor.processors.common.TextBodyPruner
import net.bytebuddy.matcher.ElementMatchers.named
import org.junit.jupiter.api.Test

class TextBodyPrunerTest : DITestLifeCycle {

    private lateinit var textBodyPruner: TextBodyPruner

    @Test
    fun doProcess_original() {
        val testHelper = TestHelper(TestHelper.testFilesDir)
        val original =
            testHelper.readTestFile(TestHelper.prefixCleanerText, TestHelper.p1Name)
        println("Texto Original ${original.length}")
        print(original)
        println("Texto Original ${original.length}")

    }

    @Test
    fun doProcess_after() {
        val testHelper = TestHelper(TestHelper.testFilesDir)
        val original =
            testHelper.readTestFile(TestHelper.prefixCleanerText, TestHelper.p1Name)
        val res = textBodyPruner.doProcess(original)
        println("Texto Limpo ${res.length}")
        print(res)
        println("Texto Limpo ${res.length}")

    }
}