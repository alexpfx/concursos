package com.github.ax_as.extrator_provas.services

import com.github.ax_as.extrator_provas.domain.model.MetaArquivo
import com.github.ax_as.extrator_provas.domain.model.MetaProva

val regexGabarito = """GAB_""".toRegex()

class ProvaMetadataServiceImpl : ProvaMetadataService {
    override fun groupProvaGabarito(arquivos: List<MetaArquivo>): List<MetaProva> {
        val partition =
            arquivos.partition { regexGabarito.containsMatchIn(it.original) }

        val gabaritos = partition.first
        val provas = partition.second

        return gabaritos.map {
            MetaProva(
                prova = findProvaForGabarito(it, provas),
                gabarito = it
            )
        }
    }

    private fun findProvaForGabarito(
        gabarito: MetaArquivo,
        arquivos: List<MetaArquivo>
    ): MetaArquivo? {
        val filtrados =
            arquivos.filter { !regexGabarito.containsMatchIn(it.original) }

        val basename = gabarito.original
        val prova = filtrados.find { basename.contains(it.original) }
        return prova
    }
}