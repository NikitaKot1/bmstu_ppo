package da.repositories

import bl.entities.Comment
import bl.repositories.ICommentRepository
import da.dao.CommentTable
import da.dao.Consumers
import da.dao.toEntity
import da.exeption.NotFoundInDBException
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class PgCommentRepository : ICommentRepository {
    override fun create(obj: Comment) {
        transaction {
            val dao = CommentTable.new {
                date = obj.date
                text = obj.text
                autorId = EntityID(obj.autor.id.toInt(), Consumers)
            }
        }
    }

    override fun read(id: ULong): Comment = transaction {
        val obj = CommentTable.findById(id.toInt())
        obj?.toEntity() ?: throw NotFoundInDBException("Comment with id = $id not found")
    }

    override fun update(obj: Comment) {
        transaction {
            val dao = CommentTable.findById(obj.id.toInt())
                ?: throw NotFoundInDBException("Comment with id = ${obj.id} not found")
            dao.date = obj.date
            dao.text = obj.text
            dao.autorId = EntityID(obj.autor.id.toInt(), Consumers)
        }
    }

    override fun delete(id: ULong) {
        transaction {
            CommentTable.findById(id.toInt())?.delete()
                ?: throw NotFoundInDBException("Comment with id = $id not found")
        }
    }

    override fun getAll(): List<Comment> = transaction {
        CommentTable.all().map { it.toEntity() }
    }

    override fun exists(id: ULong): Boolean = transaction { CommentTable.findById(id.toInt()) != null }

}