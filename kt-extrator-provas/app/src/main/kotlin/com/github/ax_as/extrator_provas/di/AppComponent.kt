package com.github.ax_as.extrator_provas.di

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ax_as.extrator_provas.App
import com.github.ax_as.extrator_provas.AppCtx
import com.github.ax_as.extrator_provas.cmd.ExtractCmd
import dagger.BindsInstance
import dagger.Component
import java.nio.file.Path
import javax.inject.Named
import javax.inject.Singleton


@Component(modules = [AppModule::class])
interface AppComponent {
    fun appCtx(): AppCtx

    fun inject(cmd: ExtractCmd)


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appDataDir(dataDir: Path): Builder

        @BindsInstance
        fun metadataDir(@Named("metadata") dir: String): Builder

        @BindsInstance
        fun appId(@Named("appId") appId: String): Builder

        fun build(): AppComponent
    }
}

