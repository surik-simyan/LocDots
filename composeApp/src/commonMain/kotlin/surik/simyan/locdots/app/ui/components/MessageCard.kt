package surik.simyan.locdots.app.ui.components

import surik.simyan.locdots.shared.data.Dot
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import surik.simyan.locdots.app.ui.DavyGray
import surik.simyan.locdots.app.ui.Jet
import surik.simyan.locdots.app.ui.Platinum

@OptIn(FormatStringsInDatetimeFormats::class)
@Composable
fun MessageCard(dot: Dot) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = DavyGray,
        ),
        modifier = Modifier
            .heightIn(min = 100.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = dot.message,
                modifier = Modifier.padding(16.dp),
                color = Platinum,
                textAlign = TextAlign.Center,
            )
            HorizontalDivider(thickness = 1.dp, color = Jet)
            Text(
                text = getDateFromTimestamp(dot.timestamp),
                modifier = Modifier.padding(16.dp),
                color = Platinum,
                textAlign = TextAlign.Start,
            )
        }
    }
}

fun getDateFromTimestamp(timestamp: Long): String {
    val now = Clock.System.now()
    val past = Instant.fromEpochSeconds(timestamp)
    val duration = now - past
    return when {
        duration.inWholeSeconds < 60 -> "was ${duration.inWholeSeconds} seconds ago"
        duration.inWholeMinutes < 60 -> "was ${duration.inWholeMinutes} minutes ago"
        duration.inWholeHours < 24 -> "was ${duration.inWholeHours} hours ago"
        duration.inWholeDays < 7 -> "was ${duration.inWholeDays} days ago"
        duration.inWholeDays < 30 -> "was ${duration.inWholeDays / 7} weeks ago"
        duration.inWholeDays < 365 -> "was ${duration.inWholeDays / 30} months ago"
        else -> "was ${duration.inWholeDays / 365} years ago"
    }
}