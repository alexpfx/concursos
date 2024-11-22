package org.example

import com.github.ajalt.clikt.completion.completionOption
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import org.example.com.github.alexpfx.extrator.questoes.cmd.ExtractCmd
import kotlin.system.exitProcess


class Main : CliktCommand() {
    override fun run() {

    }
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(args: Array<String>) {
    try {
        var app = Main()

        app.subcommands(
            ExtractCmd()
        ).completionOption().main(args)

    } catch (e: CliktError) {
        exitProcess(e.statusCode)
    }

}