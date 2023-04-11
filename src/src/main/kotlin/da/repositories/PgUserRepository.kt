package da.repositories

//
//class PgUserRepository : IManufacturerRepository {
//    override fun create(obj: User) {
//        transaction {
//            val dao = UserTable.new {
//                login = obj.login
//                password = obj.password
//                isAdmin = obj.isAdmin
//            }
//        }
//    }
//
//    override fun read(id: ULong): User = transaction {
//        val obj = UserTable.findById(id.toInt())
//        obj?.toEntity() ?: throw NotFoundInDBException("User with id = $id not found")
//    }
//
//    override fun update(obj: User) {
//        transaction {
//            val dao =
//                UserTable.findById(obj.id.toInt()) ?: throw NotFoundInDBException("User with id = ${obj.id} not found")
//            dao.login = obj.login
//            dao.password = obj.password
//            dao.isAdmin = obj.isAdmin
//        }
//    }
//
//    override fun delete(id: ULong) {
//        transaction {
//            UserTable.findById(id.toInt())?.delete() ?: throw NotFoundInDBException("User with id = $id not found")
//        }
//    }
//
//    override fun getAll(): List<User> = transaction {
//        UserTable.all().toList().map { it.toEntity() }
//    }
//
//    override fun exists(id: ULong): Boolean = transaction { UserTable.findById(id.toInt()) != null }
//
//    override fun isLoginNotExist(login: String): Boolean =
//        transaction { UserTable.find { Users.login eq login }.firstOrNull() } == null
//
//    override fun changeRole(id: ULong, isAdmin: Boolean) {
//        transaction {
//            val dao = UserTable.findById(id.toInt()) ?: throw NotFoundInDBException("User with id = $id not found")
//            dao.isAdmin = isAdmin
//        }
//    }
//
//    override fun isAdmin(id: ULong): Boolean = transaction {
//        val dao = UserTable.findById(id.toInt())
//        dao?.isAdmin ?: throw NotFoundInDBException("User with id = $id not found")
//    }
//
//    override fun getByLogin(login: String): User = UserTable.find { Users.login eq login }.firstOrNull()?.toEntity()
//        ?: throw NotFoundInDBException("User with login = $login not found")
//
//    override fun getSavedRecipes(userID: ULong): List<RecipePreview> = transaction {
//        val query =
//            Recipes.innerJoin(SavedRecipes).innerJoin(Users).slice(Recipes.columns).select(Users.id eq userID.toInt())
//        RecipePreviewTable.wrapRows(query).map { it.toEntity() }
//    }
//
//    override fun getOwnRecipes(userID: ULong): List<RecipePreview> = transaction {
//        val query = Recipes.innerJoin(Users).slice(Recipes.columns).select(Users.id eq userID.toInt())
//        RecipePreviewTable.wrapRows(query).map { it.toEntity() }
//    }
//
//    override fun getPublishedRecipes(userID: ULong): List<RecipePreview> = transaction {
//        val query = Recipes.innerJoin(Users).slice(Recipes.columns)
//            .select((Users.id eq userID.toInt()) and (Recipes.published eq true))
//        RecipePreviewTable.wrapRows(query).map { it.toEntity() }
//    }
//
//}
