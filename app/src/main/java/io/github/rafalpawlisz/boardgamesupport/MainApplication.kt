package io.github.rafalpawlisz.boardgamesupport

import android.app.Application
import io.github.rafalpawlisz.boardgamesupport.viewmodel.DieViewModel
import io.github.rafalpawlisz.boardgamesupport.viewmodel.Haptics
import io.github.rafalpawlisz.boardgamesupport.viewmodel.ImagoViewModel
import io.github.rafalpawlisz.boardgamesupport.viewmodel.TimerViewModel
import io.github.rafalpawlisz.boardgamesupport.viewmodel.WielkiZakladViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Haptics.init(this)
        startKoin {
            modules(appModule)
        }
    }
}

val appModule = module {
    viewModel { DieViewModel() }
    viewModel { ImagoViewModel() }
    viewModel { WielkiZakladViewModel() }
    viewModel { TimerViewModel() }
}