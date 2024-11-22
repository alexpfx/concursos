package com.github.ax_as.extrator_provas.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Resposta(
    val alternativa: EnumAlternativa = EnumAlternativa.A,
    val texto: String,
    val correta: Boolean = false,
) {
    companion object {
        val c = Resposta(EnumAlternativa.C, "Certo")
        val e = Resposta(EnumAlternativa.E, "Errado")
    }
}

@Serializable
enum class EnumAlternativa(val codigo: Int, val letra: String) {
    Anulada(0, "X"),
    A(1.shl(1), "A"),
    B(1.shl(2), "B"),
    C(1.shl(3), "C"),
    D(1.shl(4), "D"),
    E(1.shl(5), "E"),
    Certo(1.shl(6), "Certo"),
    Errado(1.shl(5), "Errado");

    companion object {
        fun findByCodigo(codigo: Int): EnumAlternativa {
            return entries.find { it.codigo == codigo }
                ?: throw Error("Alternativa inválida")
        }

        fun findByLetra(letra: String): EnumAlternativa {
            return entries.find { it.letra == letra }
                ?: throw Error("Alternativa inválida $letra")
        }
    }


}
