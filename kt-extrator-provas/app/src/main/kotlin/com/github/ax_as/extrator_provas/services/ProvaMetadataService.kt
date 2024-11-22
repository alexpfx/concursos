package com.github.ax_as.extrator_provas.services

import com.github.ax_as.extrator_provas.domain.model.MetaArquivo
import com.github.ax_as.extrator_provas.domain.model.MetaProva

interface ProvaMetadataService {
    fun groupProvaGabarito(arquivos: List<MetaArquivo>): List<MetaProva>
}