package com.github.ax_as.extrator_provas.domain.data

import com.github.ax_as.extrator_provas.domain.model.Banca

class BancaRepositoryHadcoded : BancaRepository {
    override fun all(): List<Banca> {
        return arrayListOf(
            "Cebraspe",
            "FCC",
            "FGV",
            "Cesgranrio",
            "Quadrix",
            "Cespe",
            "Esaf",
            "Vunesp"
        ).map {
            Banca(nome = it, sigla = it)
        }
    }
}