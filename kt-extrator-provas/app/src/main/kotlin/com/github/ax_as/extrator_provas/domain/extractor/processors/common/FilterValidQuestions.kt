package com.github.ax_as.extrator_provas.domain.extractor.processors.common

import com.github.ax_as.extrator_provas.domain.extractor.processors.PipelineProcessor
import com.github.ax_as.extrator_provas.domain.model.Questao
import com.github.ax_as.extrator_provas.domain.model.Resposta
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject

private val logger = KotlinLogging.logger {}
private const val msg = ""



class FilterValidQuestions() : PipelineProcessor<List<Questao>, List<Questao>> {
    override fun process(value: List<Questao>): List<Questao> {
        return value.filter {
            isValid(it)
        }
    }

    private fun isValid(q: Questao): Boolean {
        return q.run {
            if (numero < 1) {
                logger.error { msg.format("número: $numero") }
                return@run false
            }
            if (enunciado.isBlank()) {
                logger.error { msg.format("enunciado: $enunciado") }
                return@run false
            }
            return@run true
        }
    }

    private fun validateRespostas(respostas: List<Resposta>): Boolean {
        if (respostas.isEmpty() || respostas.size < 2) {
            logger.error { "lista de respostas inválidas (size)" }
            return false
        }

        respostas.firstOrNull {
            it.texto.isEmpty()
        }?.let {
            logger.error { "lista de respostas inválidas (texto em branco)" }
            return false
        }

        val alternativasUnicas =
            respostas.distinctBy { it.alternativa }.size == respostas.size
        if (!alternativasUnicas) {
            println("(alternativas não são unicas)")
        }
        return alternativasUnicas
    }

}