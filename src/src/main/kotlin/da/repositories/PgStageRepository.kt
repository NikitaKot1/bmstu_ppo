package da.repositories
//
//import bl.entities.Stage
//import da.dao.*
//import da.exeption.NotFoundInDBException
//import org.jetbrains.exposed.dao.id.EntityID
//import org.jetbrains.exposed.sql.transactions.transaction
//
//class PgStageRepository : IStageRepository {
//    override fun create(obj: Stage) {
//
//    }
//
//    override fun read(id: ULong): Stage {
//        val main = transaction {
//            StageTable.findById(id.toInt())?.toEntity() ?: throw NotFoundInDBException("Stage with id = $id not found")
//        }
//        val ingredients = transaction {
//            StageTable.findById(id.toInt())?.list?.map { it.toIngredient() }
//                ?: throw NotFoundInDBException("Stage with id = $id not found")
//        }
//        main.ingredients = ingredients
//        return main
//    }
//
//    override fun update(obj: Stage) {
//        transaction {
//            val dao = StageTable.findById(obj.id.toInt())
//                ?: throw NotFoundInDBException("Stage with id = ${obj.id} not found")
//            dao.time = obj.time.toInt()
//            dao.description = obj.description
//
//            val ingredients = dao.list.map { it.delete() }
//
//            for (it in obj.ingredients) {
//                IngredientListTable.new {
//                    amount = it.amount.toInt()
//                    processingType = it.processingType
//                    ingredientId = EntityID(it.id.toInt(), Ingredients)
//                    stageId = EntityID(obj.id.toInt(), Stages)
//                }
//            }
//        }
//    }
//
//    override fun delete(id: ULong) {
//        transaction {
//            val dao = StageTable.findById(id.toInt())
//                ?: throw NotFoundInDBException("Stage with id = $id not found")
//
//            dao.list.map { it.delete() }
//            dao.delete()
//        }
//    }
//
//    override fun getAll(): List<Stage> = transaction {
//        StageTable.all().map { it.toEntity() }
//    }
//
//    override fun exists(id: ULong): Boolean = transaction { StageTable.findById(id.toInt()) != null }
//}