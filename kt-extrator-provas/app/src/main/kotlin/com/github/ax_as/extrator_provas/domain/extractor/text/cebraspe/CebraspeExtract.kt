package com.github.ax_as.extrator_provas.domain.extractor.text.cebraspe

import com.github.ax_as.extrator_provas.domain.data.BancaRepository
import com.github.ax_as.extrator_provas.domain.data.BancaRepositoryHadcoded
import com.github.ax_as.extrator_provas.domain.extractor.text.TextParser
import com.github.ax_as.extrator_provas.domain.model.Banca
import com.github.ax_as.extrator_provas.domain.model.Questao
import com.github.ax_as.extrator_provas.domain.model.Resposta
import com.github.ax_as.extrator_provas.domain.util.clear

class CebraspeExtract(val bancaRepository: BancaRepository = BancaRepositoryHadcoded()) :
    TextParser<String, List<Questao>> {
    private val regexRawQuestion =
        Regex("""^(\d{1,3}\s.*?(\.\s\n))""")
    private val regexNrEn = Regex("""^(\d{1,3})(\s.+)""")
    private val regexEnunciadoGrupo =
        Regex("""^[A-Z].*?(?=.\n\d{1,3})""")
    private val siglasBancas: List<String> =
        bancaRepository.all().map { it.sigla.lowercase() }


    override fun parse(
        text: String
    ): List<Questao> {
        var rawQuestion = regexRawQuestion.findAll(text)
        var lastIndex = 0
        val questoes = mutableListOf<Questao>()
        var enunciadoGrupo = ""
        for (q in rawQuestion) {
            val ss = text.substring(lastIndex, q.range.last())
            val grupo = regexEnunciadoGrupo.find(ss)
            grupo?.value.let {
                enunciadoGrupo = it ?: enunciadoGrupo
            }
            val questao = createQuestao(
                enunciadoGrupo.clear(),
                text.substring(q.range).clear()
            )
            questoes.add(questao)
            lastIndex = q.range.last
        }
        return questoes
    }



    private fun extractBanca(text: String): Banca {
        var lines = text.lines()
        val sigla = findKnownBanca(lines)
        return Banca(nome = sigla.replaceFirstChar { it.uppercase() }, sigla)
    }

    private fun findKnownBanca(lines: List<String>): String {
        for (line in lines) {
            val spline = line.split(" ").map { it.lowercase() }
            val sigla = siglasBancas.firstOrNull {
                spline.contains(it)
            }
            if (sigla != null) {
                return sigla
            }
        }

        return ""
    }

    private fun createQuestao(
        enunciadoGrupo: String,
        rawQuestion: String
    ): Questao {
        val numero = regexNrEn.find(rawQuestion)?.groupValues?.get(1) ?: ""
        if (numero == "") {
            return Questao()
        }
        val enunciado =
            regexNrEn.find(rawQuestion)?.groupValues?.get(2)?.clear() ?: ""
        if (enunciado == "") {
            return Questao()
        }

        return Questao(
            textoBase = enunciadoGrupo,
            numero = numero.toInt(),
            enunciado = enunciado,
            respostas = createAlternativas()
        )
    }

    private fun createAlternativas(): List<Resposta> {
        return arrayListOf(Resposta.c, Resposta.e)
    }

}