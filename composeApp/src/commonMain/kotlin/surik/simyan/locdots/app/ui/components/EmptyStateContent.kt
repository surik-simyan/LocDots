package surik.simyan.locdots.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import surik.simyan.locdots.app.ui.EerieBlack
import surik.simyan.locdots.app.ui.Platinum

@Composable
fun EmptyStateContent(
    onCreateDot: () -> Unit,
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "No dots nearby, be the first one",
            color = Platinum
        )
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = onCreateDot,
            colors = ButtonDefaults.buttonColors(
                containerColor = Platinum
            )
        ) {
            Text("Create dot", color = EerieBlack)
        }
    }
}