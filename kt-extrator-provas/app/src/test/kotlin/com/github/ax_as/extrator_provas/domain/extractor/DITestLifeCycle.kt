package com.github.ax_as.extrator_provas.domain.extractor

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

interface DITestLifeCycle {

    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAllTests(): Unit {

//            startKoin {
//            AppModule().module
//                modules(
//                    applicationModule(
//                        Path.of("/data/files/concurso/testFiles"), "qq"
//                    ),
//                )
//            }
        }

        @JvmStatic
        @AfterAll
        fun afterAllTests(): Unit {
        }
    }
}