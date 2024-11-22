package com.github.ax_as.extrator_provas.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaData(
    @SerialName("lastDownload")
    val lastDownload: String,
    @SerialName("appId")
    val appId: String = "",
    @SerialName("ano")
    val ano: Int = 0,
    @SerialName("banca")
    val banca: String = "",
    @SerialName("nomeAbreviado")
    val nomeAbreviado: String = "",
    @SerialName("nomeCompleto")
    val nomeCompleto: String = "",
    @SerialName("orgao")
    val orgao: String = "",
    @SerialName("url")
    val chave: String = "",
)