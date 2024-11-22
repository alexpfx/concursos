package com.github.ax_as.extrator_provas.domain.extractor.processors.common

import com.github.ax_as.extrator_provas.domain.extractor.processors.PipelineProcessor
import com.github.ax_as.extrator_provas.domain.model.MetaArquivo
import com.github.ax_as.extrator_provas.domain.model.Prova

class ProvaParser : PipelineProcessor<Pair<MetaArquivo, String>, Prova> {

    override fun process(value: Pair<MetaArquivo, String>): Prova {
        var ma = value.first
        var text = value.second





        return Prova()

    }
}