package com.github.ax_as.extrator_provas.domain.extractor.processors

abstract class BasePipelineProcessor<I, O>(
    var listener: PipelineProcessor.Listener<I, O> = object :
        PipelineProcessor.Listener<I, O> {
        override fun call(input: I, output: O) {
            println("chamou da classe base")
        }
    }
) : PipelineProcessor<I, O> {

    override fun process(value: I): O {
        val p = doProcess(value)
        listener.call(value, p)
        return p
    }

    abstract fun doProcess(value: I): O
}