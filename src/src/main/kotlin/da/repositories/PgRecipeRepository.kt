package da.repositories

//
//class PgRecipeRepository : IRecipeRepository {
//    override fun create(obj: Recipe) {
//        transaction {
//
//            RecipeTable.new {
//                name = obj.name
//                description = obj.description
//                date = obj.date
//                ownerId = EntityID(obj.owner.id.toInt(), Users)
//            }
//        }
//    }
//
//    private fun createStage(obj: Stage, recipeId: ULong) {
//        for (it in obj.ingredients) {
//            IngredientListTable.new {
//                amount = it.amount.toInt()
//                processingType = it.processingType
//                ingredientId = EntityID(it.id.toInt(), Ingredients)
//                stage = StageTable.new {
//                    time = obj.time.toInt()
//                    description = obj.description
//                    this.recipeId = EntityID(recipeId.toInt(), Users)
//                }
//            }
//        }
//    }
//
//    override fun read(id: ULong): Recipe {
//        val dao = transaction {
//            RecipeTable.findById(id.toInt())
//                ?: throw NotFoundInDBException("Recipe with id = $id not found")
//        }
//        val res = dao.toEntity()
//        res.stages = dao.stages.map { it.toEntity() }
//        return res
//    }
//
//    override fun update(obj: Recipe) {
//        transaction {
//            val dao = RecipeTable.findById(obj.id.toInt())
//                ?: throw NotFoundInDBException("Recipe with id = ${obj.id} not found")
//            dao.name = obj.name
//            dao.description = obj.description
//            dao.time = obj.time.toInt()
//            dao.servingsNum = obj.servingsNum.toInt()
//            dao.protein = obj.pfc.protein
//            dao.fat = obj.pfc.fat
//            dao.carbon = obj.pfc.carbon
//            dao.date = obj.date
//            dao.published = obj.published
//            dao.ownerId = EntityID(obj.owner.id.toInt(), Users)
//        }
//    }
//
//    private fun updateStage(obj: Stage) {
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
//            val dao = RecipeTable.findById(id.toInt()) ?: throw NotFoundInDBException("Recipe with id = $id not found")
//            dao.stages.map { deleteStage(it.id.value.toULong()) }
//            dao.delete()
//        }
//    }
//
//    override fun getAll(): List<Recipe> = transaction {
//        RecipeTable.all().map { it.toEntity() }
//    }
//
//    override fun exists(id: ULong): Boolean = transaction { RecipeTable.findById(id.toInt()) != null }
//
//    override fun getAllPreview(): List<RecipePreview> = transaction {
//        RecipePreviewTable.all().map { it.toEntity() }
//    }
//
//    override fun getOwnerID(id: ULong): ULong = transaction {
//        RecipeTable.findById(id.toInt())?.ownerId?.value?.toULong()
//            ?: throw NotFoundInDBException("Recipe with owner id = $id not found")
//    }
//
//
//    override fun addToFavorite(id: ULong, userID: ULong) {
//        transaction {
//            SavedRecipeTable.new {
//                userId = EntityID(userID.toInt(), Users)
//                recipeId = EntityID(id.toInt(), Recipes)
//            }
//        }
//    }
//
//    override fun addComment(userId: ULong, recipeId: ULong, comment: Comment) {
//        transaction {
//            CommentTable.new {
//                date = comment.date
//                text = comment.text
//                autorId = EntityID(userId.toInt(), Users)
//                this.recipeId = EntityID(recipeId.toInt(), Recipes)
//            }
//        }
//    }
//
//    override fun addToPublishQueue(id: ULong) {
//        transaction {
//            PublishQueueTable.new {
//                recipeId = EntityID(id.toInt(), Recipes)
//            }
//        }
//    }
//
//    override fun getPublishQueue(): List<Recipe> = transaction {
//        val query = PublishQueue.innerJoin(Recipes).slice(Recipes.columns).selectAll()
//        RecipeTable.wrapRows(query).map { it.toEntity() }
//    }
//
//    override fun getSortedByDate(): List<RecipePreview> = transaction {
//        val query = Recipes.selectAll().orderBy(Recipes.date to SortOrder.DESC)
//        RecipePreviewTable.wrapRows(query).map { it.toEntity() }
//    }
//
//
//    override fun getSortedByLike(): List<RecipePreview> {
//        TODO()
//    }
//
//    override fun isPublished(id: ULong): Boolean =
//        RecipeTable.findById(id.toInt())?.published ?: throw NotFoundInDBException("Recipe with id = $id not found")
//
//    override fun approvePublication(id: ULong) {
//        transaction {
//            val recipe = RecipeTable.findById(id.toInt())
//            if (recipe != null) recipe.published = true
//            else throw NotFoundInDBException("Recipe with id = $id not found")
//
//            PublishQueueTable.findById(id.toInt())?.delete()
//                ?: throw NotFoundInDBException("Recipe with id = $id not found in publication queue")
//        }
//    }
//
//    override fun cancelPublication(id: ULong) {
//        transaction {
//            PublishQueueTable.findById(id.toInt())?.delete()
//                ?: throw NotFoundInDBException("Recipe with id = $id not found in publication queue")
//        }
//    }
//
//    override fun isInPublishQueue(id: ULong): Boolean = transaction {
//        PublishQueueTable.find { PublishQueue.recipe eq EntityID(id.toInt(), Recipes) }.firstOrNull() != null
//    }
//
//    override fun addStage(recipeId: ULong, num: ULong, stage: Stage) {
//        transaction {
//            StageTable.new {
//                time = stage.time.toInt()
//                description = stage.description
//                this.recipeId = EntityID(recipeId.toInt(), Recipes)
//            }
//        }
//    }
//
//    private fun deleteStage(id: ULong) {
//        val dao = StageTable.findById(id.toInt())
//            ?: throw NotFoundInDBException("Stage with id = $id not found")
//
//        dao.list.map { it.delete() }
//        dao.delete()
//    }
//
//    private fun readStage(id: ULong): Stage {
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
//
//}