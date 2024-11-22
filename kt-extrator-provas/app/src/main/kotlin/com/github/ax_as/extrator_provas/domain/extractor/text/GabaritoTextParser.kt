package com.github.ax_as.extrator_provas.domain.extractor.text

import com.github.ax_as.extrator_provas.domain.extractor.processors.PipelineProcessor
import com.github.ax_as.extrator_provas.domain.model.EnumAlternativa
import com.github.ax_as.extrator_provas.domain.model.Questao


class GabaritoTextParser(
    private val pipelineProcessor: PipelineProcessor<String, List<EnumAlternativa>>
) :
    TextParser<Pair<String, List<Questao>>, List<Questao>> {

    override fun parse(p: Pair<String, List<Questao>>): List<Questao> {
        val gabarito = pipelineProcessor.process(p.first)
        val mGabarito = gabarito.mapIndexed { i, g -> Pair(i + 1, g) }.toMap()

        return p.second.map {
            val a = mGabarito[it.numero] ?: return@map it
            val respostas =
                it.respostas.map { r -> if (r.alternativa == a) r.copy(correta = true) else r }

            it.copy(respostas = respostas)
        }

    }
}