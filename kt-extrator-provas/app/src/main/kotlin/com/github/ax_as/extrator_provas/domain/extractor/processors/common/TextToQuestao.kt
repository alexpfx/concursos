package com.github.ax_as.extrator_provas.domain.extractor.processors.common

import com.github.ax_as.extrator_provas.domain.extractor.processors.PipelineProcessor
import com.github.ax_as.extrator_provas.domain.extractor.processors.MatchReplacerConverter
import com.github.ax_as.extrator_provas.domain.model.Questao
import com.github.ax_as.extrator_provas.domain.model.Resposta


class TextToQuestao(
    private val config: Config
) : PipelineProcessor<List<String>, List<Questao>> {

    class Config(
        val numeros: MatchReplacerConverter<Int>,
        val enunciados: MatchReplacerConverter<String>,
        val respostas: MatchReplacerConverter<List<Resposta>>
    )

    override fun process(input: List<String>): List<Questao> {

        return processList(input)
    }

    private fun <T> convert(qpp: MatchReplacerConverter<T>, input: String): T? {
        return qpp.first.find(input)?.run {
            qpp.second(this)
        }
    }

    private fun processList(inputs: List<String>): List<Questao> {
        val res = mutableListOf<Questao>()
        for (input in inputs) {
            val numero: Int? = convert(config.numeros, input)
            val enunciado: String? = convert(config.enunciados, input)

            val rxAlternativas: List<Resposta>? = convert(config.respostas, input)
            res.add(
                Questao().copy(
                    numero = numero ?: 0,
                    enunciado = enunciado ?: "",
                    respostas = rxAlternativas ?: emptyList()
                )
            )
        }
        return res
    }

}