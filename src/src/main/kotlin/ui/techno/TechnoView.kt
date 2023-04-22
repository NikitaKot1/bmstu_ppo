package ui.techno

import tornadofx.*
import bl.Facade
import bl.entities.Manufacturer
import da.exeption.NotFoundInDBException
import da.repositories.factory.PgRepositoryFactory
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import javafx.scene.text.Font


class TechnoView : View("ТехноДэмка") {

    val facade = Facade(PgRepositoryFactory())

    override val root = hbox {
        form {
            fieldset("Personal info") {
                field("Login") {
                    textfield()
                }
                field("Password") {
                    textfield()
                }
            }
            button("Commit") {
                //TODO("cum")
            }
        }
        // vbox {
        //     alignment = Pos.TOP_LEFT
        //     spacing = 20.0

        //     textfield("") {
        //         tooltip("login") { font = Font.font("Verdana") }
        //     }
        //     textfield("") {
        //         tooltip("password") { font = Font.font("Verdana") }
        //     }
        //     button ("ТехноДэмка").action {
        //     }

        // }
    }
}