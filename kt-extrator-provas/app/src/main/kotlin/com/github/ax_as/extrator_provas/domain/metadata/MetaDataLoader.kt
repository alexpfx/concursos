package com.github.ax_as.extrator_provas.domain.metadata

import com.github.ax_as.extrator_provas.AppCtx
import com.github.ax_as.extrator_provas.domain.model.MetaData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject


class MetaDataLoader @Inject constructor(var json: Json) {

    fun load(file: File): MetaData {
        val jsonStr = file.readText()
        return json.decodeFromString<MetaData>(jsonStr)
    }

    fun loadAllFiles(mdDir: File): List<File> {
        val concursoMetadataFiles =
            mdDir.listFiles { _, name -> name.endsWith(AppCtx.concursoMetadataSufix) }
                ?: emptyArray<File>()
        return concursoMetadataFiles.toList()
    }

    fun loadAllMetadata(mdDir: File): List<MetaData> {
        return loadAllFiles(mdDir).map { load(it) }
    }

}