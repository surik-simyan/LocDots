package surik.simyan.locdots

import Dot
import org.bson.BsonValue

interface DotsRepository {
    suspend fun insertOne(dot: Dot): BsonValue?
    suspend fun getAll(): List<Dot>
}