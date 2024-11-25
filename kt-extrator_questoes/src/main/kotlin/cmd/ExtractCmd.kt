package cmd

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import extractor.ExtractorFactory
import extractor.QCConfig
import kotlinx.coroutines.runBlocking


class ExtractCmd : CliktCommand(name = "extract") {

    private val servico: String? by option("-s", "--servico")
    private val user: String? by option("-u")
    private val password: String? by option("-p")


    override fun run(): Unit = runBlocking {
        val qc = ExtractorFactory.createExtractor(servico!!, QCConfig(user = user!!, password = password!!))
        qc.extract()
    }
}