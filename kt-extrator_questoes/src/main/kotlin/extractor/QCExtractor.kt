package extractor

import com.codeborne.selenide.Condition.enabled
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selectors.byName
import com.codeborne.selenide.Selectors.byText
import com.codeborne.selenide.Selenide.*
import kotlinx.coroutines.*


class QCExtractor(val cfg: QCConfig) : Extractor {

    override suspend fun extract(): Result {
        prepare()
        return QCResult("")
    }


    private suspend fun prepare() = coroutineScope {
        val popupMonitor = launch(Dispatchers.Default) {
            while (isActive) {
                try {
                    print("listening")
                } catch (e: Exception) {

                    val sel = "body > div > div.button-container"
                    if (`$`(sel).exists() && `$`(sel).isDisplayed) {
                        `$`(sel).click()
                    }

                }
                delay(10000)
            }
        }

        open("https://www.qconcursos.com")
        println("aberto")

        `$`(byText("Entrar")).click()
        `$`(byText("Continuar com e-mail")).click()

        println("clicou em entrar")

        `$`(byName("email")).`val`(cfg.user)
        `$`(byName("password")).`val`(cfg.password)
        `$`(byText("Entrar com e-mail")).click()
        `$`(byText("Questões")).shouldBe(visible, enabled).click()

        `$`(byText("Filtrar")).shouldBe(visible, enabled).click()

        var page = 1
        do {
            filterByDisciplina(page)
            var links = getQuestionLinks()
            for (link in links) {
                println(link)
            }

            if (page++ == 10) {
                break
            }
        } while (true)


        for (i in 1..10) {
            print("delaying")
            delay(1000)
        }
        popupMonitor.cancelAndJoin()
    }

    private fun getQuestionLinks(): List<String> {
        return `$$`(".q-id > a").map { e -> e.getAttribute("href").toString() }
    }

    private fun filterByDisciplina(page: Int) {
        open("https://www.qconcursos.com/questoes-de-concursos/questoes?discipline_ids%5B%5D=96&discipline_ids%5B%5D=100&page=$page")
    }
}


data class QCResult(val content: String) : Result

data class QCConfig(val user: String, val password: String) : Config
