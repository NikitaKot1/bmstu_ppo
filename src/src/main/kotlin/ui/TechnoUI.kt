package ui

import bl.Facade
import bl.entities.Manufacturer
import da.exeption.NotFoundInDBException
import da.repositories.factory.PgRepositoryFactory

import tornadofx.*
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import ui.techno.TechnoView
import javafx.scene.text.Font



class TechnoUI : View("TehnoUI") {
    val facade = Facade(PgRepositoryFactory())
    var otvrat = 0
    fun startInit() {
        val selMode = selectMode()
        val logAuth = menuMode()
        if ((logAuth == 2) and (selMode == 2)) {
            val manuf = authoriseManufacturer()
        }
    }

    private fun selectMode(): Int {
        print("Consumer: 1\nManufacturer: 2\n")
        var end = true
        var x = 0
        while (end) {  
            //x = input.nextLine().toInt()
            //x = readln().toUInt()
            end = (x == 1) or (x == 2)
        }
        return x
    }

    private fun menuMode(): Int {
        print("LogIn: 1\nAuthorise: 2\n")
        var end = true
        var x = 0
        while (end) {
            //x = input.nextInt()
            //x = readln().toUInt()
            end = (x == 1) or (x == 2)
        }
        return x
    }

    private fun authoriseManufacturer(): Manufacturer? {
        var login = ""
        var password = ""
        var end = true
        var manuf: Manufacturer? = null
        while (end) {
            print("Input Login: ")
            login = readln()
            print("Input Password: ")
            password = readln()
//            try {
//                return facade.logInManufacturer(login, password)
//            } catch (e: NotFoundInDBException) {
//                print("Error when logIn lika a Mnaufacturer: user does not exist!\n")
//            }

            manuf = try {
                facade.logInManufacturer(login, password)
            } catch (e: NotFoundInDBException) {
                null
            }
            if (manuf == null)
                print("Error when logIn lika a Mnaufacturer: user does not exist!\n")
            else
                end = false
        }
        return manuf
    }

    override val root = vbox {
        spacing = 20.0
        val lb = label { text = "Ну привет" }
        textfield("") {
            tooltip("Plase your code here") { font = Font.font("Verdana") }
        }
        button {
            this.text = "Сказать 'Привет'"
            action {
                when (otvrat) {
                    0 -> {
                        lb.text = "Oof"
                        this.text = "Commit"
                        otvrat = 1
                    }
                    1 -> {
                        lb.text = "Another"
                    }
                }
            }
        }
    }
}