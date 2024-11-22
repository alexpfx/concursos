import cmd.ExtractCmd
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
        var app = App()

        app.subcommands(
            ExtractCmd()
        ).completionOption().main(args)

    } catch (e: CliktError) {
        exitProcess(e.statusCode)
    }

}


