package com.github.ax_as.extrator_provas.domain.extractor.processors.common

import com.github.ax_as.extrator_provas.domain.extractor.processors.BasePipelineProcessor
import com.github.ax_as.extrator_provas.domain.extractor.processors.mlDotAllRegex
import javax.inject.Inject


class TextNormalizer @Inject constructor(
    private vararg val config: Config
) :
    BasePipelineProcessor<String, String>() {


    data class Config @Inject constructor(
        val p: String,
        val transform: (MatchResult) -> CharSequence
    )

    companion object {
        operator fun invoke(vararg config: Config): TextNormalizer {
            return TextNormalizer(*config)
        }
    }

    override fun doProcess(input: String): String {
        var output = input
        config.forEach { cfg ->
            output = cfg.p.mlDotAllRegex().replace(output, cfg.transform)
        }
        return output
    }

}