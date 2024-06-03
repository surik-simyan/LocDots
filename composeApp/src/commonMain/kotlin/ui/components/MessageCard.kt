package ui.components

import Dot
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
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import ui.DavyGray
import ui.Jet
import ui.Platinum

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
                text = dot.date,
                modifier = Modifier.padding(16.dp),
                color = Platinum,
                textAlign = TextAlign.Start,
            )
        }
    }
}