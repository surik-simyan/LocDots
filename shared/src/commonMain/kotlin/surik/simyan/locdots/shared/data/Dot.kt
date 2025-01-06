package surik.simyan.locdots.shared.data

import kotlinx.serialization.Serializable

@Serializable
data class Dot(
    val timestamp: Long,
    val coordinates: Pair<Double, Double>,
    val message: String
)