package com.github.ax_as.extrator_provas.services

import com.github.ax_as.extrator_provas.domain.model.MetaData
import com.github.ax_as.extrator_provas.domain.model.MetaProva
import com.github.ax_as.extrator_provas.domain.model.Prova

interface ProvaService {
    fun insert(prova: Prova): Prova

    fun provaFromRawText(
        metadata: MetaData,
        metaProva: MetaProva
    ): Prova


}