package com.github.ax_as.extrator_provas.domain.extractor.pdf

enum class PDFMarks (val unicode: String){
    ParagraphStart(""),
    ParagraphEnd(""),
    PageStart(""),
    PageEnd(PageStart.unicode),
    ArticleStart(""),
    ArticleEnd(""),
    PageMargin(PageStart.unicode);
}