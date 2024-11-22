package com.github.ax_as.extrator_provas.domain.extractor.text.cebraspe

class CebraspeGabaritoExtractor {
    val regexStartNumero = Regex("""^([1-9]\d{0,2})\b""")
    val regexNumero = Regex("""([1-9]\d{0,2})""")
    val regexStartLetra = Regex("""^\b([X|C|E])\b""")
    val regexLetra = Regex("""\b([X|C|E])\b""")
    fun extractMap(text: String): Map<Int, Char> {
        val nrQuestoes = mutableListOf<Int>()
        val gabaritos = mutableListOf<Char>()
        val stext = text.split("\n")
        for (line in stext) {
            if (regexStartNumero.containsMatchIn(line)) {
                val numeros = getNumeros(line)
                nrQuestoes.addAll(numeros)
            } else if (regexStartLetra.containsMatchIn(line)) {
                val letras = getLetras(line)
                gabaritos.addAll(letras)
            }
        }
        return nrQuestoes.mapIndexed { i, n ->
            n to gabaritos[i]
        }.toMap()
    }

    private fun getLetras(line: String): List<Char> {
        return regexLetra.findAll(line).map {
            it.value.first()
        }.toList()
    }

    private fun getNumeros(line: String): List<Int> {
        return regexNumero.findAll(line).map {
            it.value.toInt()
        }.toList()
    }
}