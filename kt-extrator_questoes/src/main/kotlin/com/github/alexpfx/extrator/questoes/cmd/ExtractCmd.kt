package org.example.com.github.alexpfx.extrator.questoes.cmd

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context

class ExtractCmd() : CliktCommand(
    name = "extract"
) {
    override fun help(context: Context) = "Extrair as questões de determinado serviço de questões"


    override fun run() {
        echo("Extraindo questoes")
    }
}