package bl.managers

import bl.entities.Comment
import bl.exceptions.AlreadyExistingCommentException
import bl.exceptions.NotExistingCommentException
import bl.repositories.ICommentRepository
import bl.repositories.IRepository

object CommentManager : ICRUDManager<Comment> {
    private lateinit var repository: ICommentRepository

    override fun registerRepository(repository: IRepository<Comment>) {
        this.repository = repository as ICommentRepository
    }

    override fun create(obj: Comment) {
        if (!isUniq(obj)) throw AlreadyExistingCommentException("Comment already exists")

        repository.create(obj)
    }

    override fun read(id: ULong): Comment {
        if (!isExist(id)) throw NotExistingCommentException("Comment not exists")

        return repository.read(id)
    }

    override fun update(obj: Comment) {
        if (!isExist(obj.id)) throw NotExistingCommentException("Comment not exists")

        repository.update(obj)
    }

    override fun delete(id: ULong) {
        if (!isExist(id)) throw NotExistingCommentException("Comment not exists")

        repository.delete(id)
    }

    override fun getAll(): List<Comment> = repository.getAll()

    override fun isUniq(obj: Comment) = true

    override fun isExist(id: ULong): Boolean = repository.exists(id)
}