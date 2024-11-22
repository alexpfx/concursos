package com.github.ax_as.extrator_provas.domain.extractor.text

import com.github.ax_as.extrator_provas.domain.extractor.processors.mlDotAllRegex
import com.github.ax_as.extrator_provas.domain.extractor.processors.mlRegex

data class Patterns(
    val textPattern: TextPatterns? = null,
    val arg: String = ""
) {
    val ac = mutableListOf<String>()

    init {
        textPattern?.let {
            ac.add(textPattern.target.format(arg))
        }
    }

}

fun Patterns.plus(
    p: TextPatterns,
    prefix: String = "",
    suffix: String = "",
    arg: String = "",
    matchResult: MatchResult? = null,
    vararg groups: Int = intArrayOf()
): Patterns {
    if (matchResult != null && groups.isNotEmpty()) {
        this.ac.add("$prefix${p.target.format(groups.joinToString("") { matchResult.groupValues[it] })}$suffix")
    } else {
        this.ac.add("$prefix${p.target.format(arg)}$suffix")
    }
    return this
}

fun Patterns.mlRegex(): Regex {
    return this.ac.joinToString("").mlRegex()
}

fun Patterns.mlDotAllRegex(): Regex {
    return ac.joinToString("").mlDotAllRegex()
}

fun Patterns.build(): String {
    return ac.joinToString("")
}


enum class TextPatterns(val target: String) {
    alternativa("/alt-%s/"),
    numeroQuestao("/num-%s/"),
    pageBreak("/break-%s/"),
    startQuestao("/questao-%s/"),
    startBody("/body-%s/");

    fun format(s: MatchResult, vararg group: Int) =
        target.format(group.joinToString("") { s.groupValues[it] })

    fun format(s: String) = target.format(s)

    fun and(with: String, vararg s: String): String {
        return target.format(with) + s.joinToString("")
    }

    fun and() = and("")


}
