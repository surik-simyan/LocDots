package surik.simyan.locdots

import Dot
import com.mongodb.MongoException
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList
import org.bson.BsonValue

class DotsRepositoryImpl(
    private val mongoDatabase: MongoDatabase
) : DotsRepository {

    companion object {
        const val DOTS_COLLECTION = "dots"
    }

    override suspend fun insertOne(dot: Dot): BsonValue? {
        try {
            val result = mongoDatabase.getCollection<Dot>(DOTS_COLLECTION).insertOne(
                dot
            )
            return result.insertedId
        } catch (e: MongoException) {
            System.err.println("Unable to insert due to an error: $e")
        }
        return null
    }

    override suspend fun getAll(): List<Dot> {
        return mongoDatabase.getCollection<Dot>(DOTS_COLLECTION).find()
            .toList()
    }
}