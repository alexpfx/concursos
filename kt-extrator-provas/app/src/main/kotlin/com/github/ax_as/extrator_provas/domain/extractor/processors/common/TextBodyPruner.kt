package com.github.ax_as.extrator_provas.domain.extractor.processors.common

import com.github.ax_as.extrator_provas.domain.extractor.processors.BasePipelineProcessor
import com.github.ax_as.extrator_provas.domain.extractor.processors.mlRegex
import javax.inject.Inject
import kotlin.math.max


class TextBodyPruner @Inject constructor(
    private val config: Config
) : BasePipelineProcessor<String, String>() {

    class Config(
        val start: Regex,
        val end: Regex,
        val cutAfter: Regex
    )


    override fun doProcess(value: String): String {
        return processStr(value)
    }

    private fun processStr(input: String): String {
        val lastMatch = config.end.findAll(input).toList()

        val startMatch: MatchResult =
            config.start.find(input) ?: return input

        if (lastMatch.isEmpty()) {
            return input.substring(startMatch.range.last + 1)
        }

        val cutAfterMin = lastMatch.last().range.last

        val mrCutter = config.cutAfter.find(input, cutAfterMin)

        val cutIndex = mrCutter?.range?.first ?: 0

        return input.substring(
            startMatch.range.last + 1,
            max(cutIndex, cutAfterMin)
        )
    }
}