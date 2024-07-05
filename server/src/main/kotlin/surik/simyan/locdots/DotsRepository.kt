package surik.simyan.locdots

import Dot
import org.bson.BsonValue

interface DotsRepository {
    suspend fun createIndex(): Unit

    suspend fun insertOne(dot: Dot): BsonValue?
    suspend fun getAll(lat: Double, lng: Double, isDescending: Boolean): List<Dot>

    companion object {
        const val DOTS_COLLECTION = "dots"
        const val COORDINATES_FIELD = "coordinates"
    }
}