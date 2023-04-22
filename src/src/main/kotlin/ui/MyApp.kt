package ui

import javafx.stage.Stage
import tornadofx.*


class MyApp : App(MainView::class, InternalWindow.Styles::class) {
    override fun start(stage: Stage) {
        with(stage) {
            width = 1200.0
            height = 1000.0
        }
        super.start(stage)
    }

}