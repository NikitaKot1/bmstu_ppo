package bl.managers

import bl.entities.Ingredient
import bl.exceptions.AlreadyExistingIngredientException
import bl.exceptions.NotExistingIngredientException
import bl.repositories.IIngredientRepository
import bl.repositories.IRepository


object IngredientManager : ICRUDManager<Ingredient> {
    private lateinit var repository: IIngredientRepository

    override fun registerRepository(repository: IRepository<Ingredient>) {
        this.repository = repository as IIngredientRepository
    }

    override fun create(obj: Ingredient) {
        if (!isUniq(obj)) throw AlreadyExistingIngredientException("Ingredient already exists")

        repository.create(obj)
    }

    override fun read(id: ULong): Ingredient {
        if (!isExist(id)) throw NotExistingIngredientException("Ingredient not exists")

        return repository.read(id)
    }

    override fun update(obj: Ingredient) {
        if (!isExist(obj.id)) throw NotExistingIngredientException("Ingredient not exists")

        repository.update(obj)
    }

    override fun delete(id: ULong) {
        if (!isExist(id)) throw NotExistingIngredientException("Ingredient not exists")

        repository.delete(id)
    }

    override fun getAll(): List<Ingredient> = repository.getAll()

    override fun isUniq(obj: Ingredient) = repository.isNameNotExist(obj.name)

    override fun isExist(id: ULong): Boolean = repository.exists(id)

    fun findByName(name: String): Ingredient {
        var tmp = repository.findByName(name)
        if (tmp == null) throw NotExistingIngredientException("Ingredient not exists")
        else return tmp
    }
}