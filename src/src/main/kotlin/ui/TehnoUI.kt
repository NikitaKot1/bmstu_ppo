package ui

import bl.Facade
import da.repositories.factory.PgRepositoryFactory

class TehnoUI {
    val facade = Facade(PgRepositoryFactory())
    fun startInit() {
        print("Say Hello!")
    }
}