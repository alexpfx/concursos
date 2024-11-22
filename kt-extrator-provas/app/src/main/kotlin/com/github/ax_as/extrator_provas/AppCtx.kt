package com.github.ax_as.extrator_provas

import jdk.jfr.Name
import java.io.File
import java.nio.file.Path
import javax.inject.Inject
import javax.inject.Named
import kotlin.io.path.absolutePathString


data class AppCtx @Inject constructor(
    val appDataDir: Path,
    @Named("metadata")
    val metadataDir: String,
    @Named("appId")
    val appId: String,
) {

    companion object {
        const val defaultAppDataDir = "/data/files/concursos/"
        const val concursoMetadataSufix = ".metadata.json"
        const val appId = "kt-extractor-provas"

        val mapServicoDir = mapOf(
            "cebraspe" to "Cebraspe",
            "fcc" to "qq",
            "qq" to "qq",
        )
    }

    fun openFile(base: String, filename: String): File {
        return Path.of(
            appDataDir.absolutePathString(), metadataDir, base, filename
        ).toFile()
    }

    fun openMetadataDir(): File {
        return Path.of(appDataDir.absolutePathString(), metadataDir).toFile()
    }
}