import kotlinx.serialization.Serializable

@Serializable
data class Dot(
    val timestamp: Long,
    val coordinates: Array<Double>,
    val message: String
)