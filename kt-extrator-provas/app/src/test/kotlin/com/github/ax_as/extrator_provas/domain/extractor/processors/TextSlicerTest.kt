package com.github.ax_as.extrator_provas.domain.extractor.processors

import com.github.ax_as.extrator_provas.TestHelper
import com.github.ax_as.extrator_provas.TestHelper.Companion.p1Name
import com.github.ax_as.extrator_provas.TestHelper.Companion.prefixBodyText
import com.github.ax_as.extrator_provas.TestHelper.Companion.testFilesDir
import com.github.ax_as.extrator_provas.domain.extractor.DITestLifeCycle
import com.github.ax_as.extrator_provas.domain.extractor.processors.common.TextSlicer
import com.github.ax_as.extrator_provas.domain.model.Questao
import net.bytebuddy.matcher.ElementMatchers.named
import org.junit.jupiter.api.Test

class TextSlicerTest: DITestLifeCycle {
    private lateinit var textSlicer: TextSlicer<Questao>
    @Test
    fun process() {
        val helper = TestHelper(testFilesDir)
        val result = textSlicer.process(helper.readTestFile(prefixBodyText, p1Name))
        println(result)

    }
}