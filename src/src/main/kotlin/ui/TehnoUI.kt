package ui

import bl.Facade
import bl.entities.Manufacturer
import da.exeption.NotFoundInDBException
import da.repositories.factory.PgRepositoryFactory

class TehnoUI {
    val facade = Facade(PgRepositoryFactory())
    fun startInit() {
        print("Say Hello!")
        val selMode = selectMode()
        val logAuth = menuMode()
        if ((logAuth == 2u) and (selMode == 2u)) {
            val manuf = authoriseManufacturer()
        }
    }

    private fun selectMode(): UInt {
        print("Consumer: 1\nManufacturer: 2\n")
        var end = true
        var x = 0u
        while (end) {
            x = readln().toUInt()
            end = (x == 1u) or (x == 2u)
        }
        return x
    }

    private fun menuMode(): UInt {
        print("LogIn: 1\nAuthorise: 2\n")
        var end = true
        var x = 0u
        while (end) {
            x = readln().toUInt()
            end = (x == 1u) or (x == 2u)
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
}