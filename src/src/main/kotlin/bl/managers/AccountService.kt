package bl.managers

import bl.entities.User
import bl.exceptions.LogInFailedException

object AccountService {
    fun registerManufacturer(login: String, password: String) {
        val user = User(0u, login, password)
        ManufacturerManager.create(user)
    }

    fun registerConsumer(login: String, password: String) {
        val user = User(0u, login, password)
        ConsumerManager.create(user)
    }

    fun logINManufacturer(login: String, password: String): User {
        val user = ManufacturerManager.getByLogin(login)
        if (user.password != passwordConvert(password))
            throw LogInFailedException("LogIn failed")
        else
            return user
    }

    fun logINConsumer(login: String, password: String): User {
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