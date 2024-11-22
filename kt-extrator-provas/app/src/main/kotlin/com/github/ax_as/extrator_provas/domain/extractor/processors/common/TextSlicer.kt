package com.github.ax_as.extrator_provas.domain.extractor.processors.common

import com.github.ax_as.extrator_provas.domain.extractor.processors.BasePipelineProcessor
import com.github.ax_as.extrator_provas.domain.model.Questao
import org.intellij.lang.annotations.JdkConstants.InputEventMask
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import javax.inject.Inject

/**
 * Recebe um corpo de prova extraí cada questão e as coloca em uma lista.*
 */
class TextSlicer<T> @Inject constructor(
    private val config: Config<T>
) :
    BasePipelineProcessor<String, List<T>>() {

    class Config<T>(
        val selector: String,
        val parser: (Element) -> T
    )

    override fun doProcess(value: String): List<T> {
        val html = Jsoup.parse(value)
        val selected = html.select(config.selector)

        val list = mutableListOf<T>()
        for (e in selected) {
            val q = config.parser(e)
            list.add(q)
        }


        /*  val q = config.start.findAll(value).toList()
          val n = q.count()
          return q.mapIndexed() { i, mr ->
              val s = mr.range.first
              val e: Int = if (i + 1 == n)
                  value.trimEnd().length - 1
              else
                  q[i + 1].range.first

              value.substring(s, e).trim()

          }*/
        return list
    }

}