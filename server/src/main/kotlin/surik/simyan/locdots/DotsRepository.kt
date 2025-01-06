package surik.simyan.locdots

import org.bson.BsonValue
import surik.simyan.locdots.shared.data.Dot
import surik.simyan.locdots.shared.data.DotSort

interface DotsRepository {
    suspend fun createIndex()

    suspend fun insertOne(dot: Dot): BsonValue?
    suspend fun getAll(lat: Double, lng: Double, sortingType: DotSort): List<Dot>

    companion object {
        const val DOTS_COLLECTION = "dots"
        const val COORDINATES_FIELD = "coordinates"
    }
}