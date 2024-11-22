package com.github.ax_as.extrator_provas.domain.extractor.text

interface TextParser<I, O> {
    fun parse(text: I): O
}

