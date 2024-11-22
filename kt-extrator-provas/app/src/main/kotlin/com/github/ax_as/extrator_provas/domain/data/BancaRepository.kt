package com.github.ax_as.extrator_provas.domain.data

import com.github.ax_as.extrator_provas.domain.model.Banca

interface BancaRepository{

    fun all(): List<Banca>


}