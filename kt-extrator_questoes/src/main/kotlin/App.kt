import cmd.ExtractCmd
import com.codeborne.selenide.Configuration.holdBrowserOpen
import com.github.ajalt.clikt.completion.completionOption
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import kotlin.system.exitProcess

class App() : CliktCommand() {

    override fun run() {
    }
}


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(args: Array<String>) {
    try {
        val app = App()

        configSelenide()

        val extractCmd = ExtractCmd()
        app.subcommands(
            extractCmd
        ).completionOption().main(args)

    } catch (e: CliktError) {
        exitProcess(e.statusCode)
    }

}

fun configSelenide() {
    holdBrowserOpen = true
}



