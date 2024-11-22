package com.github.ax_as.extrator_provas.services

import com.github.ax_as.extrator_provas.AppCtx
import com.github.ax_as.extrator_provas.api.ProvaRest
import com.github.ax_as.extrator_provas.domain.extractor.text.TextParser
import com.github.ax_as.extrator_provas.domain.extractor.text.cebraspe.CebraspeGabaritoExtractor
import com.github.ax_as.extrator_provas.domain.model.MetaData
import com.github.ax_as.extrator_provas.domain.model.MetaProva
import com.github.ax_as.extrator_provas.domain.model.Prova
import com.github.ax_as.extrator_provas.domain.model.Questao

class ProvaServiceImpl(
    private val appCtx: AppCtx,
    private val provaRest: ProvaRest,
    private val provaTextParser: TextParser<String, List<Questao>>,
    private val gabaritoExtractor: CebraspeGabaritoExtractor
) : ProvaService {
    override fun insert(prova: Prova): Prova {
        provaRest.insert()
        return prova
    }

    override fun provaFromRawText(
        metadata: MetaData,
        metaProva: MetaProva
    ): Prova {
        if (metaProva.prova == null || metaProva.gabarito == null) {
            return Prova()
        }
        if (metaProva.prova.texto == "") {
            return Prova()
        }

        val rawProva = appCtx
            .openFile(metadata.chave, metaProva.prova.texto)
            .readText()
        val rawGabarito = appCtx
            .openFile(metadata.chave, metaProva.gabarito.texto)
            .readText()

        val gabarito = gabaritoExtractor.extractMap(rawGabarito)
        val questoes = provaTextParser.parse(rawProva)
        return bindGabarito(questoes, gabarito)
    }

    private fun bindGabarito(
        questoes: List<Questao>,
        gabaritoMap: Map<Int, Char>
    ): Prova {


        return Prova(
            questoes.map {
                val g = gabaritoMap[it.numero]
                it.copy(
                )
            }
        )
    }
}