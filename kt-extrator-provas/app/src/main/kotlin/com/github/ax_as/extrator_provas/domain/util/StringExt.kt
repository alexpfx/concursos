package com.github.ax_as.extrator_provas.domain.util

val doubleSpace = Regex("\\s+")

fun String.clear(): String {
    val s = trim().replace("\n", " ")
    return s.replace(doubleSpace, " ")
}
