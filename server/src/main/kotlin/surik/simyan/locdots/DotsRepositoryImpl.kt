package surik.simyan.locdots

import Dot
import com.mongodb.MongoException
import com.mongodb.client.model.Filters.nearSphere
import com.mongodb.client.model.Indexes
import com.mongodb.client.model.Sorts
import com.mongodb.client.model.geojson.Point
import com.mongodb.client.model.geojson.Position
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList
import org.bson.BsonValue
import surik.simyan.locdots.DotsRepository.Companion.COORDINATES_FIELD
import surik.simyan.locdots.DotsRepository.Companion.DOTS_COLLECTION

class DotsRepositoryImpl(
    private val mongoDatabase: MongoDatabase
) : DotsRepository {

    companion object {
        var indexed = false
    }

    override suspend fun createIndex() {
        if (indexed) return
        mongoDatabase.getCollection<Dot>(DOTS_COLLECTION)
            .createIndex(Indexes.geo2dsphere(COORDINATES_FIELD))
        indexed = true
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

    override suspend fun getAll(
        lat: Double,
        lng: Double,
        isDescending: Boolean
    ): List<Dot> {
        createIndex()
        return mongoDatabase.getCollection<Dot>(DOTS_COLLECTION).find(
            nearSphere(COORDINATES_FIELD, Point(Position(lng, lat)), 5000.0, null)
        ).sort(
            if (isDescending) {
                Sorts.descending(Dot::timestamp.name)
            } else {
                Sorts.ascending(Dot::timestamp.name)
            }
        ).toList()
    }
}