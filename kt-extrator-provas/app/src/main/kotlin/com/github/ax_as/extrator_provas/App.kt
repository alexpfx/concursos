/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.github.ax_as.extrator_provas

import com.github.ajalt.clikt.completion.completionOption
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import com.github.ax_as.extrator_provas.AppCtx.Companion.defaultAppDataDir
import com.github.ax_as.extrator_provas.cmd.ExtractCmd
import com.github.ax_as.extrator_provas.di.DaggerAppComponent
import java.nio.file.Path
import kotlin.system.exitProcess

class App : CliktCommand() {


    private val servico: String by option(
        names = arrayOf("-s"),
        help = "nome do serviço"
    ).default("qq")
    private val dataDir: Path
            by option(
                names = arrayOf("--data-dir", "-d"),
                help = "caminho do diretório de dados"
            )
                .path(mustExist = true)
                .default(Path.of(defaultAppDataDir))
    private val config by findOrSetObject { mutableMapOf<String, Any>() }
    override fun run() {
        config["dataDir"] = dataDir
        config["servico"] = servico
    }


}

fun main(args: Array<String>) {
    try {
        var app = App()

        val appComponent = DaggerAppComponent.builder()
            .appId("kt-extractor-provas")
            .appDataDir(Path.of("/data/files/concursos"))
            .metadataDir("qq")
            .build()

        var cmd = ExtractCmd()
        appComponent.inject(cmd)
        app.subcommands(
            cmd
        )
            .completionOption().main(args)
    } catch (e: CliktError) {
        exitProcess(e.statusCode)
    }
}