package bl.managers

import bl.entities.RecipePreview
import bl.entities.User
import bl.exceptions.AlreadyExistingUserException
import bl.exceptions.NotExistingUserException
import bl.repositories.IManufacturerRepository
import bl.repositories.IRepository

object ManufacturerManager : ICRUDManager<User> {
    private lateinit var repository: IManufacturerRepository

    override fun registerRepository(repository: IRepository<User>) {
        this.repository = repository as IManufacturerRepository
    }

    override fun create(obj: User) {
        if (!isUniq(obj)) throw AlreadyExistingUserException("User already exists")

        repository.create(obj)
    }

    override fun read(id: ULong): User {
        if (!isExist(id)) throw NotExistingUserException("User not exists")

        return repository.read(id)
    }

    override fun update(obj: User) {
        if (!isExist(obj.id)) throw NotExistingUserException("User not exists")

        repository.update(obj)
    }

    override fun delete(id: ULong) {
        if (!isExist(id)) throw NotExistingUserException("User not exists")

        repository.delete(id)
    }

    override fun getAll(): List<User> = repository.getAll()

    override fun isUniq(obj: User) = repository.isLoginNotExist(obj.login)

    override fun isExist(id: ULong): Boolean = repository.exists(id)

    fun getByLogin(login: String) = repository.getByLogin(login) ?: throw NotExistingUserException("User not exists")

    fun getMarks(userID: ULong): List<RecipePreview> {
        if (!isExist(userID)) throw NotExistingUserException("User not exists")

        return repository.getMarks(userID)
    }
}