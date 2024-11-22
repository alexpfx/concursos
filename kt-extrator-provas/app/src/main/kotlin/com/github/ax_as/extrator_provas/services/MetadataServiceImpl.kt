package com.github.ax_as.extrator_provas.services

import com.github.ax_as.extrator_provas.AppCtx
import com.github.ax_as.extrator_provas.domain.metadata.MetaDataLoader
import com.github.ax_as.extrator_provas.domain.model.MetaData
import java.io.File

class MetadataServiceImpl(
    val appCtx: AppCtx,
    val metaDataLoader: MetaDataLoader
) :
    MetadataService {


    override fun loadAllFromDir(): List<MetaData> {
        val filesDir = appCtx.openMetadataDir()
        val concursoMetadataFiles =
            filesDir.listFiles { _, name -> name.endsWith(AppCtx.concursoMetadataSufix) }
                ?: emptyArray()

        val metaData = mutableListOf<MetaData>()

        for (mdf: File in concursoMetadataFiles) {
            val metadata = metaDataLoader.load(mdf)
            metaData.add(metadata)
        }
        return metaData
    }


}