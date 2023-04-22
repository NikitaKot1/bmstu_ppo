package ui

import tornadofx.*
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import ui.techno.TechnoView
import ui.TechnoUI


class MainView : View("My View") {
    override val root = vbox {

        //label("Параша драная этот ваш ЖабаЭфИкс и ТолчекЭфИкс")
        hbox {
            alignment = Pos.CENTER
            button ("ТехноДэмка").action {
                TechnoUI().openWindow()
            }
            button ("Не готовое приложение").action {
            }
        }
    }
}
