package com.github.ax_as.extrator_provas.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Questao(
    val numero: Int = 0,
    val enunciado: String = "",
    val textoBase: String = "",
    val respostas: List<Resposta> = emptyList(),
    ){

    companion object{
        val invalid = Questao(-1, "", "", emptyList())
    }
}

