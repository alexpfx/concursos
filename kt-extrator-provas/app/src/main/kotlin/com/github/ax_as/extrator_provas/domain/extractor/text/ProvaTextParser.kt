package com.github.ax_as.extrator_provas.domain.extractor.text

import com.github.ax_as.extrator_provas.domain.extractor.processors.PipelineProcessor
import com.github.ax_as.extrator_provas.domain.model.Questao
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import kotlin.time.TimeSource

private val logger = KotlinLogging.logger {}

class ProvaTextParser<T> @Inject constructor(
    private val processors: PipelineProcessor<String, List<T>>,
) : TextParser<String, List<T>> {
    private val timeSource = TimeSource.Monotonic
    private var timeMark = TimeSource.Monotonic.markNow()
    override fun parse(
        text: String
    ): List<T> {
        timeMark = TimeSource.Monotonic.markNow()
        try {
            return processors.process(text)
        } finally {
            logger.debug { "texto processado em ${timeMark.elapsedNow()}" }
        }

    }

}