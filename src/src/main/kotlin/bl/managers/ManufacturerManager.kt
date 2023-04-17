package bl.managers

import bl.entities.Manufacturer
import bl.entities.RecipePreview
import bl.exceptions.AlreadyExistingUserException
import bl.exceptions.NotExistingUserException
import bl.repositories.IManufacturerRepository
import bl.repositories.IRepository

object ManufacturerManager : ICRUDManager<Manufacturer> {
    private lateinit var repository: IManufacturerRepository

    override fun registerRepository(repository: IRepository<Manufacturer>) {
        this.repository = repository as IManufacturerRepository
    }

    override fun create(obj: Manufacturer) {
        if (!isUniq(obj)) throw AlreadyExistingUserException("Manufacturer already exists")

        repository.create(obj)
    }

    override fun read(id: ULong): Manufacturer {
        if (!isExist(id)) throw NotExistingUserException("Manufacturer not exists")

        return repository.read(id)
    }

    override fun update(obj: Manufacturer) {
        if (!isExist(obj.id)) throw NotExistingUserException("Manufacturer not exists")

        repository.update(obj)
    }

    override fun delete(id: ULong) {
        if (!isExist(id)) throw NotExistingUserException("Manufacturer not exists")

        repository.delete(id)
    }

    override fun getAll(): List<Manufacturer> = repository.getAll()

    override fun isUniq(obj: Manufacturer) = repository.isLoginNotExist(obj.login)

    override fun isExist(id: ULong): Boolean = repository.exists(id)

    fun getByLogin(login: String) = repository.getByLogin(login) ?: throw NotExistingUserException("User not exists")

    fun getMarks(userID: ULong): List<RecipePreview> {
        if (!isExist(userID)) throw NotExistingUserException("User not exists")

        return repository.getMarks(userID)
    }
}