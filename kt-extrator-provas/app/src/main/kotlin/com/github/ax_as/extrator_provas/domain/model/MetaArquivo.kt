package com.github.ax_as.extrator_provas.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaArquivo(
    @SerialName("data")
    val dataGeracao: String,
    @SerialName("descricao")
    val descricao: String,
    @SerialName("downloadFullUrl")
    val downloadFullUrl: String,
    @SerialName("nome")
    val original: String,
    @SerialName("rawTextFile")
    val texto: String = "",
    @SerialName("extractedProva")
    val parseado: String = ""
) {
    val hasTextFile: Boolean
        get() = texto.isNotBlank()
    val hasProva: Boolean
        get() = parseado.isNotBlank()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MetaArquivo

        return original == other.original
    }

    override fun hashCode(): Int {
        return original.hashCode()
    }


}
