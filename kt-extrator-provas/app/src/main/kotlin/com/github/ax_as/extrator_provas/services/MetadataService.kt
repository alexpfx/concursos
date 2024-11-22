package com.github.ax_as.extrator_provas.services

import com.github.ax_as.extrator_provas.domain.model.MetaData

interface MetadataService {
    fun loadAllFromDir(): List<MetaData>
}