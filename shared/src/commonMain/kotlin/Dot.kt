import kotlinx.serialization.Serializable

@Serializable
data class Dot(
    val date: String,
    val lat: Double,
    val log: Double,
    val message: String
)