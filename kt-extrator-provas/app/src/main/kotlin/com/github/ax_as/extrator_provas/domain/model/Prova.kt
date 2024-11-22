package com.github.ax_as.extrator_provas.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Prova(
    @SerialName("questoes")
    val questoes: List<Questao> = arrayListOf(),

    @SerialName("modelo")
    val modelo: String = ""


)

