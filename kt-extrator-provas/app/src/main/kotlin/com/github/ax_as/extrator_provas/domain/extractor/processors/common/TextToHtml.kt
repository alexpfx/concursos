package com.github.ax_as.extrator_provas.domain.extractor.processors.common

import com.github.ax_as.extrator_provas.domain.extractor.processors.BasePipelineProcessor
import org.apache.commons.text.StringEscapeUtils
import javax.inject.Inject

class TextToHtml @Inject constructor(
    private val config: Config
) : BasePipelineProcessor<String, String>() {

    data class Config(
        val docPrefix: String? = null,
        val docSuffix: String? = null,
        val wrapperClass: String,
        val items: List<Item>,
    ) {
        data class Item(
            val pattern: Regex,
            val groupName: List<String>,
        )
    }

    override fun doProcess(value: String): String {
        val prefix =
            config.docPrefix
                ?: "<!DOCTYPE html>\n<html><head><title>title</title></head><body>"
        val suffix = config.docSuffix ?: "</body></html>"

        var output = StringEscapeUtils.escapeHtml4(value)
        val documentWrapper = "${prefix}%s${suffix}"
        val openClose = "<div class=\"${config.wrapperClass}\">%s</div>"
        val itemOpenClose = "<div class=\"%s\">%s</div>"

        for (item in config.items) {
            val all = item.pattern.findAll(input = output).toList().asReversed()

            for (mr in all) {
                for (groupName in item.groupName.asReversed()) {
                    val selectedGroup = mr.groups[groupName]
                    val selectedText = selectedGroup?.value ?: ""
                    if (selectedGroup != null && selectedText.isNotBlank()) {
                        output = output.replaceRange(
                            selectedGroup.range,
                            itemOpenClose.format(
                                groupName,
                                selectedText.trim()
                            )
                        )
                    }
                }
            }
        }
        return documentWrapper.format(openClose.format(output.trim()))
    }


}