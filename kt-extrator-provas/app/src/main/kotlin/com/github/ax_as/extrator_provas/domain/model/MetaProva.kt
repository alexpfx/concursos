package com.github.ax_as.extrator_provas.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MetaProva(
    val prova: MetaArquivo?,
    val gabarito: MetaArquivo?
)