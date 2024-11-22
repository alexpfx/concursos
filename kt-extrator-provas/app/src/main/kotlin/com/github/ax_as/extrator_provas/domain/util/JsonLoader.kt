package com.github.ax_as.extrator_provas.domain.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.name
import kotlin.io.path.writeText

class JsonLoader(val json: Json) {

    fun <T> write(
        parentDir: Path,
        fileName: String,
        obj: T,
        serializer: KSerializer<T>
    ): String {
        val jsonFile = Path.of(
            parentDir.absolutePathString(),
            "$fileName.json"
        )
        val jsonStr = json.encodeToString(serializer, obj)
        jsonFile.writeText(jsonStr)
        return jsonFile.name
    }

    inline fun <reified T> fromJsonString(value: String): T {
        return json.decodeFromString(value)
    }

    fun <T> update(
        file: File,
        obj: T,
        serializer: KSerializer<T>
    ): String {
        val jsonStr = json.encodeToString(serializer, obj)
        file.writeText(jsonStr)
        return file.name
    }
}