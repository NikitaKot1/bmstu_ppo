package bl.managers

import bl.entities.Consumer
import bl.entities.RecipePreview
import bl.exceptions.AlreadyExistingUserException
import bl.exceptions.NotExistingUserException
import bl.repositories.IConsumerRepository
import bl.repositories.IRepository

object ConsumerManager : ICRUDManager<Consumer> {

    private lateinit var repository: IConsumerRepository

    override fun registerRepository(repository: IRepository<Consumer>) {
        this.repository = repository as IConsumerRepository
    }

    override fun create(obj: Consumer) {
        if (!isUniq(obj)) throw AlreadyExistingUserException("User already exists")

        repository.create(obj)
    }

    override fun read(id: ULong): Consumer {
        if (!isExist(id)) throw NotExistingUserException("User not exists")

        return repository.read(id)
    }

    override fun update(obj: Consumer) {
        if (!isExist(obj.id)) throw NotExistingUserException("User not exists")

        repository.update(obj)
    }

    override fun delete(id: ULong) {
        if (!isExist(id)) throw NotExistingUserException("User not exists")

        repository.delete(id)
    }

    override fun getAll(): List<Consumer> = repository.getAll()

    override fun isUniq(obj: Consumer) = repository.isLoginNotExist(obj.login)

    override fun isExist(id: ULong): Boolean = repository.exists(id)

    fun getByLogin(login: String) = repository.getByLogin(login) ?: throw NotExistingUserException("User not exists")

    fun getSavedMarks(userId: ULong): List<RecipePreview> {
        if (!isExist(userId)) throw NotExistingUserException("User not exists")

        return repository.getSavedMarks(userId)
    }
}