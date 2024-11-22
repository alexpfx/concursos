package com.github.ax_as.extrator_provas.domain.util

enum class SpecialChar(val unicode: String) {
    SuperScript("\u2070\u00B9\u00B2\u00B3\u2074\u2075\u2076\u2077\u2078\u2079"),
    SuperScriptO("\u2070"),
    SuperScriptA("\u00AA")
    ;

    companion object {
        fun getSuperScriptNumber(num: Int): String {
            return SuperScript.unicode[num].toString()
        }
    }
}
