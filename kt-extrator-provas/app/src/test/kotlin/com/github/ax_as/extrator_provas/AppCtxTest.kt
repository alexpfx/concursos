package com.github.ax_as.extrator_provas

import com.github.ax_as.extrator_provas.di.AppComponent
import com.github.ax_as.extrator_provas.di.DaggerAppComponent
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Path
import kotlin.test.assertEquals

class AppCtxTest {

    private lateinit var appComponent: AppComponent

    @BeforeEach
    fun setup() {
        appComponent = DaggerAppComponent.builder()
            .appId("teste")
            .appDataDir(Path.of("/data/files/concursos/"))
            .metadataDir("qq").build()
    }

    @Test
    fun instance() {
        val appCtx = appComponent.appCtx()
        assertEquals("teste", appCtx.appId)
        assertEquals(Path.of("/data/files/concursos/"), appCtx.appDataDir)
        assertEquals("qq", appCtx.metadataDir)


    }

}