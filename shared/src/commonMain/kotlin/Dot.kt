import kotlinx.serialization.Serializable

@Serializable
data class Dot(
    val id: Int,
    val date: String,
    val lat: Double,
    val lon: Double,
    val message: String
)