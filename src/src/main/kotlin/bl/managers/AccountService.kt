package bl.managers

import bl.entities.Consumer
import bl.entities.Manufacturer
import bl.exceptions.LogInFailedException

object AccountService {
    fun registerManufacturer(login: String, password: String) {
        val user = Manufacturer(0u, login, password)
        ManufacturerManager.create(user)
    }

    fun registerConsumer(login: String, password: String) {
        val user = Consumer(0u, login, password)
        ConsumerManager.create(user)
    }

    fun logINManufacturer(login: String, password: String): Manufacturer {
        val user = ManufacturerManager.getByLogin(login)
        if (user.password != passwordConvert(password))
            throw LogInFailedException("LogIn failed")
        else
            return user
    }

    fun logINConsumer(login: String, password: String): Consumer {
        val user = ConsumerManager.getByLogin(login)
        if (user.password != passwordConvert(password))
            throw LogInFailedException("LogIn failed")
        else
            return user
    }

    private fun passwordConvert(password: String): String { //todo
        return password
    }
}